package com.sgiep.sgiep_back.services;

import com.rabbitmq.client.Channel;
import com.sgiep.sgiep_back.constantes.RabbitMQConstantes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CancelamentoConsumer {

    @RabbitListener(queues = RabbitMQConstantes.FILA_CANCELAMENTO, ackMode = "MANUAL")
    public void processCancelamento(String message, Channel channel, Message rabbitMessage) throws IOException {
        System.out.println("Cancellation message received: " + message);

        // Após o processamento da mensagem
        channel.basicAck(rabbitMessage.getMessageProperties().getDeliveryTag(), false);
    }


}


