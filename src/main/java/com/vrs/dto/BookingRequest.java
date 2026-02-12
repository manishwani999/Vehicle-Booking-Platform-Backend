package com.vrs.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingRequest {
    private Long customerId;
    private Long vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
