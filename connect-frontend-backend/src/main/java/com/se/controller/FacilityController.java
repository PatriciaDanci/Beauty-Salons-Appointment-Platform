package com.se.controller;

import com.se.model.ApiResponse;
import com.se.model.Facility;
import com.se.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@CrossOrigin(origins = "http://localhost:3000")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    // Get all facilities
    @GetMapping
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    // Get facilities by name
    @GetMapping("/name")
    public List<Facility> getFacilitiesByName(@RequestParam String name) {
        return facilityService.getFacilitiesByName(name);
    }

    // Create a new facility
    @PostMapping
    public ApiResponse createFacility(@RequestBody Facility facility) {
        facilityService.createFacility(facility);
        return new ApiResponse("Facility created successfully", true);
    }

    // Update an existing facility
    @PutMapping("/{id}")
    public ApiResponse updateFacility(@PathVariable Long id, @RequestBody Facility facility) throws Exception {
        facilityService.updateFacility(id, facility);
        return new ApiResponse("Facility updated successfully", true);
    }

    // Delete a facility
    @DeleteMapping("/{id}")
    public ApiResponse deleteFacility(@PathVariable Long id) throws Exception {
        facilityService.deleteFacility(id);
        return new ApiResponse("Facility deleted successfully", true);
    }
}
