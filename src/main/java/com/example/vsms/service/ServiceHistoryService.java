package com.example.vsms.service;

import com.example.vsms.dto.HistoryRequest;
import com.example.vsms.dto.HistoryResponse;
import com.example.vsms.entity.ServiceHistory;
import com.example.vsms.entity.Vehicle;
import com.example.vsms.exception.ResourceNotFoundException;
import com.example.vsms.repository.ServiceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceHistoryService {

    private final ServiceHistoryRepository historyRepository;
    private final VehicleService vehicleService;
    private final PredictionService predictionService;

    @Transactional(readOnly = true)
    public List<HistoryResponse> findAll(Long vehicleId) {
        List<ServiceHistory> histories = vehicleId == null
                ? historyRepository.findAll()
                : historyRepository.findByVehicleIdOrderByServiceDateDesc(vehicleId);
        return histories.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ServiceHistory findEntity(Long id) {
        return historyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service history not found with id " + id));
    }

    @Transactional(readOnly = true)
    public HistoryResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public HistoryResponse create(HistoryRequest request) {
        Vehicle vehicle = vehicleService.findEntity(request.vehicleId());
        ServiceHistory history = ServiceHistory.builder()
                .vehicle(vehicle)
                .description(request.description())
                .serviceDate(request.serviceDate())
                .cost(request.cost())
                .build();
        return toResponse(historyRepository.save(history));
    }

    public HistoryResponse update(Long id, HistoryRequest request) {
        ServiceHistory history = findEntity(id);
        Vehicle vehicle = vehicleService.findEntity(request.vehicleId());
        history.setVehicle(vehicle);
        history.setDescription(request.description());
        history.setServiceDate(request.serviceDate());
        history.setCost(request.cost());
        return toResponse(history);
    }

    public void delete(Long id) {
        historyRepository.delete(findEntity(id));
    }

    HistoryResponse toResponse(ServiceHistory history) {
        return new HistoryResponse(
                history.getId(),
                history.getVehicle().getId(),
                history.getVehicle().getRegistrationNumber(),
                history.getVehicle().getCustomer().getName(),
                history.getDescription(),
                history.getServiceDate(),
                history.getCost(),
                predictionService.predictNextServiceDate(history.getVehicle().getId())
        );
    }
}
