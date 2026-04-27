package com.example.vsms.dto;

public record CustomerResponse(
        Long id,
        String name,
        String phone,
        String email,
        String address,
        long vehicleCount
) {
}
