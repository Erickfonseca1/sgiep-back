package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ProfessorController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getProfessors() {
        List<User> professors = userService.getUsersByRole("PROFESSOR");
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
}
