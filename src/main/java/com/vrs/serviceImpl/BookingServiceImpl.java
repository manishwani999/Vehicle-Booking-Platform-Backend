package com.vrs.serviceImpl;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vrs.enums.BookingStatus;
import com.vrs.model.Booking;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.repository.BookingRepository;
import com.vrs.repository.UserRepository;
import com.vrs.repository.VehicleRepository;
import com.vrs.service.BookingService;
import com.vrs.service.EmailService;
import com.vrs.util.HtmlTemplateProcessor;
import com.vrs.util.PdfGenerator;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final ZoneId INDIA_ZONE = ZoneId.of("Asia/Kolkata");

    // ================= CREATE BOOKING =================

    @Override
    public Booking createBooking(Long userId, Long vehicleId, Booking booking) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));

        if (!vehicle.isStatus()) {
            throw new RuntimeException("Vehicle is not available");
        }

        long days = ChronoUnit.DAYS.between(
                booking.getStartDate(),
                booking.getEndDate()
        );

        if (days <= 0) {
            throw new RuntimeException("End date must be after start date");
        }

        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setBookingDate(LocalDate.now(INDIA_ZONE));
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalCost(days * vehicle.getRentPerDay());

        vehicle.setStatus(false);

        vehicleRepository.save(vehicle);
        return bookingRepository.save(booking);
    }

    // ================= FETCH BOOKINGS =================

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(); // ✔ dashboard safe
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with id: " + bookingId));
    }

    // ================= CANCEL BOOKING =================

    @Override
    public Booking cancelBooking(Long bookingId) {

        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);

        Vehicle vehicle = booking.getVehicle();
        if (vehicle != null) {
            vehicle.setStatus(true);
            vehicleRepository.save(vehicle);
        }

        return bookingRepository.save(booking);
    }

    // ================= AUTO COMPLETE BOOKINGS =================

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    public void updateCompletedBookings() {

        LocalDate today = LocalDate.now(INDIA_ZONE);

        bookingRepository.findAll().forEach(b -> {
            if (b.getStatus() == BookingStatus.CONFIRMED
                    && b.getEndDate().isBefore(today)) {

                b.setStatus(BookingStatus.COMPLETED);
                bookingRepository.save(b);

                Vehicle v = b.getVehicle();
                if (v != null) {
                    v.setStatus(true);
                    vehicleRepository.save(v);
                }
            }
        });
    }

    // ================= RECEIPT =================

    @Override
    public File generateReceiptFile(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        User user = booking.getUser();
        Vehicle vehicle = booking.getVehicle();
        User admin = vehicle != null ? vehicle.getAdmin() : null;

        long totalDays = Math.max(
                ChronoUnit.DAYS.between(
                        booking.getStartDate(),
                        booking.getEndDate()
                ), 1);

        Map<String, String> values = new HashMap<>();
        values.put("BOOKING_ID", booking.getId().toString());
        values.put("BOOKING_DATE", booking.getBookingDate().toString());
        values.put("USER_NAME", user.getName());
        values.put("USER_EMAIL", user.getEmail());
        values.put("VEHICLE_BRAND", vehicle.getBrand());
        values.put("VEHICLE_MODEL", vehicle.getModel());
        values.put("ADMIN_NAME",
                admin != null ? admin.getName() : "Vehicle Rental System");
        values.put("TOTAL_DAYS", String.valueOf(totalDays));
        values.put("RENT_PER_DAY", String.valueOf(vehicle.getRentPerDay()));
        values.put("TOTAL_AMOUNT", String.valueOf(booking.getTotalCost()));

        String html = HtmlTemplateProcessor.fillTemplateFromClasspath(
                "templates/booking-receipt.html", values);

        String outputPath = "receipts/booking_" + booking.getId() + ".pdf";
        PdfGenerator.generatePdfFromHtml(html, outputPath);

        emailService.sendEmailWithAttachment(
                user.getEmail(),
                "Booking Receipt - #" + booking.getId(),
                "Please find your booking receipt attached.",
                outputPath
        );

        return new File(outputPath);
    }
}
