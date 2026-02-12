package com.vrs.service;

import java.util.List;

import com.vrs.dto.VehicleResponse;
import com.vrs.model.Vehicle;

public interface VehicleService {

//    // ADMIN
//    Vehicle addVehicleForAdmin(Long adminId, Vehicle vehicle);
//    List<Vehicle> getVehiclesByAdmin(Long adminId);
//    void deleteVehicle(Long vehicleId);
//
//    // PUBLIC / USER
//    List<Vehicle> getAllVehicles();
//    Vehicle getVehicleById(Long id);
//    List<Vehicle> getVehiclesByAvailability(boolean status);
//    List<Vehicle> getVehiclesByMaxRent(double maxRent);
//    List<Vehicle> getAvailableVehiclesByLocation(String city)

	// ================= ADMIN =================
	Vehicle addVehicleForAdmin(Long adminId, Vehicle vehicle);

	List<Vehicle> getVehiclesByAdmin(Long adminId);

	void deleteVehicle(Long vehicleId);

	// ================= PUBLIC / USER =================
	List<VehicleResponse> getAllVehicles();

	VehicleResponse getVehicleById(Long id);

	List<VehicleResponse> getVehiclesByAvailability(boolean status);

	List<VehicleResponse> getVehiclesByMaxRent(double maxRent);

	List<VehicleResponse> getAvailableVehiclesByLocation(String city);
}
