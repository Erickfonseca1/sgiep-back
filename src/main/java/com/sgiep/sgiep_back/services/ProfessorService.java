package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ProfessorService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findByRole("PROFESSOR", pageable);
    }

    public List<User> getActiveProfessors() {
        return userRepository.findByRoleAndActive("PROFESSOR", true);
    }

    public User findProfessorById(Long id) {
        User professor = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
        if (!"PROFESSOR".equalsIgnoreCase(professor.getRole())) {
            throw new RuntimeException("User is not a professor");
        }
        return professor;
    }

    public List<Activity> getActivitiesByProfessor(Long professorId) {
        User professor = findProfessorById(professorId);
        if (professor != null && professor.isActive()) {
            return professor.getActivitiesAsProfessor();
        }
        return null;
    }

    public void changeProfessorStatus(Long professorId) {
        User professor = findProfessorById(professorId);
        professor.setActive(!professor.isActive());
        userRepository.save(professor);
    }

    public List<User> findProfessorsByFilters(String name, String email) {
        return userRepository.findProfessorsByFilters(name, email);
    }

    public Page<User> findProfessorsByFilters(String name, String email, Pageable pageable) {
        // Define os valores padrão para name e email caso sejam nulos
        String normalizedName = (name == null || name.isEmpty()) ? "" : name;
        String normalizedEmail = (email == null || email.isEmpty()) ? "" : email;

        System.out.println("normalizedName: " + normalizedName);

        // Chama o metodo do repositório que utiliza a convenção do Spring Data JPA
        return userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                "PROFESSOR", normalizedName, normalizedEmail, pageable);
    }

    public User createProfessor(User professor) {
        professor.setRole("PROFESSOR");
        professor.setActive(false);
        return userRepository.save(professor);
    }

    public User updateProfessor(Long professorId, User updatedProfessor) {
        User existingProfessor = userRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        existingProfessor.setName(updatedProfessor.getName());
        existingProfessor.setEmail(updatedProfessor.getEmail());

        return userRepository.save(existingProfessor);
    }

    public static String removeAccents(String str) {
        if (str == null) {
            return null;
        }
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();  // também converte para minúsculas
    }
}
