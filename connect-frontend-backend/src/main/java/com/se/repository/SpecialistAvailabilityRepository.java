package com.se.repository;

import com.se.model.SpecialistAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpecialistAvailabilityRepository extends JpaRepository<SpecialistAvailability, Long> {
    List<SpecialistAvailability> findBySpecialistIdAndDate(Long specialistId, LocalDate date);
}
