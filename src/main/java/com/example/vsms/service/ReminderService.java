package com.example.vsms.service;

import com.example.vsms.dto.ReminderResponse;
import com.example.vsms.entity.ServiceHistory;
import com.example.vsms.entity.Vehicle;
import com.example.vsms.repository.ServiceHistoryRepository;
import com.example.vsms.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReminderService {

    private final VehicleRepository vehicleRepository;
    private final ServiceHistoryRepository historyRepository;
    private final PredictionService predictionService;

    public List<ReminderResponse> getDueSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(days);
        return vehicleRepository.findAll().stream()
                .map(vehicle -> toReminder(vehicle, today, limit))
                .filter(ReminderResponse::dueSoon)
                .toList();
    }

    private ReminderResponse toReminder(Vehicle vehicle, LocalDate today, LocalDate limit) {
        LocalDate nextServiceDate = predictionService.predictNextServiceDate(vehicle.getId());
        LocalDate lastServiceDate = historyRepository.findFirstByVehicleIdOrderByServiceDateDesc(vehicle.getId())
                .map(ServiceHistory::getServiceDate)
                .orElse(null);
        boolean dueSoon = nextServiceDate != null
                && (!nextServiceDate.isBefore(today))
                && (!nextServiceDate.isAfter(limit));
        return new ReminderResponse(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getCustomer().getName(),
                lastServiceDate,
                nextServiceDate,
                dueSoon
        );
    }
}
