package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Citizen;
import com.sgiep.sgiep_back.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    public Citizen findById(Long id) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        return citizen.orElse(null);
    }

    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }
}
