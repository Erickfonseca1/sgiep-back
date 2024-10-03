package com.sgiep.sgiep_back.dto;

public class ScheduleDTO {
  private String dayOfWeek;
  private String startTime;
  private String endTime;

  // Getters e Setters
  public String getDayOfWeek() {
      return dayOfWeek;
  }

  public void setDayOfWeek(String dayOfWeek) {
      this.dayOfWeek = dayOfWeek;
  }

  public String getStartTime() {
      return startTime;
  }

  public void setStartTime(String startTime) {
      this.startTime = startTime;
  }

  public String getEndTime() {
      return endTime;
  }

  public void setEndTime(String endTime) {
      this.endTime = endTime;
  }
}
