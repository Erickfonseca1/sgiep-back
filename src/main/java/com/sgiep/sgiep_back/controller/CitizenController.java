package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Citizen;
import com.sgiep.sgiep_back.model.Professor;
import com.sgiep.sgiep_back.services.CitizenService;
import com.sgiep.sgiep_back.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @GetMapping
    public ResponseEntity<List<Citizen>> getCitizens() {
        List<Citizen> citizens = citizenService.getAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/{id}")
    public Citizen getCitizen(@PathVariable Long id) {
        return citizenService.findById(id);
    }
}
