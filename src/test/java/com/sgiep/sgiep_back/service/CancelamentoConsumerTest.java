package com.sgiep.sgiep_back.service;

import com.rabbitmq.client.Channel;
import com.sgiep.sgiep_back.services.CancelamentoConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CancelamentoConsumerTest {

    @InjectMocks
    private CancelamentoConsumer cancelamentoConsumer;

    @Mock
    private Channel channel;

    @Mock
    private Message rabbitMessage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessCancelamento() throws IOException {
        // Mockando a mensagem do RabbitMQ
        String mockMessage = "Cancellation message";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryTag(1L);
        when(rabbitMessage.getMessageProperties()).thenReturn(messageProperties);

        // Chamando o método que processa a mensagem
        cancelamentoConsumer.processCancelamento(mockMessage, channel, rabbitMessage);

        // Verificando se o ack foi chamado após o processamento
        verify(channel, times(1)).basicAck(1L, false);
        verify(rabbitMessage, times(1)).getMessageProperties();
    }
}

