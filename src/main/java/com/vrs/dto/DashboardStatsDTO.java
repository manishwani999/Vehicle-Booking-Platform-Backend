package com.vrs.dto;

import java.util.Map;


public class DashboardStatsDTO {

    private long totalUsers;
    private long totalVehicles;
    private long availableVehicles;
    private long totalBookings;
    private long activeBookings;
    private long completedBookings;
    private long cancelledBookings;
    private double totalRevenue;

    public DashboardStatsDTO(
            long totalUsers,
            long totalVehicles,
            long availableVehicles,
            long totalBookings,
            long activeBookings,
            long completedBookings,
            long cancelledBookings,
            double totalRevenue) {

        this.totalUsers = totalUsers;
        this.totalVehicles = totalVehicles;
        this.availableVehicles = availableVehicles;
        this.totalBookings = totalBookings;
        this.activeBookings = activeBookings;
        this.completedBookings = completedBookings;
        this.cancelledBookings = cancelledBookings;
        this.totalRevenue = totalRevenue;
    }

	public long getTotalUsers() {
		return totalUsers;
	}

	public long getTotalVehicles() {
		return totalVehicles;
	}

	public long getAvailableVehicles() {
		return availableVehicles;
	}

	public long getTotalBookings() {
		return totalBookings;
	}

	public long getActiveBookings() {
		return activeBookings;
	}

	public long getCompletedBookings() {
		return completedBookings;
	}

	public long getCancelledBookings() {
		return cancelledBookings;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}
    
}

