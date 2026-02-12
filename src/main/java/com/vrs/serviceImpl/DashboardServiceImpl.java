package com.vrs.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrs.dto.DashboardStatsDTO;
import com.vrs.repository.BookingRepository;
import com.vrs.repository.UserRepository;
import com.vrs.repository.VehicleRepository;
import com.vrs.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public DashboardStatsDTO getDashboardStats() {

        long totalUsers = userRepository.count();
        long totalVehicles = vehicleRepository.countTotalVehicles();
        long availableVehicles = vehicleRepository.countAvailableVehicles();

        long totalBookings = bookingRepository.countTotalBookings();
        long activeBookings = bookingRepository.countActiveBookings();
        long completedBookings = bookingRepository.countCompletedBookings();
        long cancelledBookings = bookingRepository.countCancelledBookings();

        double totalRevenue =
                bookingRepository.getTotalRevenue() != null
                        ? bookingRepository.getTotalRevenue()
                        : 0.0;

        return new DashboardStatsDTO(
                totalUsers,
                totalVehicles,
                availableVehicles,
                totalBookings,
                activeBookings,
                completedBookings,
                cancelledBookings,
                totalRevenue
        );
    }
}

