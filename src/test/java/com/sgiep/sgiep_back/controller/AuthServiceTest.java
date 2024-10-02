package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.client.sgiepAuth.AuthFieldsClient;
import com.sgiep.sgiep_back.dto.RegisterRequestDTO;
import com.sgiep.sgiep_back.services.AuthService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    private AuthFieldsClient authServiceFeignClient;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegister() {
        // Mockando a resposta do Feign Client para o método register
        String mockRegisterResponse = "User registered successfully";

        // Definindo o comportamento simulado do método register
        when(authServiceFeignClient.register(any(RegisterRequestDTO.class))).thenReturn(mockRegisterResponse);

        // Executando o teste do método register no AuthService
        String response = authService.register("John Doe", "john@example.com", "password123", "USER");

        // Verificando se a resposta está correta
        assertEquals("User registered successfully", response);

        // Verificando se o método register do FeignClient foi chamado com os parâmetros corretos
        verify(authServiceFeignClient).register(any(RegisterRequestDTO.class));
    }

}
