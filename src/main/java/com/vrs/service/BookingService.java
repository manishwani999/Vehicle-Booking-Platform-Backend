package com.vrs.service;

import java.io.File;
import java.util.List;

import com.vrs.model.Booking;

public interface BookingService {

    Booking createBooking(Long userId, Long vehicleId, Booking booking);

    List<Booking> getAllBookings();

    Booking getBookingById(Long bookingId);

    Booking cancelBooking(Long bookingId);

    // 🔥 Generate PDF and return File
    File generateReceiptFile(Long bookingId);
}
