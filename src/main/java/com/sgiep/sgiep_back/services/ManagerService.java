package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private UserRepository userRepository;

    public User findManagerById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && "MANAGER".equalsIgnoreCase(user.get().getRole())) {
            return user.get();
        }

        throw new RuntimeException("Manager not found or user is not a manager");
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findByRole("MANAGER", pageable);
    }

    public Page<User> findManagersByFilters(String name, String email, Pageable pageable) {
        // Define os valores padrão para name e email caso sejam nulos
        String normalizedName = (name == null || name.isEmpty()) ? "" : name;
        String normalizedEmail = (email == null || email.isEmpty()) ? "" : email;

        System.out.println("normalizedName: " + normalizedName);

        // Chama o metodo do repositório que utiliza a convenção do Spring Data JPA
        return userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                "MANAGER", normalizedName, normalizedEmail, pageable);
    }

    public List<User> getActiveManagers() {
        return userRepository.findByRoleAndActive("MANAGER", true);
    }

    public User updateManager(Long managerId, User updatedManager) {
        User existingManager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        existingManager.setName(updatedManager.getName());
        existingManager.setEmail(updatedManager.getEmail());

        return userRepository.save(existingManager);
    }

    public void changeManagerStatus(Long managerId) {
        User manager = findManagerById(managerId);
        manager.setActive(!manager.isActive());
        userRepository.save(manager);
    }
}
