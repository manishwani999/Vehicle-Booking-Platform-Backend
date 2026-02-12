package com.vrs.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrs.dto.VehicleResponse;
import com.vrs.enums.Role;
import com.vrs.model.Location;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.repository.LocationRepository;
import com.vrs.repository.UserRepository;
import com.vrs.repository.VehicleRepository;
import com.vrs.service.VehicleService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    // ================= ADMIN =================

    @Override
    public Vehicle addVehicleForAdmin(Long adminId, Vehicle vehicle) {

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("User is not an ADMIN");
        }

        if (vehicle.getLocation() == null || vehicle.getLocation().getId() == null) {
            throw new RuntimeException("Location is required");
        }

        Location location = locationRepository.findById(
                vehicle.getLocation().getId()
        ).orElseThrow(() -> new RuntimeException("Location not found"));

        vehicle.setAdmin(admin);
        vehicle.setLocation(location);
        vehicle.setStatus(true);

        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getVehiclesByAdmin(Long adminId) {
        return vehicleRepository.findByAdminId(adminId);
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    // ================= PUBLIC / USER =================

    @Override
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        return mapToDto(vehicle);
    }

    @Override
    public List<VehicleResponse> getVehiclesByAvailability(boolean status) {
        return vehicleRepository.findByStatus(status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponse> getVehiclesByMaxRent(double maxRent) {
        return vehicleRepository.findByRentPerDayLessThanEqual(maxRent)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponse> getAvailableVehiclesByLocation(String city) {
        return vehicleRepository.findAvailableByCity(city)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ================= DTO MAPPER =================

    private VehicleResponse mapToDto(Vehicle vehicle) {
        VehicleResponse dto = new VehicleResponse();

        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setRentPerDay(vehicle.getRentPerDay());
        dto.setStatus(vehicle.isStatus());

        if (vehicle.getLocation() != null) {
            dto.setLocationName(vehicle.getLocation().getCity());
        } else {
            dto.setLocationName("N/A");
        }

        return dto;
    }
}
