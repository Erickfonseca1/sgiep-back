package com.sgiep.sgiep_back.service;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.Schedule;
import com.sgiep.sgiep_back.repository.ActivityRepository;
import com.sgiep.sgiep_back.services.AcitivityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AcitivityServiceTest {

    @InjectMocks
    private AcitivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para o método findAll()
    @Test
    public void testFindAll() {
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(2L);

        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> activities = activityService.findAll();

        assertNotNull(activities);
        assertEquals(2, activities.size());
        verify(activityRepository, times(1)).findAll();
    }

    // Teste para o método findById()
    @Test
    public void testFindById() {
        Activity activity = new Activity();
        activity.setId(1L);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        Activity result = activityService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(activityRepository, times(1)).findById(1L);
    }

    // Teste para o método save()
    @Test
    public void testSave() {
        Activity activity = new Activity();
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity savedActivity = activityService.save(activity);

        assertNotNull(savedActivity);
        verify(activityRepository, times(1)).save(activity);
    }

    // Teste para o método findAll(Pageable)
    @Test
    public void testFindAllPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        Activity activity = new Activity();
        Page<Activity> activityPage = new PageImpl<>(Arrays.asList(activity), pageable, 1);

        when(activityRepository.findAll(pageable)).thenReturn(activityPage);

        Page<Activity> result = activityService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(activityRepository, times(1)).findAll(pageable);
    }

    // Teste para o método findAllOrderedBySchedule()
    @Test
    public void testFindAllOrderedBySchedule() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();

        Schedule schedule1 = new Schedule();
        schedule1.setStartTime(LocalTime.of(10, 0));
        activity1.setSchedules(Arrays.asList(schedule1));

        Schedule schedule2 = new Schedule();
        schedule2.setStartTime(LocalTime.of(8, 0));
        activity2.setSchedules(Arrays.asList(schedule2));

        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> activities = activityService.findAllOrderedBySchedule();

        assertNotNull(activities);
        assertEquals(2, activities.size());
        assertEquals(activity2, activities.get(0)); // Atividade com horário de início mais cedo
        verify(activityRepository, times(1)).findAll();
    }

    // Teste para o método filterByTime()
    @Test
    public void testFilterByTime() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        Activity activity = new Activity();

        when(activityRepository.findActivitiesByTimeRange(startTime, endTime)).thenReturn(Arrays.asList(activity));

        List<Activity> activities = activityService.filterByTime(startTime, endTime);

        assertNotNull(activities);
        assertEquals(1, activities.size());
        verify(activityRepository, times(1)).findActivitiesByTimeRange(startTime, endTime);
    }

    // Teste para o método findActivitiesByProfessor()
    @Test
    public void testFindActivitiesByProfessor() {
        Activity activity = new Activity();

        when(activityRepository.findByProfessorId(1L)).thenReturn(Arrays.asList(activity));

        List<Activity> activities = activityService.findActivitiesByProfessor(1L);

        assertNotNull(activities);
        assertEquals(1, activities.size());
        verify(activityRepository, times(1)).findByProfessorId(1L);
    }

    // Teste para o método deleteActivity()
    @Test
    public void testDeleteActivity() {
        doNothing().when(activityRepository).deleteById(1L);

        activityService.deleteActivity(1L);

        verify(activityRepository, times(1)).deleteById(1L);
    }
}

