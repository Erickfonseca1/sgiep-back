package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.ManagerService;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ManagerController {


    @Autowired
    private ManagerService managerService;

    @GetMapping("/paged")
    public Page<User> getPagedManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return managerService.findAll(pageable);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveManagers() {
        List<User> managers = managerService.getActiveManagers();
        return ResponseEntity.ok(managers);
    }
}
