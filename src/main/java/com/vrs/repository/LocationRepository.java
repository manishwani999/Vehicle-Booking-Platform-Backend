package com.vrs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vrs.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCity(String city);
}
