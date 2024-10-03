package com.sgiep.sgiep_back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "schedules")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @JsonBackReference
    private Activity activity;

    //Getters and Setters


    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
