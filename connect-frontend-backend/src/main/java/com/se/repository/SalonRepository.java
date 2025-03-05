package com.se.repository;

import com.se.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalonRepository extends JpaRepository<Salon, Long> {

    @Query("SELECT s FROM Salon s WHERE LOWER(s.name) = LOWER(REPLACE(:name, '-', ' '))")
    Optional<Salon> findByName(@Param("name") String name);

    List<Salon> findByNameContainingIgnoreCase(String name);

}
