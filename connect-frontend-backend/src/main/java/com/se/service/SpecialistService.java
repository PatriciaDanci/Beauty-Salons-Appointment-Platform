package com.se.service;

import com.se.model.Specialist;

import java.util.List;

public interface SpecialistService {
    List<Specialist> getAllSpecialists();
    Specialist getSpecialistById(Long id);
    List<Specialist> getSpecialistsBySalon(Long salonId);
    Specialist createSpecialist(Specialist specialist);
    Specialist updateSpecialist(Long id, Specialist specialist);
    void deleteSpecialist(Long id);
    List<Specialist> getSpecialistsByFacility(String facilityName);
    void removeAllFacilitiesFromSpecialist(Long specialistId);
    Specialist getSpecialistByName(String name);
}
