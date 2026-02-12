package com.vrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vrs.enums.BookingStatus;
import com.vrs.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b) FROM Booking b")
    long countTotalBookings();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'CONFIRMED'")
    long countActiveBookings();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'COMPLETED'")
    long countCompletedBookings();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'CANCELLED'")
    long countCancelledBookings();

    @Query("SELECT COALESCE(SUM(b.totalCost), 0) FROM Booking b WHERE b.status = 'COMPLETED'")
    Double getTotalRevenue();

    @Query("""
        SELECT FUNCTION('DATE_FORMAT', b.bookingDate, '%Y-%m'), SUM(b.totalCost)
        FROM Booking b
        WHERE b.status = 'COMPLETED'
        GROUP BY FUNCTION('DATE_FORMAT', b.bookingDate, '%Y-%m')
    """)
    List<Object[]> getRevenueByMonth();

    @Query("""
        SELECT CONCAT(v.brand, ' ', v.model), COUNT(b)
        FROM Booking b JOIN b.vehicle v
        GROUP BY v.id
        ORDER BY COUNT(b) DESC
    """)
    List<Object[]> getTopVehicles();
}

