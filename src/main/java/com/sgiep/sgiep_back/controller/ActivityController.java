package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.dto.ActivityDTO;
import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Schedule;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.UserRepository;
import com.sgiep.sgiep_back.services.AcitivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.stream.Collectors;


import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class ActivityController {

    @Autowired
    public AcitivityService activityService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PagedResourcesAssembler<Activity> pagedResourcesAssembler;

    @GetMapping
    public List<Activity> getActivities() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable Long id) {
        return activityService.findById(id);
    }

    @PostMapping
public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO activityDTO) {
    try {
        // Converter DTO para entidade Activity
        Activity activity = new Activity();
        activity.setName(activityDTO.getName());
        activity.setDescription(activityDTO.getDescription());
        activity.setLocation(activityDTO.getLocation());
        activity.setMaxVacancies(activityDTO.getMaxVacancies());

        // Associar o professor à atividade, se aplicável
        if (activityDTO.getProfessorId() != null) {
            User professor = userRepository.findById(activityDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            activity.setProfessor(professor);
        }

        // Converter ScheduleDTO para Schedule e associar à atividade
        List<Schedule> schedules = activityDTO.getSchedules().stream().map(scheduleDTO -> {
            Schedule schedule = new Schedule();
            schedule.setDayOfWeek(DayOfWeek.valueOf(scheduleDTO.getDayOfWeek().toUpperCase()));  // Converter string para DayOfWeek
            schedule.setStartTime(LocalTime.parse(scheduleDTO.getStartTime()));  // Converter string para LocalTime
            schedule.setEndTime(LocalTime.parse(scheduleDTO.getEndTime()));      // Converter string para LocalTime
            schedule.setActivity(activity);  // Associar atividade ao schedule
            return schedule;
        }).collect(Collectors.toList());

        activity.setSchedules(schedules);

        Activity createdActivity = activityService.save(activity);
        return ResponseEntity.ok(createdActivity);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}



    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity updatedActivity) {
        Activity existingActivity = activityService.findById(id);
        
        if (existingActivity == null) {
            return ResponseEntity.notFound().build();
        }

        existingActivity.setName(updatedActivity.getName());
        existingActivity.setDescription(updatedActivity.getDescription());
        existingActivity.setLocation(updatedActivity.getLocation());
        existingActivity.setMaxVacancies(updatedActivity.getMaxVacancies());
        existingActivity.setProfessor(updatedActivity.getProfessor());
        existingActivity.setSchedules(updatedActivity.getSchedules());

        Activity savedActivity = activityService.save(existingActivity);
        
        return ResponseEntity.ok(savedActivity);
    }


    @GetMapping("/paged")
    public PagedModel<EntityModel<Activity>> getPagedActivities(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activityPage = activityService.findAll(pageable);

        return pagedResourcesAssembler.toModel(activityPage,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActivityController.class).getPagedActivities(page, size)).withSelfRel());
    }

    @GetMapping("/{id}/citizens")
    public List<User> getActivityCitizens(@PathVariable Long id) {
        return activityService.findCitizensByActivity(id);
    }

    @GetMapping("/{id}/schedules")
    public List<Schedule> getActivitySchedules(@PathVariable Long id) {
        Activity activity = activityService.findById(id);
        if (activity != null) {
            return activity.getSchedules();
        } else {
            throw new RuntimeException("Activity not found");
        }
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

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
    }
}
