package com.se.repository;

import com.se.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query("SELECT f FROM Facility f WHERE LOWER(f.name) = LOWER(REPLACE(:name, '-', ' '))")
    Optional<Facility> findByName(@Param("name") String name);

    List<Facility> findByNameContainingIgnoreCase(String name);
}
