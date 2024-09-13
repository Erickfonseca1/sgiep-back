package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private UserRepository userRepository;

    public User findProfessorById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && "PROFESSOR".equalsIgnoreCase(user.get().getRole())) {
            return user.get();
        }
        throw new RuntimeException("Professor not found or user is not a professor");
    }

    public List<User> getAllProfessors() {
        return userRepository.findByRole("PROFESSOR");
    }

    public List<Activity> getActivitiesByProfessor(Long professorId) {
        User professor = findProfessorById(professorId);
        if (professor != null) {
            return professor.getActivitiesAsProfessor();
        }
        return null;
    }
}
