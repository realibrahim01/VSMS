package com.example.vsms.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HistoryRequest(
        @NotNull(message = "Vehicle id is required")
        Long vehicleId,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Service date is required")
        LocalDate serviceDate,

        @NotNull(message = "Cost is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be zero or greater")
        BigDecimal cost
) {
}
