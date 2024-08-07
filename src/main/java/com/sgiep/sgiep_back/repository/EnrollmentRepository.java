package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
