package com.sgiep.sgiep_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "citizens")
public class Citizen extends User{

    @ManyToMany(mappedBy = "students")
    @JsonIgnoreProperties("students")
    private List<Activity> activities;

    // Getters and Setters

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
