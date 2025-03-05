package com.se.controller;

import com.se.model.ApiResponse;
import com.se.model.Appointment;
import com.se.model.AppointmentDTO;
import com.se.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Get all appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Get appointments by user
    @GetMapping("/user/{userId}")
    public List<AppointmentDTO> getAppointmentsByUser(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUserDTO(userId);
    }

    // Get appointments by salon
    @GetMapping("/salon/{id}")
    public List<Appointment> getAppointmentsBySalon(@PathVariable Long id) {
        return appointmentService.getAppointmentsBySalon(id);
    }

    // Get appointments by specialist
    @GetMapping("/specialist/{id}")
    public List<Appointment> getAppointmentsBySpecialist(@PathVariable Long id) {
        return appointmentService.getAppointmentsBySpecialist(id);
    }

    // Get appointments within a time range
    @GetMapping("/time-range")
    public List<Appointment> getAppointmentsByTimeRange(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return appointmentService.getAppointmentsByTimeRange(startTime, endTime);
    }

    // Create a new appointment
    @PostMapping
    public ApiResponse createAppointment(@RequestBody Appointment appointment) {
        appointmentService.createAppointment(appointment);
        return new ApiResponse("Appointment created successfully", true);
    }

    @PostMapping("/book")
    public ApiResponse bookAppointment(@RequestBody Map<String, Object> appointmentDetails) {
        try {
            // Extract parameters from the JSON body
            Long specialistId = Long.valueOf(appointmentDetails.get("specialistId").toString());
            Long facilityId = Long.valueOf(appointmentDetails.get("facilityId").toString());
            Long salonId = Long.valueOf(appointmentDetails.get("salonId").toString());
            Long userId = Long.valueOf(appointmentDetails.get("userId").toString());
            LocalDateTime appointmentTime = LocalDateTime.parse(appointmentDetails.get("appointmentTime").toString());

            // Debugging log
            System.out.println("Received Booking Request:");
            System.out.println("Specialist ID: " + specialistId);
            System.out.println("Facility ID: " + facilityId);
            System.out.println("Salon ID: " + salonId);
            System.out.println("User ID: " + userId);
            System.out.println("Appointment Time: " + appointmentTime);

            // Call service to book the appointment
            appointmentService.bookAppointment(specialistId, facilityId, salonId, userId, appointmentTime);

            return new ApiResponse("Appointment booked successfully!", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Failed to book appointment: " + e.getMessage(), false);
        }
    }

    // Update an existing appointment
    @PutMapping("/{id}")
    public ApiResponse updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) throws Exception {
        appointmentService.updateAppointment(id, appointment);
        return new ApiResponse("Appointment updated successfully", true);
    }

    // Delete an appointment
    @DeleteMapping("/{id}")
    public ApiResponse deleteAppointment(@PathVariable Long id) throws Exception {
        appointmentService.deleteAppointment(id);
        return new ApiResponse("Appointment deleted successfully", true);
    }
}
