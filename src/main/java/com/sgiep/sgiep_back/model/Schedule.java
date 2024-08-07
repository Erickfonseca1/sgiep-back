package com.sgiep.sgiep_back.model;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class Schedule {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
}
