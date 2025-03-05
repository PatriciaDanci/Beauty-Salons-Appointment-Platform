package com.se.repository;

import com.se.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    @Query("SELECT s FROM Specialist s WHERE LOWER(s.name) = LOWER(REPLACE(:name, '-', ' '))")
    Optional<Specialist> findByName(@Param("name") String name);

    List<Specialist> findBySalonId(Long salonId);
    List<Specialist> findByFacilities_NameContainingIgnoreCase(String facilityName);
}
