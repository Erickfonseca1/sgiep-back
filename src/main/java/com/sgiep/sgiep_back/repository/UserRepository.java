package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
