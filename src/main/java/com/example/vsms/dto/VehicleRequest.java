package com.example.vsms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotBlank(message = "Brand is required")
        String brand,

        @NotBlank(message = "Model is required")
        String model,

        @NotBlank(message = "Registration number is required")
        String registrationNumber
) {
}
