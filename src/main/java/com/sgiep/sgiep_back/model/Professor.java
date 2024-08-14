package com.sgiep.sgiep_back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="professors")
public class Professor extends User{
    @OneToMany(mappedBy = "professor")
    @JsonIgnoreProperties("properties")
    private List<Activity> activities;

    // Getters and Setters

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
