package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.constantes.RabbitMQConstantes;
import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import com.sgiep.sgiep_back.repository.EnrollmentRepository;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;  // Injetar o RabbitTemplate para mensageria

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    // Método para inscrever o cidadão na atividade
    public boolean enrollStudent(Long activityId, Long citizenId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        User citizen = userRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        if (!"CITIZEN".equalsIgnoreCase(citizen.getRole())) {
            throw new RuntimeException("User is not a citizen");
        }

        if (activity.getStudents().contains(citizen)) {
            return false; // Já está inscrito
        }

        activity.getStudents().add(citizen);
        activityRepository.save(activity);

        // Enviar mensagem para a fila de inscrição no RabbitMQ
        String mensagem = "Citizen " + citizen.getId() + " enrolled in Activity " + activity.getId();
        rabbitTemplate.convertAndSend(RabbitMQConstantes.FILA_INSCRICAO, mensagem);

        return true;
    }

    public boolean cancelEnrollment(Long activityId, Long citizenId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        User citizen = userRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        if (!activity.getStudents().contains(citizen)) {
            return false; // Não está inscrito
        }

        // Remover o cidadão da atividade
        activity.getStudents().remove(citizen);
        activityRepository.save(activity);

        // Enviar mensagem para a fila de cancelamento no RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConstantes.FILA_CANCELAMENTO,
                "Citizen " + citizen.getId() + " canceled enrollment in Activity " + activity.getId());

        return true;
    }

}
