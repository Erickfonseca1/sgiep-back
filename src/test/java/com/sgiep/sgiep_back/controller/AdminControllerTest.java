package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.AdminService;
import com.sgiep.sgiep_back.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AdminService adminService;

    @Test
    public void testGetPagedAdmins() throws Exception {
        // Configurando a resposta mockada do UserService
        User admin1 = new User();
        admin1.setId(1L);
        admin1.setName("Admin One");

        User admin2 = new User();
        admin2.setId(2L);
        admin2.setName("Admin Two");

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> pagedAdmins = new PageImpl<>(Arrays.asList(admin1, admin2), pageable, 2);

        given(userService.getUsersByRole("ADMIN", pageable)).willReturn(pagedAdmins);

        // Realiza a requisição GET e verifica a resposta
        mockMvc.perform(get("/api/admins/paged")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Admin One")))
                .andExpect(jsonPath("$.content[1].name", is("Admin Two")));
    }

    @Test
    public void testFilterAdmins() throws Exception {
        // Configurando a resposta mockada do AdminService
        User admin1 = new User();
        admin1.setId(1L);
        admin1.setName("Filtered Admin");

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> pagedAdmins = new PageImpl<>(Arrays.asList(admin1), pageable, 1);

        given(adminService.findAdminByFilters("Filtered Admin", null, pageable)).willReturn(pagedAdmins);

        // Realiza a requisição GET e verifica a resposta
        mockMvc.perform(get("/api/admins/filter")
                .param("name", "Filtered Admin")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Filtered Admin")));
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        // Simulação do serviço de deleção
        Mockito.doNothing().when(userService).deleteUser(1L);

        // Realiza a requisição DELETE e verifica a resposta
        mockMvc.perform(delete("/api/admins/1"))
                .andExpect(status().isOk());

        // Verifica se o método deleteUser foi chamado com o id correto
        Mockito.verify(userService).deleteUser(1L);
    }
}

