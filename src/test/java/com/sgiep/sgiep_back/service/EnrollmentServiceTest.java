package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.constantes.RabbitMQConstantes;
import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import com.sgiep.sgiep_back.repository.EnrollmentRepository;
import com.sgiep.sgiep_back.repository.UserRepository;
import com.sgiep.sgiep_back.services.EnrollmentService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para inscrever o cidadão em uma atividade
    @Test
    public void testEnrollStudentSuccessfully() {
        // Mockando a atividade
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setMaxVacancies(10);  // Máximo de vagas
        activity.setStudents(mock(List.class));  // Mock para a lista de estudantes

        // Mockando o cidadão
        User citizen = new User();
        citizen.setId(1L);
        citizen.setRole("CITIZEN");

        // Simulando que a atividade e o cidadão são encontrados
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(citizen));

        // Simulação para verificar vagas disponíveis
        when(activity.getStudents().size()).thenReturn(5);

        // Chamando o método
        boolean result = enrollmentService.enrollStudent(1L, 1L);

        // Verificações
        assertTrue(result);
        verify(activity.getStudents(), times(1)).add(citizen);
        verify(activityRepository, times(1)).save(activity);

        // Verificando se a mensagem foi enviada ao RabbitMQ
        verify(rabbitTemplate, times(1))
                .convertAndSend(RabbitMQConstantes.FILA_INSCRICAO, "Citizen 1 enrolled in Activity 1");
    }

    // Teste para verificar quando o usuário não é cidadão
    @Test
    public void testEnrollStudentNotACitizen() {
        // Mockando a atividade
        Activity activity = new Activity();
        activity.setId(1L);

        // Mockando um usuário que não é cidadão
        User nonCitizen = new User();
        nonCitizen.setId(1L);
        nonCitizen.setRole("PROFESSOR");

        // Simulando que a atividade e o usuário são encontrados
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(nonCitizen));

        // Verificando se a exceção é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            enrollmentService.enrollStudent(1L, 1L);
        });

        assertEquals("User is not a citizen", exception.getMessage());
    }

    // Teste para verificar quando a atividade está sem vagas
    @Test
    public void testEnrollStudentNoVacancies() {
        // Mockando a atividade sem vagas
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setMaxVacancies(10);  // Máximo de vagas
        activity.setStudents(mock(List.class));

        // Mockando o cidadão
        User citizen = new User();
        citizen.setId(1L);
        citizen.setRole("CITIZEN");

        // Simulando que a atividade e o cidadão são encontrados
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(citizen));

        // Simulação para que a atividade não tenha vagas
        when(activity.getStudents().size()).thenReturn(10);

        // Verificando se a exceção é lançada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            enrollmentService.enrollStudent(1L, 1L);
        });

        assertEquals("No vacancies available for this activity.", exception.getMessage());
    }

    // Teste para cancelar a inscrição
    @Test
    public void testCancelEnrollmentSuccessfully() {
        // Mockando a atividade e o cidadão
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setStudents(mock(List.class));  // Mock da lista de estudantes

        User citizen = new User();
        citizen.setId(1L);

        // Simulando que a atividade e o cidadão são encontrados
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(citizen));

        // Simulação para verificar que o cidadão está inscrito
        when(activity.getStudents().contains(citizen)).thenReturn(true);

        // Chamando o método de cancelamento
        boolean result = enrollmentService.cancelEnrollment(1L, 1L);

        // Verificações
        assertTrue(result);
        verify(activity.getStudents(), times(1)).remove(citizen);
        verify(activityRepository, times(1)).save(activity);

        // Verificando se a mensagem foi enviada ao RabbitMQ
        verify(rabbitTemplate, times(1))
                .convertAndSend(RabbitMQConstantes.FILA_CANCELAMENTO,
                        "Citizen 1 canceled enrollment in Activity 1");
    }

    // Teste para cancelar inscrição que não existe
    @Test
    public void testCancelEnrollmentNotEnrolled() {
        // Mockando a atividade e o cidadão
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setStudents(mock(List.class));

        User citizen = new User();
        citizen.setId(1L);

        // Simulando que a atividade e o cidadão são encontrados
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(citizen));

        // Simulação para verificar que o cidadão não está inscrito
        when(activity.getStudents().contains(citizen)).thenReturn(false);

        // Chamando o método de cancelamento
        boolean result = enrollmentService.cancelEnrollment(1L, 1L);

        // Verificações
        assertFalse(result);
        verify(activity.getStudents(), never()).remove(citizen);
        verify(activityRepository, never()).save(activity);

        // Verificando se nenhuma mensagem foi enviada ao RabbitMQ
        verify(rabbitTemplate, never())
                .convertAndSend(anyString(), anyString());
    }
}

