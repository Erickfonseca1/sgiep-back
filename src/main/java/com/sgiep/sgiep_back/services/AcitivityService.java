package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Schedule;
import com.sgiep.sgiep_back.model.User;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import com.sgiep.sgiep_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.time.LocalTime;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class AcitivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Activity findById(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.orElse(null);
    }

    @CacheEvict(value = {"activities", "activities:list"}, allEntries = true)
    public Activity save(Activity activity) {
        if (activity.getSchedules() != null) {
            for (Schedule schedule : activity.getSchedules()) {
                schedule.setActivity(activity);
            }
        }
        return activityRepository.save(activity);
    }

    @Cacheable(value = "activities:list", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Activity> findAll(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    public List<Activity> findAllOrderedBySchedule() {
        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
            .sorted(Comparator.comparing(activity -> activity.getSchedules().get(0).getStartTime()))
            .collect(Collectors.toList());
    }

    public List<Activity> filterByTime(LocalTime startTime, LocalTime endTime) {
        return activityRepository.findActivitiesByTimeRange(startTime, endTime);
    }

    public List<Activity> findActivitiesByProfessor(Long professorId) {
        return activityRepository.findByProfessorId(professorId);
    }

    public List<Activity> filterActivitiesByProfessor(Long professorId, String name, String location) {
        return activityRepository.findByProfessorIdAndFilters(professorId, name, location);
    }

    public List<Activity> findCitizenActivities(Long citizenId) {
        return activityRepository.findActivitiesByCitizenId(citizenId);
    }

    public List<Activity> filterCitizenAgendaByDate(Long citizenId, LocalDate startDate, LocalDate endDate) {
        List<Activity> activities = activityRepository.findActivitiesByCitizenId(citizenId);
    
        return activities.stream()
            .filter(activity -> activity.getSchedules().stream().anyMatch(schedule -> 
                (startDate == null || schedule.getDayOfWeek().getValue() >= startDate.getDayOfWeek().getValue()) &&
                (endDate == null || schedule.getDayOfWeek().getValue() <= endDate.getDayOfWeek().getValue())
            ))
            .collect(Collectors.toList());
    }

    public List<Activity> findProfessorActivities(Long professorId) {
        return activityRepository.findActivitiesByProfessorId(professorId);
    }

    public List<Activity> filterProfessorAgendaByDate(Long professorId, LocalDate startDate, LocalDate endDate) {
        List<Activity> activities = activityRepository.findActivitiesByProfessorId(professorId);
    
        return activities.stream()
            .filter(activity -> activity.getSchedules().stream().anyMatch(schedule ->
                (startDate == null || schedule.getDayOfWeek().getValue() >= startDate.getDayOfWeek().getValue()) &&
                (endDate == null || schedule.getDayOfWeek().getValue() <= endDate.getDayOfWeek().getValue())
            ))
            .collect(Collectors.toList());
    }

    @CacheEvict(value = {"activities", "activities:list"}, allEntries = true)
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    public List<Activity> findCitizensByActivity(Long id) {
        return activityRepository.findCitizensByActivity(id);
    }
}
