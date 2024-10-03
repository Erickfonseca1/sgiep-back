package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import com.sgiep.sgiep_back.services.UserService;

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

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para listar todos os usuários
    @Test
    public void testFindAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    // Teste para encontrar um usuário por ID
    @Test
    public void testFindUserById_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("User not found", exception.getMessage());
    }

    // Teste para encontrar usuários por função com paginação
    @Test
    public void testGetUsersByRole() {
        Pageable pageable = PageRequest.of(0, 10);
        User user = new User();
        user.setId(1L);
        user.setRole("USER");

        Page<User> pagedUsers = new PageImpl<>(Arrays.asList(user), pageable, 1);

        when(userRepository.findByRole("USER", pageable)).thenReturn(pagedUsers);

        Page<User> result = userService.getUsersByRole("USER", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findByRole("USER", pageable);
    }

    // Teste para encontrar usuários com filtros (nome e email)
    @Test
    public void testFindUsersByFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Page<User> pagedUsers = new PageImpl<>(Arrays.asList(user), pageable, 1);

        when(userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                eq("USER"), anyString(), anyString(), eq(pageable)))
                .thenReturn(pagedUsers);

        Page<User> result = userService.findUsersByFilters("John", "john@example.com", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1))
                .findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                        "USER", "John", "john@example.com", pageable);
    }

    // Teste para buscar atividades associadas ao usuário (professor ou cidadão)
    @Test
    public void testGetActivitiesByUserId_AsProfessor() {
        User user = new User();
        user.setId(1L);
        user.setRole("PROFESSOR");
        Activity activity = new Activity();
        user.setActivitiesAsProfessor(Arrays.asList(activity));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Activity> result = userService.getActivitiesByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetActivitiesByUserId_AsCitizen() {
        User user = new User();
        user.setId(1L);
        user.setRole("CITIZEN");
        Activity activity = new Activity();
        user.setActivitiesAsStudent(Arrays.asList(activity));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Activity> result = userService.getActivitiesByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetActivitiesByUserId_NotAProfessorOrCitizen() {
        User user = new User();
        user.setId(1L);
        user.setRole("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getActivitiesByUserId(1L);
        });

        assertEquals("User is not a professor", exception.getMessage());
    }

    // Teste para mudar o status de um usuário
    @Test
    public void testChangeUserStatus() {
        User user = new User();
        user.setId(1L);
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changeUserStatus(1L);

        assertFalse(user.isActive());  // Verifica se o status foi alterado
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    // Teste para deletar um usuário
    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}

