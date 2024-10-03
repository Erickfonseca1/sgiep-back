package com.sgiep.sgiep_back.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterRequestDTOTest {

    @Test
    public void testRegisterRequestDTO() {
        String name = "John Doe";
        String email = "john@example.com";
        String password = "password123";
        String role = "USER";

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(name, email, password, role);

        assertEquals(name, registerRequestDTO.name());
        assertEquals(email, registerRequestDTO.email());
        assertEquals(password, registerRequestDTO.password());
        assertEquals(role, registerRequestDTO.role());
    }
}

