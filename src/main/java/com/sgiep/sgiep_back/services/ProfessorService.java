package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findByRole("PROFESSOR", pageable);
    }

    public List<User> getActiveProfessors() {
        return userRepository.findByRoleAndActive("PROFESSOR", true);
    }

    public List<Activity> getActivitiesByProfessor(Long professorId) {
        User professor = findProfessorById(professorId);
        if (professor != null && professor.isActive()) {
            return professor.getActivitiesAsProfessor();
        }
        return null;
    }
}
