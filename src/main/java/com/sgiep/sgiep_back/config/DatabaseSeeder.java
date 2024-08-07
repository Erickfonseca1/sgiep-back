package com.sgiep.sgiep_back.config;

import com.sgiep.sgiep_back.model.*;
import com.sgiep.sgiep_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Criação de usuários
        User user1 = new User();
        user1.setName("John Doe");

        User user2 = new User();
        user2.setName("Jane Doe");

        userRepository.saveAll(Arrays.asList(user1, user2));

        // Criação de atividades
        Activity activity1 = new Activity();
        activity1.setName("Natação");
        activity1.setDescription("Aulas de natação");
        activity1.setLocation("Piscina Municipal");

        Schedule schedule1 = new Schedule();
        schedule1.setDayOfWeek(DayOfWeek.TUESDAY);
        schedule1.setStartTime(LocalTime.of(19, 0));
        schedule1.setEndTime(LocalTime.of(21, 0));
        schedule1.setActivity(activity1);

        Schedule schedule2 = new Schedule();
        schedule2.setDayOfWeek(DayOfWeek.THURSDAY);
        schedule2.setStartTime(LocalTime.of(19, 0));
        schedule2.setEndTime(LocalTime.of(21, 0));
        schedule2.setActivity(activity1);

        activity1.setSchedules(Arrays.asList(schedule1, schedule2));

        Activity activity2 = new Activity();
        activity2.setName("Futebol");
        activity2.setDescription("Aulas de futebol");
        activity2.setLocation("Campo Municipal");

        Schedule schedule3 = new Schedule();
        schedule3.setDayOfWeek(DayOfWeek.MONDAY);
        schedule3.setStartTime(LocalTime.of(18, 0));
        schedule3.setEndTime(LocalTime.of(20, 0));
        schedule3.setActivity(activity2);

        Schedule schedule4 = new Schedule();
        schedule4.setDayOfWeek(DayOfWeek.WEDNESDAY);
        schedule4.setStartTime(LocalTime.of(18, 0));
        schedule4.setEndTime(LocalTime.of(20, 0));
        schedule4.setActivity(activity2);

        activity2.setSchedules(Arrays.asList(schedule3, schedule4));

        activityRepository.saveAll(Arrays.asList(activity1, activity2));

        // Criação de inscrições
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setEnrollmentDate(LocalDate.now());
        enrollment1.setUser(user1);
        enrollment1.setActivity(activity1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setEnrollmentDate(LocalDate.now());
        enrollment2.setUser(user2);
        enrollment2.setActivity(activity2);

        enrollmentRepository.saveAll(Arrays.asList(enrollment1, enrollment2));
    }
}
