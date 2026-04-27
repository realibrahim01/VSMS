package com.example.vsms.dto;

import com.example.vsms.entity.BookingStatus;
import com.example.vsms.entity.ServiceType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingRequest(
        @NotNull(message = "Vehicle id is required")
        Long vehicleId,

        @NotNull(message = "Service date is required")
        @FutureOrPresent(message = "Service date cannot be in the past")
        LocalDate serviceDate,

        @NotNull(message = "Service type is required")
        ServiceType serviceType,

        BookingStatus status
) {
}
