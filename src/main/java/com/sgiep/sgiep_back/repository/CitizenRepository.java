package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
}
