package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.ProfessorService;
import com.sgiep.sgiep_back.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController professorController;

    // Teste para o endpoint POST /api/professors
    @Test
    public void testCreateProfessor() throws Exception {
        // Mockando a criação de um professor
        User professor = new User();
        professor.setId(1L);
        professor.setName("New Professor");

        // Mockando o serviço
        given(professorService.createProfessor(professor)).willReturn(professor);

        // Requisição POST e verificações
        mockMvc.perform(post("/api/professors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"New Professor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Professor")));
    }

    // Teste para o endpoint PUT /api/professors/{id}
    @Test
    public void testUpdateProfessor() throws Exception {
        // Mockando a atualização de um professor
        User updatedProfessor = new User();
        updatedProfessor.setId(1L);
        updatedProfessor.setName("Updated Professor");

        // Mockando o serviço
        given(professorService.updateProfessor(1L, updatedProfessor)).willReturn(updatedProfessor);

        // Requisição PUT e verificações
        mockMvc.perform(put("/api/professors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Professor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Professor")));
    }
}

