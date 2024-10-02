package com.sgiep.sgiep_back.controller;

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
    public Activity createActivity(@RequestBody Activity activity) {
        // Verifica se o campo professor contém um id
        if (activity.getProfessor() != null && activity.getProfessor().getId() != null) {
            // Busca o professor pelo id e atribui à atividade
            User professor = userRepository.findById(activity.getProfessor().getId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            activity.setProfessor(professor);

            System.out.println("Professor: " + professor.getName());
        }

        // Salva a atividade
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
    public PagedModel<EntityModel<Activity>> getPagedActivities(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activityPage = activityService.findAll(pageable);

        return pagedResourcesAssembler.toModel(activityPage,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActivityController.class).getPagedActivities(page, size)).withSelfRel());
    }

    @GetMapping("/{id}/citizens")
    public List<Activity> getActivityCitizens(@PathVariable Long id) {
        return activityService.findCitizensByActivity(id);
    }

    @GetMapping("/{id}/schedules")
    public List<Schedule> getActivitySchedules(@PathVariable Long id) {
        Activity activity = activityService.findById(id);
        if (activity != null) {
            System.out.println("Activity schedules: " + activity.getSchedules());
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
