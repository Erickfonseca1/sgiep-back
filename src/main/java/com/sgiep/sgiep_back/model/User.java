package com.sgiep.sgiep_back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String role;  // Ex: "PROFESSOR", "ADMIN", "CITIZEN" AND "MANAGER"
    private boolean active;

    @OneToMany(mappedBy = "professor")
    @JsonIgnoreProperties("professor")
    private List<Activity> activitiesAsProfessor;

    @ManyToMany(mappedBy = "students")
    @JsonIgnoreProperties("students")
    private List<Activity> activitiesAsStudent;

    // Getters and Setters
    public List<Activity> getActivitiesAsProfessor() {
        return activitiesAsProfessor;
    }

    public void setActivitiesAsProfessor(List<Activity> activitiesAsProfessor) {
        this.activitiesAsProfessor = activitiesAsProfessor;
    }

    public List<Activity> getActivitiesAsStudent() {
        return activitiesAsStudent;
    }

    public void setActivitiesAsStudent(List<Activity> activitiesAsStudent) {
        this.activitiesAsStudent = activitiesAsStudent;
    }
}
