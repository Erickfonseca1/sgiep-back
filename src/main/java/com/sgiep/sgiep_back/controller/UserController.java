package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/professors")
    public User createProfessor(@RequestBody User professor) {
        return userService.createProfessor(professor);
    }

    @PutMapping("/professors/{professorId}")
    public User updateProfessor(@PathVariable Long professorId, @RequestBody User updatedProfessor) {
        return userService.updateProfessor(professorId, updatedProfessor);
    }

    @GetMapping("/api/users/professors")
    public Object getProfessors(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    @RequestParam(required = false) Integer page,   // Parâmetros de paginação opcionais
    @RequestParam(required = false) Integer size) {
    if (page != null && size != null) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.findProfessorsByFilters(name, email, pageable);
    } else {
        return userService.findProfessorsByFilters(name, email);
    }
}

    @GetMapping("/professors")
    public List<User> getProfessors
    (@RequestParam(required = false) String name, @RequestParam(required = false) String email) 
    {
        return userService.findProfessorsByFilters(name, email);
    }

// Listagem paginada de professores
    @GetMapping("/professors/paginated")
    public Page<User> getPaginatedProfessors(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    @RequestParam(defaultValue = "0") int page,      // Página padrão = 0 (primeira página)
    @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
        return userService.findProfessorsByFilters(name, email, pageable);
    }

}
