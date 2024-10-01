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

    public Page<User> findUsersByFilters(String name, String email, Pageable pageable) {
        // Se name e email forem nulos ou vazios, define como string vazia
        String searchName = (name == null || name.isEmpty()) ? "" : name;
        String searchEmail = (email == null || email.isEmpty()) ? "" : email;

        // Chama o repositório para fazer a busca com os filtros
        return userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                "USER", searchName, searchEmail, pageable);
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

    public void changeUserStatus(Long id) {
        User user = findById(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
