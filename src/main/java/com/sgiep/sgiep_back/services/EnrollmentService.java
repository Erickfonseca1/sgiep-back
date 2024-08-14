package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Citizen;
import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import com.sgiep.sgiep_back.repository.CitizenRepository;
import com.sgiep.sgiep_back.repository.EnrollmentRepository;
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
    private CitizenRepository citizenRepository;

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public boolean enrollStudent(Long activityId, Long citizenId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        Citizen citizen = citizenRepository.findById(citizenId).orElse(null);

        if (activity == null || citizen == null) {
            return false;
        }

        if (!activity.getStudents().contains(citizen)) {
            activity.getStudents().add(citizen);
            activityRepository.save(activity);
        }

        return true;
    }
}
