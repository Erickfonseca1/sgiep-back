package com.sgiep.sgiep_back.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestDTOTest {

    @Test
    public void testLoginRequestDTO() {
        String email = "test@example.com";
        String password = "password123";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        assertEquals(email, loginRequestDTO.email());
        assertEquals(password, loginRequestDTO.password());
    }
}
