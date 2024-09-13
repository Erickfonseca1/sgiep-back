package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import com.sgiep.sgiep_back.repository.EnrollmentRepository;
import com.sgiep.sgiep_back.repository.UserRepository;
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

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public boolean enrollStudent(Long activityId, Long citizenId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Activity not found"));
        User citizen = userRepository.findById(citizenId).orElseThrow(() -> new RuntimeException("Citizen not found"));

        if (!"CITIZEN".equalsIgnoreCase(citizen.getRole())) {
            throw new RuntimeException("User is not a citizen");
        }

        if (activity.getStudents().contains(citizen)) {
            return false;  // Already enrolled
        }

        activity.getStudents().add(citizen);
        activityRepository.save(activity);
        return true;
    }
}
