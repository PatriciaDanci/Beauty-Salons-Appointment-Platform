package com.se.model;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long id;
    private String facilityName;
    private String salonName;
    private String specialistName;
    private LocalDateTime appointmentTime;

    // Constructor
    public AppointmentDTO(Long id, String facilityName, String salonName, String specialistName, LocalDateTime appointmentTime) {
        this.id = id;
        this.facilityName = facilityName;
        this.salonName = salonName;
        this.specialistName = specialistName;
        this.appointmentTime = appointmentTime;
    }

    // Getters and Setters
    // ...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


}
