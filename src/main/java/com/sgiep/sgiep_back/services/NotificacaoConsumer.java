package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.constantes.RabbitMQConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class NotificacaoConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = RabbitMQConstantes.FILA_NOTIFICACAO)
    public void processNotificacao(String message) {
        System.out.println("Notification message received: " + message);

        // Enviar a mensagem para todos os clientes conectados ao tópico "/topic/notifications"
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}

