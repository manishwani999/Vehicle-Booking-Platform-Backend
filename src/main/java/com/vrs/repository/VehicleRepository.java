package com.vrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vrs.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT COUNT(v) FROM Vehicle v")
    long countTotalVehicles();

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.status = true")
    long countAvailableVehicles();

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.status = false")
    long countBookedVehicles();
    
    List<Vehicle> findByAdminId(Long adminId);

    List<Vehicle> findByStatus(boolean status);

    List<Vehicle> findByRentPerDayLessThanEqual(double rent);

    @Query("""
        SELECT v FROM Vehicle v
        WHERE v.status = true AND LOWER(v.location.city) = LOWER(:city)
    """)
    List<Vehicle> findAvailableByCity(String city);
}

