package com.se.controller;

import com.se.model.*;
import com.se.service.SalonService;
import com.se.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/salons")
@CrossOrigin(origins = "http://localhost:3000")
public class SalonController {

    @Autowired
    private SalonService salonService;

    @Autowired
    private ReviewService reviewService;

    // Get all salons
    @GetMapping
    public List<Salon> getAllSalons() {
        return salonService.getAllSalons();
    }

    // Get salon by ID
    @GetMapping("/{id}")
    public Salon getSalonById(@PathVariable Long id) throws Exception {
        return salonService.getSalonById(id);
    }

    // Get salon by name
    @GetMapping("/name")
    public Salon getSalonByName(@RequestParam String name) throws Exception {
        return salonService.getSalonByName(name);
    }

    // Get facilities by salon ID
    @GetMapping("/{id}/facilities")
    public List<Facility> getFacilitiesBySalon(@PathVariable Long id) throws Exception {
        return salonService.getFacilitiesBySalon(id);
    }

    // Get facilities with details (name, price, time) for a salon
    @GetMapping("/{salonId}/facilities/details")
    public List<Map<String, Object>> getFacilityDetailsBySalon(@PathVariable Long salonId) throws Exception {
        return salonService.getFacilitiesBySalon(salonId).stream().map(facility -> {
            Map<String, Object> facilityDetails = new HashMap<>();
            facilityDetails.put("name", facility.getName());
            facilityDetails.put("price", facility.getPrice());
            facilityDetails.put("time", facility.getTime());
            return facilityDetails;
        }).toList();
    }

    @GetMapping("/{salonId}/specialists/details")
    public List<Map<String, String>> getSpecialistDetailsBySalon(@PathVariable Long salonId) throws Exception {
        List<Specialist> specialists = salonService.getSpecialistsBySalon(salonId);
        return specialists.stream().map(specialist -> {
            Map<String, String> specialistDetails = new HashMap<>();
            specialistDetails.put("name", specialist.getName());
            specialistDetails.put("specialization", specialist.getSpecialization());
            return specialistDetails;
        }).toList();
    }


    // Get the names of all facilities in a salon
    @GetMapping("/{salonId}/facilities/names")
    public List<String> getFacilityNamesBySalon(@PathVariable Long salonId) throws Exception {
        return salonService.getFacilityNamesBySalon(salonId);
    }

    @GetMapping("/{salonId}/facilities/{facilityId}/specialists")
    public List<Specialist> getSpecialistsByFacilityInSalon(
            @PathVariable Long salonId, @PathVariable Long facilityId) throws Exception {
        return salonService.getSpecialistsByFacilityInSalon(salonId, facilityId);
    }

    // Get facilities provided by a specific specialist in a salon
    @GetMapping("/{salonId}/specialists/{specialistId}/facilities")
    public List<Facility> getFacilitiesBySpecialistInSalon(@PathVariable Long salonId, @PathVariable Long specialistId) throws Exception {
        return salonService.getFacilitiesBySpecialistInSalon(salonId, specialistId);
    }

    @GetMapping ("/{salonId}/specialists/{specialistId}/facilities/names")
    public List<String> getFacilityNamesBySpecialistInSalon(@PathVariable Long salonId, @PathVariable Long specialistId) throws Exception {
        return salonService.getFacilityNamesBySpecialistInSalon(salonId, specialistId);
    }

    // Get all specialists in a salon without facilities
    @GetMapping("/{salonId}/specialists")
    public List<Specialist> getSpecialistsBySalon(@PathVariable Long salonId) throws Exception {
        return salonService.getSpecialistsBySalon(salonId);
    }

    /*// Search salons by name or facilities
    @GetMapping("/search")
    public List<Salon> searchSalons(@RequestParam String keyword) {
        return salonService.searchSalons(keyword);
    }*/

