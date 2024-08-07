package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
