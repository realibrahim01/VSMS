package com.example.vsms.dto;

import java.time.LocalDate;

public record VehicleResponse(
        Long id,
        Long customerId,
        String customerName,
        String brand,
        String model,
        String registrationNumber,
        LocalDate predictedNextServiceDate
) {
}
