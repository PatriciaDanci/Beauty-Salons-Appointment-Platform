package com.se.service;

import com.se.model.Facility;
import com.se.model.Specialist;
import com.se.model.Salon;

import java.util.List;

public interface SalonService {
    List<Salon> getAllSalons();
    Salon getSalonById(Long id) throws Exception;
    Salon getSalonByName(String name) throws Exception;
    void createSalon(Salon salon);
    void updateSalon(Long id, Salon salon) throws Exception;
    void deleteSalon(Long id) throws Exception;
    List<Facility> getFacilitiesBySalon(Long salonId) throws Exception;
    void addFacilitiesToSalon(Long salonId, List<Long> facilityIds) throws Exception;
    List<Facility> getFacilitiesBySpecialistInSalon(Long salonId, Long specialistId) throws Exception;
    List<Specialist> getSpecialistsBySalon(Long salonId) throws Exception;
    List<String> getSpecialistNamesBySalon(Long salonId) throws Exception;
    List<String> getFacilityNamesBySalon(Long salonId) throws Exception;
    List<String> getFacilityNamesBySpecialistInSalon(Long salonId, Long specialistId) throws Exception;
    List<Salon> searchSalons(String keyword);
    void removeFacilitiesFromSalon(Long salonId, List<Long> facilityIds) throws Exception;
    void removeAllFacilitiesFromSalon(Long salonId) throws Exception;
    List<Specialist> getSpecialistsByFacilityInSalon(Long salonId, Long facilityId) throws Exception;
    List<String> getSpecialistNamesByFacilityInSalon(Long salonId, Long facilityId) throws Exception;
    Specialist getSpecialistByName(String name) throws Exception;
    Facility getFacilityByName(String name) throws Exception;
}


