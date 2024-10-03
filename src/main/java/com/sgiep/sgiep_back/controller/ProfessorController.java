package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.ProfessorService;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ProfessorController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfessorService professorService;


    @GetMapping("/paged")
    public Page<User> getPagedProfessors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return professorService.findAll(pageable);
    }

    @GetMapping("/filter")
    public Page<User> getPagedProfessors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return professorService.findProfessorsByFilters(name, email, pageable);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveProfessors() {
        List<User> professors = professorService.getActiveProfessors();
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getProfessor(@PathVariable Long id) {
        User professor = userService.findProfessorById(id);
        return ResponseEntity.ok(professor);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<Activity>> getProfessorActivities(@PathVariable Long id) {
        List<Activity> activities = userService.getActivitiesByUserId(id);
        if (activities != null && !activities.isEmpty()) {
            return ResponseEntity.ok(activities);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createProfessor(@RequestBody User professor) {
        User createdProfessor = professorService.createProfessor(professor);
        return ResponseEntity.ok(createdProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfessor(@PathVariable Long id, @RequestBody User updatedProfessor) {
        User professor = professorService.updateProfessor(id, updatedProfessor);
        return ResponseEntity.ok(professor);
    }

    @PutMapping("/{id}/status")
    public void changeProfessorStatus(@PathVariable Long id) {
        professorService.changeProfessorStatus(id);
    }
}
