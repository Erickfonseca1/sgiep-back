package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<?> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.findAll());
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestBody Map<String, Object> requestBody) {
        if (requestBody == null || !requestBody.containsKey("activityId") || !requestBody.containsKey("citizenId")) {
            return ResponseEntity.badRequest().body("Dados inválidos: activityId ou citizenId está ausente.");
        }

        Long activityId = ((Number) requestBody.get("activityId")).longValue();
        Long citizenId = ((Number) requestBody.get("citizenId")).longValue();

        boolean enrolled = enrollmentService.enrollStudent(activityId, citizenId);

        if (enrolled) {
            return ResponseEntity.ok("Citizen enrolled successfully in the activity.");
        } else {
            return ResponseEntity.badRequest().body("Citizen is already enrolled in this activity.");
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelEnrollment(@RequestBody Map<String, Object> requestBody) {
        Long activityId = ((Number) requestBody.get("activityId")).longValue();
        Long citizenId = ((Number) requestBody.get("citizenId")).longValue();

        boolean canceled = enrollmentService.cancelEnrollment(activityId, citizenId);

        if (canceled) {
            return ResponseEntity.ok("Citizen canceled enrollment successfully.");
        } else {
            return ResponseEntity.badRequest().body("Citizen is not enrolled in this activity.");
        }
    }



}
