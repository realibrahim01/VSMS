package com.example.vsms.dto;

import com.example.vsms.entity.BookingStatus;
import com.example.vsms.entity.ServiceType;

import java.time.LocalDate;

public record BookingResponse(
        Long id,
        Long vehicleId,
        String registrationNumber,
        String customerName,
        LocalDate serviceDate,
        ServiceType serviceType,
        BookingStatus status
) {
}
