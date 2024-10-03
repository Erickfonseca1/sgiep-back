package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.ManagerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerController managerController;

    // Teste para o endpoint PUT /api/managers/{id}
    @Test
    public void testUpdateManager() throws Exception {
        // Mockando a atualização do gestor
        User updatedManager = new User();
        updatedManager.setId(1L);
        updatedManager.setName("Updated Manager");

        // Mockando o serviço
        given(managerService.updateManager(1L, updatedManager)).willReturn(updatedManager);

        // Requisição PUT e verificações
        mockMvc.perform(put("/api/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Manager\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Manager")));
    }
}

