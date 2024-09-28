package com.sgiep.sgiep_back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sgiep.sgiep_back.model.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "activities")
public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonIgnoreProperties("activitiesAsProfessor")
    private User professor;

    @ManyToMany
    @JoinTable(
            name = "activity_student",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnoreProperties("activitiesAsStudent")
    private List<User> students;  // Agora é User, e o role será "CITIZEN"

    // Getters and Setters


}
