package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAdminByFilters(String name, String email, Pageable pageable) {
        // Define os valores padrão para name e email caso sejam nulos
        String normalizedName = (name == null || name.isEmpty()) ? "" : name;
        String normalizedEmail = (email == null || email.isEmpty()) ? "" : email;

        return userRepository.findByRoleAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase("ADMIN", normalizedName, normalizedEmail, pageable);
    }
}
