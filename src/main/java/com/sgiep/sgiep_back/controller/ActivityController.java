package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.services.AcitivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ActivityController {

    @Autowired
    public AcitivityService acitivityService;

    @GetMapping
    public List<Activity> getActivities() {
        return acitivityService.findAll();
    }

    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable Long id) {
        return acitivityService.findById(id);
    }
}
