package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@RestController
@RequestMapping("/api/citizens")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class CitizenController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getCitizens() {
        return userService.getCitizens();
    }

    @GetMapping("/paged")
    public Page<User> getPagedCitizens(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getUsersByRole("CITIZEN", pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getCitizen(@PathVariable Long id) {
        User citizen = userService.findCitizenById(id);
        return ResponseEntity.ok(citizen);
    }

    @GetMapping("/{id}/activities")
    public List<Activity> getCitizenActivities(@PathVariable Long id) {
        return userService.getActivitiesByUserId(id);
    }
    

    @PutMapping("/{id}/status")
    public void changeCitizenStatus(@PathVariable Long id) {
        userService.changeUserStatus(id);
    }
}