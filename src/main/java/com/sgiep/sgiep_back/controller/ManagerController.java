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

    @GetMapping("/filter")
    public Page<User> getPagedManagers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return managerService.findManagersByFilters(name, email, pageable);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveManagers() {
        List<User> managers = managerService.getActiveManagers();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getManager(@PathVariable Long id) {
        User manager = managerService.findManagerById(id);
        return ResponseEntity.ok(manager);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateManager(@PathVariable Long id, @RequestBody User manager) {
        User updatedManager = managerService.updateManager(id, manager);
        return ResponseEntity.ok(updatedManager);
    }

    @PutMapping("/{id}/status")
    public void changeManagerStatus(@PathVariable Long id) {
        managerService.changeManagerStatus(id);
    }
}
