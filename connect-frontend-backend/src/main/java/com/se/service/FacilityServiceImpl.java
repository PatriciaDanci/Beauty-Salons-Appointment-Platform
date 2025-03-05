package com.se.service;

import com.se.model.Facility;
import com.se.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    public Facility createFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public Facility updateFacility(Long id, Facility facility) throws Exception {
        Facility existingFacility = facilityRepository.findById(id)
                .orElseThrow(() -> new Exception("Facility not found"));
        existingFacility.setName(facility.getName());
        existingFacility.setDescription(facility.getDescription());
        existingFacility.setPrice(facility.getPrice());
        return facilityRepository.save(existingFacility);
    }

    @Override
    public void deleteFacility(Long id) throws Exception {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new Exception("Facility not found"));
        facilityRepository.delete(facility);
    }

    @Override
    public List<Facility> getFacilitiesByName(String name) {
        return facilityRepository.findByNameContainingIgnoreCase(name); // New method
    }

    @Override
    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id).orElse(null); // New method
    }
}
