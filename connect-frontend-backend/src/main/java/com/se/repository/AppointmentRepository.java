package com.se.repository;

import com.se.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId);

    List<Appointment> findBySalonId(Long salonId);

    List<Appointment> findBySpecialistId(Long specialistId);

    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
}
