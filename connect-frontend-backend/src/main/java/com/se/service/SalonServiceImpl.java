package com.se.service;

import com.se.model.Facility;
import com.se.model.Salon;
import com.se.model.Specialist;
import com.se.repository.SalonRepository;
import com.se.repository.FacilityRepository;
import com.se.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SalonServiceImpl implements SalonService {

    @Autowired
    private SalonRepository salonRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long id) throws Exception {
        return salonRepository.findById(id).orElseThrow(() -> new Exception("Salon not found"));
    }

    @Override
    public Salon getSalonByName(String name) throws Exception {
        return salonRepository.findByName(name)
                .orElseThrow(() -> new Exception("Salon with name '" + name + "' not found"));
    }

    @Override
    public void createSalon(Salon salon) {
        salonRepository.save(salon);
    }

    @Override
    public Specialist getSpecialistByName(String name) throws Exception {
        return specialistRepository.findByName(name)
                .orElseThrow(() -> new Exception("Specialist with name '" + name + "' not found"));
    }

    @Override
    public Facility getFacilityByName(String name) throws Exception {
        return facilityRepository.findByName(name)
                .orElseThrow(() -> new Exception("Facility with name '" + name + "' not found"));
    }


    @Override
    public void updateSalon(Long id, Salon salon) throws Exception {
        Salon existingSalon = salonRepository.findById(id)
                .orElseThrow(() -> new Exception("Salon not found"));

        existingSalon.setName(salon.getName());
        existingSalon.setAddress(salon.getAddress());
        existingSalon.setCity(salon.getCity());

        salonRepository.save(existingSalon);
    }

    @Override
    public void deleteSalon(Long id) throws Exception {
        Salon salon = salonRepository.findById(id)
                .orElseThrow(() -> new Exception("Salon not found"));

        salonRepository.delete(salon);
    }

    @Override
    public List<Facility> getFacilitiesBySalon(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found"));
        return salon.getFacilities();
    }

    @Override
    public void addFacilitiesToSalon(Long salonId, List<Long> facilityIds) throws Exception {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found"));
        List<Facility> facilities = facilityRepository.findAllById(facilityIds); // Assume facilityRepository exists
        salon.getFacilities().addAll(facilities);
        salonRepository.save(salon);
    }

    @Override
    public List<Facility> getFacilitiesBySpecialistInSalon(Long salonId, Long specialistId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Find the specialist in the salon
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new Exception("Specialist not found with ID: " + specialistId));

        // Ensure the specialist belongs to the specified salon
        if (!specialist.getSalon().getId().equals(salonId)) {
            throw new Exception("Specialist does not belong to the specified salon");
        }

        // Return the facilities provided by the specialist
        return specialist.getFacilities();
    }

    @Override
    public List<Specialist> getSpecialistsBySalon(Long salonId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Return the list of specialists working at the salon
        return salon.getSpecialists();
    }

    @Override
    public List<String> getSpecialistNamesBySalon(Long salonId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Fetch only the names of specialists
        return salon.getSpecialists()
                .stream()
                .map(Specialist::getName) // Map each Specialist to their name
                .toList(); // Collect as a list
    }

    @Override
    public List<String> getFacilityNamesBySalon(Long salonId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Fetch only the names of facilities
        return salon.getFacilities()
                .stream()
                .map(Facility::getName) // Map each Facility to its name
                .toList(); // Collect as a list
    }

    @Override
    public List<String> getFacilityNamesBySpecialistInSalon(Long salonId, Long specialistId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Find the specialist in the salon
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new Exception("Specialist not found with ID: " + specialistId));

        // Ensure the specialist belongs to the specified salon
        if (!specialist.getSalon().getId().equals(salonId)) {
            throw new Exception("Specialist does not belong to the specified salon");
        }

        // Fetch only the names of facilities provided by the specialist
        return specialist.getFacilities()
                .stream()
                .map(Facility::getName) // Map each Facility to its name
                .toList(); // Collect as a list
    }

    @Override
    public List<Salon> searchSalons(String keyword) {
        // Find salons by name
        List<Salon> salonsByName = salonRepository.findByNameContainingIgnoreCase(keyword);

        // Find facilities by name
        List<Facility> facilities = facilityRepository.findByNameContainingIgnoreCase(keyword);

        // Get salons providing these facilities
        Set<Salon> salonsByFacility = new HashSet<>();
        for (Facility facility : facilities) {
            salonsByFacility.addAll(facility.getSalons());
        }

        // Combine and return unique results
        salonsByName.addAll(salonsByFacility);
        return new ArrayList<>(new HashSet<>(salonsByName));
    }

    @Override
    public void removeFacilitiesFromSalon(Long salonId, List<Long> facilityIds) throws Exception {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found"));

        // Filter out facilities with the given IDs
        salon.getFacilities().removeIf(facility -> facilityIds.contains(facility.getId()));

        salonRepository.save(salon);
    }

    @Override
    public void removeAllFacilitiesFromSalon(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found"));

        // Clear all facilities
        salon.getFacilities().clear();

        salonRepository.save(salon);
    }

    @Override
    public List<Specialist> getSpecialistsByFacilityInSalon(Long salonId, Long facilityId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Ensure the facility exists
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new Exception("Facility not found with ID: " + facilityId));

        // Get all specialists in the salon who provide this facility
        return salon.getSpecialists().stream()
                .filter(specialist -> specialist.getFacilities().contains(facility))
                .toList();
    }

    @Override
    public List<String> getSpecialistNamesByFacilityInSalon(Long salonId, Long facilityId) throws Exception {
        // Ensure the salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new Exception("Salon not found with ID: " + salonId));

        // Ensure the facility exists
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new Exception("Facility not found with ID: " + facilityId));

        // Get all specialists in the salon who provide this facility and map their names
        return salon.getSpecialists().stream()
                .filter(specialist -> specialist.getFacilities().contains(facility))
                .map(Specialist::getName) // Map to names only
                .toList();
    }


}
