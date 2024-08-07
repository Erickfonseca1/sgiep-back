package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
