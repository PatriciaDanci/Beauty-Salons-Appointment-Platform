package com.se.service;

import com.se.model.SpecialistAvailability;
import com.se.repository.SpecialistAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SpecialistAvailabilityService {

    @Autowired
    private SpecialistAvailabilityRepository availabilityRepository;

    public List<SpecialistAvailability> getAvailability(Long specialistId, LocalDate date) {
        return availabilityRepository.findBySpecialistIdAndDate(specialistId, date);
    }

    public SpecialistAvailability createAvailability(SpecialistAvailability availability) {
        return availabilityRepository.save(availability);
    }

    public List<SpecialistAvailability> getAvailabilityBySpecialistAndDate(Long specialistId, String date) {
        LocalDate parsedDate = LocalDate.parse(date); // Parse the date
        return availabilityRepository.findBySpecialistIdAndDate(specialistId, parsedDate);
    }

    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }
}
