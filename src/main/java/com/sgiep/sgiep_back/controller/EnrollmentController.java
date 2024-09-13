package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.services.AcitivityService;
import com.sgiep.sgiep_back.services.EnrollmentService;
import com.sgiep.sgiep_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AcitivityService activityService;

    @Autowired
    private UserService userService;

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
            return ResponseEntity.ok("Citizen enrolled successfully in the activity");
        } else {
            Activity activity = activityService.findById(activityId);
            User citizen = userService.findById(citizenId);

            if (activity != null && citizen != null && "CITIZEN".equalsIgnoreCase(citizen.getRole()) && activity.getStudents().contains(citizen)) {
                return ResponseEntity.badRequest().body("Citizen is already enrolled in this activity");
            }

            return ResponseEntity.badRequest().body("Activity or Citizen not found");
        }
    }
}
