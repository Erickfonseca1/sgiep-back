package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.services.NotificacaoConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

public class NotificacaoConsumerTest {

    @InjectMocks
    private NotificacaoConsumer notificacaoConsumer;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para processar a notificação recebida
    @Test
    public void testProcessNotificacao() {
        String mockMessage = "Test notification message";

        // Chamando o método processNotificacao
        notificacaoConsumer.processNotificacao(mockMessage);

        // Verificando se a mensagem foi enviada para o tópico "/topic/notifications"
        verify(messagingTemplate, times(1))
                .convertAndSend("/topic/notifications", mockMessage);
    }
}

