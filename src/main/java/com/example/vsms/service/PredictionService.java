package com.example.vsms.service;

import com.example.vsms.entity.ServiceHistory;
import com.example.vsms.repository.ServiceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private static final long DEFAULT_SERVICE_INTERVAL_DAYS = 180;

    private final ServiceHistoryRepository historyRepository;

    public LocalDate predictNextServiceDate(Long vehicleId) {
        List<ServiceHistory> histories = historyRepository.findByVehicleIdOrderByServiceDateDesc(vehicleId);
        if (histories.isEmpty()) {
            return null;
        }

        LocalDate latest = histories.get(0).getServiceDate();
        if (histories.size() == 1) {
            return latest.plusDays(DEFAULT_SERVICE_INTERVAL_DAYS);
        }

        long totalGap = 0;
        int gapCount = 0;
        for (int i = 0; i < histories.size() - 1; i++) {
            LocalDate newer = histories.get(i).getServiceDate();
            LocalDate older = histories.get(i + 1).getServiceDate();
            long gap = ChronoUnit.DAYS.between(older, newer);
            if (gap > 0) {
                totalGap += gap;
                gapCount++;
            }
        }

        long averageGap = gapCount == 0 ? DEFAULT_SERVICE_INTERVAL_DAYS : Math.max(30, totalGap / gapCount);
        return latest.plusDays(averageGap);
    }
}
