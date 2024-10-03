package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import com.sgiep.sgiep_back.services.ProfessorService;

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

public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para encontrar todos os professores paginados
    @Test
    public void testFindAllProfessors() {
        Pageable pageable = PageRequest.of(0, 10);
        User professor = new User();
        professor.setId(1L);
        professor.setRole("PROFESSOR");

        Page<User> pagedProfessors = new PageImpl<>(Arrays.asList(professor), pageable, 1);

        when(userRepository.findByRole("PROFESSOR", pageable)).thenReturn(pagedProfessors);

        Page<User> result = professorService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findByRole("PROFESSOR", pageable);
    }

    // Teste para listar professores ativos
    @Test
    public void testGetActiveProfessors() {
        User professor = new User();
        professor.setId(1L);
        professor.setRole("PROFESSOR");
        professor.setActive(true);

        when(userRepository.findByRoleAndActive("PROFESSOR", true)).thenReturn(Arrays.asList(professor));

        List<User> result = professorService.getActiveProfessors();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByRoleAndActive("PROFESSOR", true);
    }

    // Teste para encontrar um professor por ID
    @Test
    public void testFindProfessorById_Success() {
        User professor = new User();
        professor.setId(1L);
        professor.setRole("PROFESSOR");

        when(userRepository.findById(1L)).thenReturn(Optional.of(professor));

        User result = professorService.findProfessorById(1L);

        assertNotNull(result);
        assertEquals("PROFESSOR", result.getRole());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindProfessorById_NotAProfessor() {
        User user = new User();
        user.setId(1L);
        user.setRole("STUDENT");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            professorService.findProfessorById(1L);
        });

        assertEquals("User is not a professor", exception.getMessage());
    }

    // Teste para atualizar um professor
    @Test
    public void testUpdateProfessor() {
        User existingProfessor = new User();
        existingProfessor.setId(1L);
        existingProfessor.setName("John Doe");
        existingProfessor.setEmail("john@example.com");

        User updatedProfessor = new User();
        updatedProfessor.setName("John Smith");
        updatedProfessor.setEmail("john.smith@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingProfessor));
        when(userRepository.save(any(User.class))).thenReturn(updatedProfessor);

        User result = professorService.updateProfessor(1L, updatedProfessor);

        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        assertEquals("john.smith@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingProfessor);
    }

    // Teste para mudar o status de um professor
    @Test
    public void testChangeProfessorStatus() {
        User professor = new User();
        professor.setId(1L);
        professor.setRole("PROFESSOR");
        professor.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(userRepository.save(any(User.class))).thenReturn(professor);

        professorService.changeProfessorStatus(1L);

        assertFalse(professor.isActive());  // Verifica se o status foi alterado
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(professor);
    }

    // Teste para encontrar professores por filtros (nome e email)
    @Test
    public void testFindProfessorsByFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        User professor = new User();
        professor.setId(1L);
        professor.setRole("PROFESSOR");

        Page<User> pagedProfessors = new PageImpl<>(Arrays.asList(professor), pageable, 1);

        when(userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                eq("PROFESSOR"), anyString(), anyString(), eq(pageable)))
                .thenReturn(pagedProfessors);

        Page<User> result = professorService.findProfessorsByFilters("John", "john@example.com", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1))
                .findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                        "PROFESSOR", "John", "john@example.com", pageable);
    }

    // Teste para criar um professor
    @Test
    public void testCreateProfessor() {
        User professor = new User();
        professor.setName("John Doe");
        professor.setEmail("john@example.com");

        when(userRepository.save(any(User.class))).thenReturn(professor);

        User result = professorService.createProfessor(professor);

        assertNotNull(result);
        assertEquals("PROFESSOR", result.getRole());  // Verifica se o papel foi setado corretamente
        assertFalse(result.isActive());  // Verifica se o status foi setado como inativo
        verify(userRepository, times(1)).save(professor);
    }
}
