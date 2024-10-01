package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Page<User> getUsersByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public User findProfessorById(Long id) {
        User professor = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor not found"));
        if (!"PROFESSOR".equalsIgnoreCase(professor.getRole())) {
            throw new RuntimeException("User is not a professor");
        }
        return professor;
    }

    public List<Activity> getActivitiesByUserId(Long id) {
        User user = findById(id);
        if ("PROFESSOR".equalsIgnoreCase(user.getRole())) {
            return user.getActivitiesAsProfessor();
        }

        if ("CITIZEN".equalsIgnoreCase(user.getRole())) {
            return user.getActivitiesAsStudent();
        }

        throw new RuntimeException("User is not a professor");
    }
}
