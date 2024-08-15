package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Professor;
import com.sgiep.sgiep_back.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor findById(Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        return professor.orElse(null);
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public List<Activity> getActivitiesByProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElse(null);
        if (professor != null ) {
            return professor.getActivities();
        }
        return null;
    }
}
