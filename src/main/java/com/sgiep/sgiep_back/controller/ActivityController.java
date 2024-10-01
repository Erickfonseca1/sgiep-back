package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.services.AcitivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalTime;
import java.time.LocalDate;


import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ActivityController {

    @Autowired
    public AcitivityService activityService;

    @GetMapping
    public List<Activity> getActivities() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable Long id) {
        return activityService.findById(id);
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.save(activity);
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable Long id, @RequestBody Activity updatedActivity) {
        Activity existingActivity = activityService.findById(id);
        if (existingActivity != null) {
            updatedActivity.setId(id); // Garante que o ID seja o mesmo
            return activityService.save(updatedActivity);
        } else {
        throw new RuntimeException("Activity not found");
        }
    }

    @GetMapping("/paged")
    public Page<Activity> getPagedActivities(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return activityService.findAll(pageable);
    }

    @GetMapping("/filter-by-time")
    public List<Activity> filterActivitiesByTime(@RequestParam LocalTime startTime, @RequestParam LocalTime endTime) {
        return activityService.filterByTime(startTime, endTime);
    }

    @GetMapping("/professor/{professorId}")
    public List<Activity> getActivitiesByProfessor(@PathVariable Long professorId) {
        return activityService.findActivitiesByProfessor(professorId);
    }

    @GetMapping("/professor/{professorId}/filter")
    public List<Activity> filterActivitiesByProfessor(
        @PathVariable Long professorId, 
        @RequestParam(required = false) String name, 
        @RequestParam(required = false) String location) {
            return activityService.filterActivitiesByProfessor(professorId, name, location);
    }

    @GetMapping("/citizen/{citizenId}/agenda")
    public List<Activity> getCitizenAgenda(@PathVariable Long citizenId) {
        return activityService.findCitizenActivities(citizenId);
    }

    @GetMapping("/citizen/{citizenId}/agenda/filter")
    public List<Activity> filterCitizenAgenda(
        @PathVariable Long citizenId,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) { 
            return activityService.filterCitizenAgendaByDate(citizenId, startDate, endDate); 
        }

    @GetMapping("/professor/{professorId}/agenda")
    public List<Activity> getProfessorAgenda(@PathVariable Long professorId) {
        return activityService.findProfessorActivities(professorId);
    }

    @GetMapping("/professor/{professorId}/agenda/filter")
    public List<Activity> filterProfessorAgenda(
    @PathVariable Long professorId,
    @RequestParam(required = false) LocalDate startDate,
    @RequestParam(required = false) LocalDate endDate) {
        return activityService.filterProfessorAgendaByDate(professorId, startDate, endDate);
    }
}
