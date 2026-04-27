package com.example.vsms.service;

import com.example.vsms.dto.DashboardResponse;
import com.example.vsms.entity.BookingStatus;
import com.example.vsms.repository.CustomerRepository;
import com.example.vsms.repository.ServiceBookingRepository;
import com.example.vsms.repository.ServiceHistoryRepository;
import com.example.vsms.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceBookingRepository bookingRepository;
    private final ServiceHistoryRepository historyRepository;

    public DashboardResponse getDashboard() {
        BigDecimal totalRevenue = historyRepository.sumTotalRevenue();
        return new DashboardResponse(
                customerRepository.count(),
                vehicleRepository.count(),
                bookingRepository.count(),
                bookingRepository.countByStatus(BookingStatus.PENDING),
                bookingRepository.countByStatus(BookingStatus.COMPLETED),
                totalRevenue == null ? BigDecimal.ZERO : totalRevenue
        );
    }
}
