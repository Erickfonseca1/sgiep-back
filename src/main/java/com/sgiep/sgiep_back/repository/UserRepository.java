package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);
    List<User> findByRoleAndActive(String role, Boolean active);
}