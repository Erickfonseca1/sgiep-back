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

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
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

    public User createProfessor(User professor) {
        // Garante que o role do professor seja "PROFESSOR"
        professor.setRole("PROFESSOR");
        return userRepository.save(professor);
    }

    public User updateProfessor(Long professorId, User updatedProfessor) {
        User existingProfessor = userRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor not found"));
        
        // Atualiza os campos relevantes
        existingProfessor.setName(updatedProfessor.getName());
        existingProfessor.setEmail(updatedProfessor.getEmail());
        // Outros campos...
    
        return userRepository.save(existingProfessor);
    }

    public List<User> findProfessorsByFilters(String name, String email) {
        return userRepository.findProfessorsByFilters(name, email);
    }

    public Page<User> findProfessorsByFilters(String name, String email, Pageable pageable) {
        return userRepository.findProfessorsByFilters(name, email, pageable);
    public void changeStatus(Long id) {
        User user = findById(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
    }
}
