package com.vrs.controller;

import java.util.List;

import java.io.File;
import java.nio.file.Files;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vrs.model.Booking;
import com.vrs.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // ---------------- CREATE BOOKING ----------------

    @PostMapping("/user/{userId}/vehicle/{vehicleId}")
    public ResponseEntity<Booking> createBooking(
            @PathVariable Long userId,
            @PathVariable Long vehicleId,
            @RequestBody Booking booking) {

        return ResponseEntity.ok(
                bookingService.createBooking(userId, vehicleId, booking)
        );
    }

    // ---------------- FETCH BOOKINGS ----------------

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // ---------------- CANCEL BOOKING ----------------

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    // ---------------- RECEIPT ----------------

    @GetMapping("/receipt/{bookingId}")
    public ResponseEntity<Resource> downloadReceipt(@PathVariable Long bookingId) throws Exception {

        File file = bookingService.generateReceiptFile(bookingId);

        ByteArrayResource resource =
                new ByteArrayResource(Files.readAllBytes(file.toPath()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }
}
