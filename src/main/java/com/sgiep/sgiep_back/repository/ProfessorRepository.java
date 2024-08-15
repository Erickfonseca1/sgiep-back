package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
