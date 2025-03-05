package com.se.controller;

import com.se.model.ApiResponse;
import com.se.model.Facility;
import com.se.model.Specialist;
import com.se.service.SpecialistService;
import com.se.repository.FacilityRepository; // Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend connections
public class SpecialistController {

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private FacilityRepository facilityRepository; // Inject FacilityRepository

    // Get all specialists
    @GetMapping
    public List<Specialist> getAllSpecialists() {
        return specialistService.getAllSpecialists();
    }

    // Get specialist by ID
    @GetMapping("/{id}")
    public Specialist getSpecialistById(@PathVariable Long id) {
        return specialistService.getSpecialistById(id);
    }

    @GetMapping("/name")
    public Specialist getSpecialistByName(@RequestParam String name) {
        return specialistService.getSpecialistByName(name);
    }


    // Get specialists by salon ID
    @GetMapping("/salon/{salonId}")
    public List<Specialist> getSpecialistsBySalon(@PathVariable Long salonId) {
        return specialistService.getSpecialistsBySalon(salonId);
    }

    // Get only the names of facilities a specialist provides
    @GetMapping("/{id}/facilities/names")
    public List<String> getFacilityNamesBySpecialist(@PathVariable Long id) {
        Specialist specialist = specialistService.getSpecialistById(id);
        return specialist.getFacilities().stream()
                .map(Facility::getName) // Map each Facility object to its name
                .toList(); // Collect as a list of strings
    }

    // Get facilities for a specialist
    @GetMapping("/{id}/facilities")
    public List<Facility> getFacilitiesBySpecialist(@PathVariable Long id) {
        Specialist specialist = specialistService.getSpecialistById(id);
        return specialist.getFacilities(); // Return the list of facilities
    }

    // Search specialists by facility name
    @GetMapping("/search/facility")
    public List<Specialist> searchSpecialistsByFacility(@RequestParam String facilityName) {
        return specialistService.getSpecialistsByFacility(facilityName);
    }

    // Create a new specialist
    @PostMapping
    public ApiResponse createSpecialist(@RequestBody Specialist specialist) {
        specialistService.createSpecialist(specialist);
        return new ApiResponse("Specialist created successfully", true);
    }

    // Update a specialist
    @PutMapping("/{id}")
    public ApiResponse updateSpecialist(@PathVariable Long id, @RequestBody Specialist specialist) {
        specialistService.updateSpecialist(id, specialist);
        return new ApiResponse("Specialist updated successfully", true);
    }

    // Add facilities to a specialist
    @PutMapping("/{id}/facilities")
    public ApiResponse addFacilitiesToSpecialist(@PathVariable Long id, @RequestBody List<Long> facilityIds) {
        Specialist specialist = specialistService.getSpecialistById(id);
        List<Facility> facilities = facilityRepository.findAllById(facilityIds); // Fetch facilities from DB
        specialist.getFacilities().addAll(facilities); // Add new facilities
        specialistService.updateSpecialist(id, specialist); // Save updated specialist
        return new ApiResponse("Facilities added successfully to the specialist", true);
    }

    // Delete a specialist by ID
    @DeleteMapping("/{id}")
    public ApiResponse deleteSpecialist(@PathVariable Long id) {
        specialistService.deleteSpecialist(id);
        return new ApiResponse("Specialist deleted successfully", true);
    }

    // Remove all facilities from a specialist
    @DeleteMapping("/{id}/facilities")
    public ApiResponse removeAllFacilitiesFromSpecialist(@PathVariable Long id) {
        specialistService.removeAllFacilitiesFromSpecialist(id);
        return new ApiResponse("All facilities removed from the specialist", true);
    }


}
