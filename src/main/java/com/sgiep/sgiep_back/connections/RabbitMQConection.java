package com.sgiep.sgiep_back.connections;

import com.sgiep.sgiep_back.constantes.RabbitMQConstantes;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private final AmqpAdmin amqpAdmin;

    // Construtor para injeção do AmqpAdmin
    public RabbitMQConection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    // Método para criação de uma fila
    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    // Método para criação de uma Direct Exchange
    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    // Método para criar o binding entre fila e exchange
    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    // Método chamado logo após a inicialização da classe (PostConstruct)
    @PostConstruct
    private void adicionaFilas() {
        // Criando as filas
        Queue filaInscricao = this.fila(RabbitMQConstantes.FILA_INSCRICAO);
        Queue filaCancelamento = this.fila(RabbitMQConstantes.FILA_CANCELAMENTO);
        Queue filaNotificacao = this.fila(RabbitMQConstantes.FILA_NOTIFICACAO);

        // Criando a Direct Exchange
        DirectExchange troca = this.trocaDireta();

        // Declarando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(filaInscricao);
        this.amqpAdmin.declareQueue(filaCancelamento);
        this.amqpAdmin.declareQueue(filaNotificacao);

        // Declarando a exchange no RabbitMQ
        this.amqpAdmin.declareExchange(troca);

        // Criando e declarando os bindings (associação das filas à exchange)
        Binding ligacaoInscricao = this.relacionamento(filaInscricao, troca);
        Binding ligacaoCancelamento = this.relacionamento(filaCancelamento, troca);
        Binding ligacaoNotificacao = this.relacionamento(filaNotificacao, troca);

        this.amqpAdmin.declareBinding(ligacaoInscricao);
        this.amqpAdmin.declareBinding(ligacaoCancelamento);
        this.amqpAdmin.declareBinding(ligacaoNotificacao);
    }
}
