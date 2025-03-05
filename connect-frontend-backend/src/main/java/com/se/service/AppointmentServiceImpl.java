package com.se.service;

import com.se.model.*;
import com.se.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private SalonService salonService;

    @Autowired
    private UserService userService;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByUser(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByUserDTO(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);

        return appointments.stream().map(appointment -> new AppointmentDTO(
                appointment.getId(),
                appointment.getFacility() != null ? appointment.getFacility().getName() : "Unknown Facility",
                appointment.getSalon() != null ? appointment.getSalon().getName() : "Unknown Salon",
                appointment.getSpecialist() != null ? appointment.getSpecialist().getName() : "Unknown Specialist",
                appointment.getAppointmentTime()
        )).collect(Collectors.toList());
    }


    @Override
    public List<Appointment> getAppointmentsBySalon(Long salonId) {
        return appointmentRepository.findBySalonId(salonId);
    }

    @Override
    public List<Appointment> getAppointmentsBySpecialist(Long specialistId) {
        return appointmentRepository.findBySpecialistId(specialistId);
    }

    @Override
    public List<Appointment> getAppointmentsByTimeRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentTimeBetween(start, end);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        if (appointment.getFacility() == null || appointment.getFacility().getId() == null) {
            throw new IllegalArgumentException("Facility cannot be null or missing");
        }
        return appointmentRepository.save(appointment);
    }


    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) throws Exception {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new Exception("Appointment not found"));

        existingAppointment.setUser(appointment.getUser());
        existingAppointment.setSalon(appointment.getSalon());
        existingAppointment.setSpecialist(appointment.getSpecialist());
        existingAppointment.setFacility(appointment.getFacility());
        existingAppointment.setAppointmentTime(appointment.getAppointmentTime());

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(Long id) throws Exception {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new Exception("Appointment not found"));
        appointmentRepository.delete(appointment);
    }

    @Override
    public void bookAppointment(Long specialistId, Long facilityId, Long salonId, Long userId, LocalDateTime appointmentTime) throws Exception {
        // Validate time slot availability
        List<Appointment> existingAppointments = appointmentRepository.findBySpecialistId(specialistId);
        for (Appointment existing : existingAppointments) {
            if (existing.getAppointmentTime().equals(appointmentTime)) {
                throw new Exception("Time slot is already booked for this specialist.");
            }
        }

        // Fetch related entities
        Specialist specialist = specialistService.getSpecialistById(specialistId);
        Facility facility = facilityService.getFacilityById(facilityId);
        Salon salon = salonService.getSalonById(salonId);
        User user = userService.getUserById(userId);

        // Create and save the new appointment
        Appointment newAppointment = new Appointment(appointmentTime, facility, salon, specialist, user);
        appointmentRepository.save(newAppointment);
    }

}
