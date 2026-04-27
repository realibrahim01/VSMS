package com.example.vsms.dto;

import java.time.LocalDate;

public record ReminderResponse(
        Long vehicleId,
        String registrationNumber,
        String customerName,
        LocalDate lastServiceDate,
        LocalDate predictedNextServiceDate,
        boolean dueSoon
) {
}
