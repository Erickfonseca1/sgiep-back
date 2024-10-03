package com.sgiep.sgiep_back.dto;

import java.util.List;

public class ActivityDTO {
    private String name;
    private String description;
    private String location;
    private int maxVacancies;
    private Long professorId;
    private List<ScheduleDTO> schedules;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxVacancies() {
        return maxVacancies;
    }

    public void setMaxVacancies(int maxVacancies) {
        this.maxVacancies = maxVacancies;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}