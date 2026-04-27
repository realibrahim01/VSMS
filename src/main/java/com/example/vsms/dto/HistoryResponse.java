package com.example.vsms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HistoryResponse(
        Long id,
        Long vehicleId,
        String registrationNumber,
        String customerName,
        String description,
        LocalDate serviceDate,
        BigDecimal cost,
        LocalDate predictedNextServiceDate
) {
}