    @GetMapping("/search")
    public List<Map<String, Object>> searchSalons(@RequestParam String keyword) {
        List<Salon> salons = salonService.searchSalons(keyword);

        return salons.stream().map(salon -> {
            Map<String, Object> salonDetails = new HashMap<>();
            salonDetails.put("id", salon.getId());
            salonDetails.put("name", salon.getName());
            salonDetails.put("address", salon.getAddress());
            salonDetails.put("city", salon.getCity());
            salonDetails.put("pictureUrl", salon.getPictureUrl());

            // Calculate average rating
            List<Review> reviews = reviewService.getReviewsBySalon(salon.getId());
            double avgRating = reviews.isEmpty()
                    ? 0.0
                    : reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

            salonDetails.put("averageRating", avgRating > 0.0 ? String.format("%.1f", avgRating) : "N/A");

            return salonDetails;
        }).toList();
    }


    @GetMapping("/{salonId}/facilities/{facilityId}/specialists/names")
    public List<String> getSpecialistNamesByFacilityInSalon(
            @PathVariable Long salonId, @PathVariable Long facilityId) throws Exception {
        return salonService.getSpecialistNamesByFacilityInSalon(salonId, facilityId);
    }

    // Get the names of all specialists in a salon
    @GetMapping("/{salonId}/specialists/names")
    public List<String> getSpecialistNamesBySalon(@PathVariable Long salonId) throws Exception {
        return salonService.getSpecialistNamesBySalon(salonId);
    }

    @GetMapping("/{salonId}/specialists/name")
    public Specialist getSpecialistByName(@PathVariable Long salonId, @RequestParam String name) throws Exception {
        Specialist specialist = salonService.getSpecialistByName(name);
        if (!specialist.getSalon().getId().equals(salonId)) {
            throw new Exception("Specialist does not belong to the specified salon");
        }
        return specialist;
    }

    @GetMapping("/{salonId}/facilities/name")
    public Facility getFacilityByName(@PathVariable Long salonId, @RequestParam String name) throws Exception {
        Facility facility = salonService.getFacilityByName(name);
        if (!facility.getSalons().stream().anyMatch(salon -> salon.getId().equals(salonId))) {
            throw new Exception("Facility does not belong to the specified salon");
        }
        return facility;
    }

    // Add facilities to a salon
    @PutMapping("/{id}/facilities")
    public ApiResponse addFacilitiesToSalon(@PathVariable Long id, @RequestBody List<Long> facilityIds) throws Exception {
        salonService.addFacilitiesToSalon(id, facilityIds);
        return new ApiResponse("Facilities added successfully", true);
    }

    // Create a new salon
    @PostMapping
    public ApiResponse createSalon(@RequestBody Salon salon) {
        salonService.createSalon(salon);
        return new ApiResponse("Salon created successfully", true);
    }

    // Update an existing salon
    @PutMapping("/{id}")
    public ApiResponse updateSalon(@PathVariable Long id, @RequestBody Salon salon) throws Exception {
        salonService.updateSalon(id, salon);
        return new ApiResponse("Salon updated successfully", true);
    }

    @PutMapping("/{salonId}/facilities/remove")
    public ApiResponse removeFacilitiesFromSalon(@PathVariable Long salonId, @RequestBody List<Long> facilityIds) throws Exception {
        salonService.removeFacilitiesFromSalon(salonId, facilityIds);
        return new ApiResponse("Facilities removed successfully", true);
    }

    @PutMapping("/{salonId}/facilities/removeAll")
    public ApiResponse removeAllFacilitiesFromSalon(@PathVariable Long salonId) throws Exception {
        salonService.removeAllFacilitiesFromSalon(salonId);
        return new ApiResponse("All facilities removed successfully", true);
    }

    // Delete a salon
    @DeleteMapping("/{id}")
    public ApiResponse deleteSalon(@PathVariable Long id) throws Exception {
        salonService.deleteSalon(id);
        return new ApiResponse("Salon deleted successfully", true);
    }
}