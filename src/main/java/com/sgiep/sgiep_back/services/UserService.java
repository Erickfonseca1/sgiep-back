package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Citizen;
import com.sgiep.sgiep_back.model.Professor;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.CitizenRepository;
import com.sgiep.sgiep_back.repository.ProfessorRepository;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);

        if ("professor".equals(savedUser.getRole())) {
            Professor professor = new Professor();
            professor.setId(savedUser.getId());
            professorRepository.save(professor);
        }

        if ("citizen".equals(savedUser.getRole())) {
            Citizen citizen = new Citizen();
            citizen.setId(savedUser.getId());
            citizenRepository.save(citizen);
        }

        return savedUser;
    }
}
