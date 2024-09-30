package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> getAllManagers() {
        return userRepository.findByRole("MANAGER");
    }

    public List<User> getActiveManagers() {
        return userRepository.findByRoleAndActive("MANAGER", true);
    }
}
