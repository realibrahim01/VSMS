package com.example.vsms.dto;

import java.math.BigDecimal;

public record DashboardResponse(
        long totalCustomers,
        long totalVehicles,
        long totalBookings,
        long pendingBookings,
        long completedBookings,
        BigDecimal totalRevenue
) {
}
