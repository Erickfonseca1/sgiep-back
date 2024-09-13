package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ManagerController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getManagers() {
        List<User> managers = userService.getUsersByRole("MANAGER");
        return ResponseEntity.ok(managers);
    }
}
