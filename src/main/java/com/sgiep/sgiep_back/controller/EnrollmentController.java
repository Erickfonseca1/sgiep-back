package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.findAll();
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.save(enrollment);
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestParam Long activityId, @RequestParam Long citizenId) {
        boolean enrolled = enrollmentService.enrollStudent(activityId, citizenId);

        if (enrolled) {
            return ResponseEntity.ok("Citizen enrolled sucessfully in the activity");
        } else {
            return ResponseEntity.badRequest().body("Activity or Citizen not found");
        }
    }
}
