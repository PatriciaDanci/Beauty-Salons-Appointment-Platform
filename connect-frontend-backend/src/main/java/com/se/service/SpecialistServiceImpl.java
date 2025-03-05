package com.se.service;

import com.se.model.Facility;
import com.se.model.Salon;
import com.se.model.Specialist;
import com.se.repository.FacilityRepository;
import com.se.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private FacilityRepository facilityRepository; // Inject FacilityRepository

    @Override
    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    @Override
    public Specialist getSpecialistById(Long id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialist not found with id: " + id));
    }

    @Override
    public List<Specialist> getSpecialistsBySalon(Long salonId) {
        return specialistRepository.findBySalonId(salonId);
    }

    @Override
    public Specialist createSpecialist(Specialist specialist) {
        return specialistRepository.save(specialist);
    }

    @Override
    public Specialist updateSpecialist(Long id, Specialist specialistDetails) {
        Specialist existingSpecialist = getSpecialistById(id);

        // Update fields
        existingSpecialist.setName(specialistDetails.getName());
        existingSpecialist.setSpecialization(specialistDetails.getSpecialization());
        existingSpecialist.setSalon(specialistDetails.getSalon());
        existingSpecialist.setFacilities(specialistDetails.getFacilities());

        return specialistRepository.save(existingSpecialist);
    }

    @Override
    public void deleteSpecialist(Long id) {
        Specialist existingSpecialist = getSpecialistById(id);
        specialistRepository.delete(existingSpecialist);
    }

    @Override
    public List<Specialist> getSpecialistsByFacility(String facilityName) {
        return specialistRepository.findByFacilities_NameContainingIgnoreCase(facilityName);
    }

    @Override
    public void removeAllFacilitiesFromSpecialist(Long specialistId) {
        Specialist specialist = getSpecialistById(specialistId); // Fetch the specialist
        specialist.getFacilities().clear(); // Clear the facilities list
        specialistRepository.save(specialist); // Save the updated specialist
    }

    @Override
    public Specialist getSpecialistByName(String name) {
        return specialistRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Specialist not found with name: " + name));
    }

}
