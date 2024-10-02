package com.sgiep.sgiep_back.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseDTOTest {

    @Test
    public void testResponseDTO() {
        Long id = 1L;
        String name = "John Doe";
        String token = "abc123";
        String role = "USER";

        ResponseDTO responseDTO = new ResponseDTO(id, name, token, role);

        assertEquals(id, responseDTO.id());
        assertEquals(name, responseDTO.name());
        assertEquals(token, responseDTO.token());
        assertEquals(role, responseDTO.role());
    }
}

