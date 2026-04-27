package com.example.vsms.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CompleteBookingRequest(
        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Cost is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be zero or greater")
        BigDecimal cost
) {
}
