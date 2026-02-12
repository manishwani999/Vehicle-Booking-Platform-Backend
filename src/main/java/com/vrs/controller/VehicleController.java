package com.vrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vrs.dto.VehicleResponse;
import com.vrs.model.Vehicle;
import com.vrs.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // ================= PUBLIC =================

    @GetMapping
    public List<VehicleResponse> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/id/{id}")
    public VehicleResponse getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    @GetMapping("/available/{status}")
    public List<VehicleResponse> getByAvailability(@PathVariable boolean status) {
        return vehicleService.getVehiclesByAvailability(status);
    }

    @GetMapping("/rent/{maxRent}")
    public List<VehicleResponse> getByMaxRent(@PathVariable double maxRent) {
        return vehicleService.getVehiclesByMaxRent(maxRent);
    }

    @GetMapping("/location/{city}")
    public List<VehicleResponse> getByLocation(@PathVariable String city) {
        return vehicleService.getAvailableVehiclesByLocation(city);
    }

    // ================= ADMIN =================

    @PostMapping("/admin/{adminId}")
    public Vehicle addVehicle(
            @PathVariable Long adminId,
            @RequestBody Vehicle vehicle) {

        return vehicleService.addVehicleForAdmin(adminId, vehicle);
    }

    @GetMapping("/admin/{adminId}")
    public List<Vehicle> getAdminVehicles(@PathVariable Long adminId) {
        return vehicleService.getVehiclesByAdmin(adminId);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
