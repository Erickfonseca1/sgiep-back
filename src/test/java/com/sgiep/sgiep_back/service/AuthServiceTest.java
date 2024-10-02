package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.client.sgiepAuth.AuthFieldsClient;
import com.sgiep.sgiep_back.dto.LoginRequestDTO;
import com.sgiep.sgiep_back.dto.RegisterRequestDTO;
import com.sgiep.sgiep_back.dto.ResponseDTO;
import com.sgiep.sgiep_back.services.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthFieldsClient authServiceFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para o método register
    @Test
    public void testRegister() {
        // Mockando o retorno do Feign Client
        when(authServiceFeignClient.register(any(RegisterRequestDTO.class))).thenReturn("User registered successfully");

        // Chamando o serviço de registro
        String response = authService.register("John Doe", "john@example.com", "password123", "USER");

        // Verificando o resultado
        assertNotNull(response);
        assertEquals("User registered successfully", response);
    }
}

