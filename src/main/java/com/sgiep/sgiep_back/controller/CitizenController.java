package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class CitizenController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getCitizens() {
        List<User> citizens = userService.getUsersByRole("CITIZEN");
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/{id}")
    public User getCitizen(@PathVariable Long id) {
        return userService.findById(id);
    }
}