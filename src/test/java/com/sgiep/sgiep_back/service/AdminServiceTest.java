package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import com.sgiep.sgiep_back.services.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para o método findAdminByFilters
    @Test
    public void testFindAdminByFilters() {
        // Mockando um usuário administrador
        User admin1 = new User();
        admin1.setId(1L);
        admin1.setName("Admin One");
        admin1.setEmail("admin1@example.com");

        User admin2 = new User();
        admin2.setId(2L);
        admin2.setName("Admin Two");
        admin2.setEmail("admin2@example.com");

        // Simulação de retorno paginado
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> pagedAdmins = new PageImpl<>(Arrays.asList(admin1, admin2), pageable, 2);

        // Mockando o repositório para retornar a página de admins
        when(userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                eq("ADMIN"),
                anyString(),
                anyString(),
                eq(pageable)
        )).thenReturn(pagedAdmins);

        // Chamando o serviço
        Page<User> result = adminService.findAdminByFilters("Admin", "example.com", pageable);

        // Verificando o resultado
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Admin One", result.getContent().get(0).getName());
        assertEquals("admin1@example.com", result.getContent().get(0).getEmail());

        // Verificando se o repositório foi chamado corretamente
        verify(userRepository, times(1))
                .findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase("ADMIN", "Admin", "example.com", pageable);
    }
}

