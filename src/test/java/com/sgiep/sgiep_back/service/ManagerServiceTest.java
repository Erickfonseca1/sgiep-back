package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import com.sgiep.sgiep_back.services.ManagerService;

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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para encontrar um gerente por ID
    @Test
    public void testFindManagerById_Success() {
        User manager = new User();
        manager.setId(1L);
        manager.setRole("MANAGER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(manager));

        User result = managerService.findManagerById(1L);

        assertNotNull(result);
        assertEquals("MANAGER", result.getRole());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindManagerById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            managerService.findManagerById(1L);
        });

        assertEquals("Manager not found or user is not a manager", exception.getMessage());
    }

    // Teste para encontrar todos os gerentes paginados
    @Test
    public void testFindAllManagers() {
        Pageable pageable = PageRequest.of(0, 10);
        User manager = new User();
        manager.setId(1L);
        manager.setRole("MANAGER");

        Page<User> pagedManagers = new PageImpl<>(Arrays.asList(manager), pageable, 1);

        when(userRepository.findByRole("MANAGER", pageable)).thenReturn(pagedManagers);

        Page<User> result = managerService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findByRole("MANAGER", pageable);
    }

    // Teste para encontrar gerentes por filtros (nome e email)
    @Test
    public void testFindManagersByFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        User manager = new User();
        manager.setId(1L);
        manager.setRole("MANAGER");

        Page<User> pagedManagers = new PageImpl<>(Arrays.asList(manager), pageable, 1);

        when(userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                eq("MANAGER"), anyString(), anyString(), eq(pageable)))
                .thenReturn(pagedManagers);

        Page<User> result = managerService.findManagersByFilters("John", "john@example.com", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1))
                .findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                        "MANAGER", "John", "john@example.com", pageable);
    }

    // Teste para listar gerentes ativos
    @Test
    public void testGetActiveManagers() {
        User manager = new User();
        manager.setId(1L);
        manager.setRole("MANAGER");
        manager.setActive(true);

        when(userRepository.findByRoleAndActive("MANAGER", true)).thenReturn(Arrays.asList(manager));

        List<User> result = managerService.getActiveManagers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByRoleAndActive("MANAGER", true);
    }

    // Teste para atualizar um gerente
    @Test
    public void testUpdateManager() {
        User existingManager = new User();
        existingManager.setId(1L);
        existingManager.setName("John Doe");
        existingManager.setEmail("john@example.com");

        User updatedManager = new User();
        updatedManager.setName("John Smith");
        updatedManager.setEmail("john.smith@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingManager));
        when(userRepository.save(any(User.class))).thenReturn(updatedManager);

        User result = managerService.updateManager(1L, updatedManager);

        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        assertEquals("john.smith@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingManager);
    }

    // Teste para mudar o status de um gerente
    @Test
    public void testChangeManagerStatus() {
        User manager = new User();
        manager.setId(1L);
        manager.setRole("MANAGER");
        manager.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(manager));
        when(userRepository.save(any(User.class))).thenReturn(manager);

        managerService.changeManagerStatus(1L);

        assertFalse(manager.isActive());  // Verifica se o status foi alterado
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(manager);
    }
}

