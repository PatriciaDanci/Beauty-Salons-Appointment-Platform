package com.se.controller;

import com.se.model.Specialist;
import com.se.model.SpecialistAvailability;
import com.se.service.SpecialistAvailabilityService;
import com.se.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@CrossOrigin(origins = "http://localhost:3000")
public class SpecialistAvailabilityController {

    @Autowired
    private SpecialistAvailabilityService availabilityService;

    @Autowired
    private SpecialistService specialistService;

    // Fetch availability for a specialist on a specific date
    @GetMapping("/{specialistId}/availability")
    public List<SpecialistAvailability> getAvailability(@PathVariable Long specialistId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date); // Parse date from query parameter
        return availabilityService.getAvailability(specialistId, localDate);
    }

    @GetMapping("/availability/name")
    public List<SpecialistAvailability> getSpecialistAvailabilityByName(
            @RequestParam String name,
            @RequestParam String date) throws Exception {
        Specialist specialist = specialistService.getSpecialistByName(name); // Fetch specialist by name
        if (specialist == null) {
            throw new Exception("Specialist not found with name: " + name);
        }
        System.out.println("Fetched Specialist ID: " + specialist.getId());

        LocalDate localDate = LocalDate.parse(date.trim()); // Parse date from query parameter
        //return availabilityService.getAvailability(specialist.getId(), localDate); // Search by
        List<SpecialistAvailability> availabilities = availabilityService.getAvailability(specialist.getId(), localDate);
        System.out.println("Fetched Availability: " + availabilities);
        return availabilities;

    }

    // Create availability for a specialist
    @PostMapping("/{specialistId}/availability")
    public SpecialistAvailability createAvailability(@PathVariable Long specialistId, @RequestBody SpecialistAvailability availability) {
        availability.setId(specialistId);
        return availabilityService.createAvailability(availability);
    }

    // Delete availability
    @DeleteMapping("/availability/{id}")
    public void deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
    }
}
