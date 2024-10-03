package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByRole(String role, Pageable pageable);

    List<User> findByRole(String role);

    List<User> findByRoleAndActive(String role, Boolean active);

    Page<User> findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String role,
            String name,
            String email,
            Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role = 'PROFESSOR' " +
       "AND (:name IS NULL OR u.name LIKE %:name%) " +
       "AND (:email IS NULL OR u.email LIKE %:email%)")
    List<User> findProfessorsByFilters(@Param("name") String name, @Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.role = 'PROFESSOR' " +
           "AND (:name IS NULL OR u.name LIKE %:name%) " +
           "AND (:email IS NULL OR u.email LIKE %:email%)")
    Page<User> findProfessorsByFilters(@Param("name") String name, 
                                       @Param("email") String email, 
                                       Pageable pageable);
}