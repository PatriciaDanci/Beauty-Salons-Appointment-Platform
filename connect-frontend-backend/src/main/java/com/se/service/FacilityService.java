package com.se.service;

import com.se.model.Facility;

import java.util.List;

public interface FacilityService {
    List<Facility> getAllFacilities();

    Facility createFacility(Facility facility);

    Facility updateFacility(Long id, Facility facility) throws Exception;

    void deleteFacility(Long id) throws Exception;

    List<Facility> getFacilitiesByName(String name); // New method

    Facility getFacilityById(Long id); // New method
}
