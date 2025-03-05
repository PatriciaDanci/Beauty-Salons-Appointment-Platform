package com.se.service;

import com.se.model.Appointment;
import com.se.model.AppointmentDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByUser(Long userId);

    List<Appointment> getAppointmentsBySalon(Long salonId);

    List<Appointment> getAppointmentsBySpecialist(Long specialistId);

    List<Appointment> getAppointmentsByTimeRange(LocalDateTime start, LocalDateTime end);

    Appointment createAppointment(Appointment appointment);

    Appointment updateAppointment(Long id, Appointment appointment) throws Exception;

    void deleteAppointment(Long id) throws Exception;
    void bookAppointment(Long specialistId, Long facilityId, Long salonId, Long userId, LocalDateTime appointmentTime) throws Exception;
    List<AppointmentDTO> getAppointmentsByUserDTO(Long userId);
}
