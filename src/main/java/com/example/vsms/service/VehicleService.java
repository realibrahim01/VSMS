package com.example.vsms.service;

import com.example.vsms.dto.VehicleRequest;
import com.example.vsms.dto.VehicleResponse;
import com.example.vsms.entity.Customer;
import com.example.vsms.entity.Vehicle;
import com.example.vsms.exception.DuplicateResourceException;
import com.example.vsms.exception.ResourceNotFoundException;
import com.example.vsms.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;
    private final PredictionService predictionService;

    @Transactional(readOnly = true)
    public List<VehicleResponse> findAll(String search, Long customerId) {
        List<Vehicle> vehicles;
        if (customerId != null) {
            vehicles = vehicleRepository.findByCustomerId(customerId);
        } else if (search != null && !search.isBlank()) {
            vehicles = vehicleRepository
                    .findByRegistrationNumberContainingIgnoreCaseOrBrandContainingIgnoreCaseOrModelContainingIgnoreCase(
                            search, search, search
                    );
        } else {
            vehicles = vehicleRepository.findAll();
        }
        return vehicles.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Vehicle findEntity(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id " + id));
    }

    @Transactional(readOnly = true)
    public VehicleResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public VehicleResponse create(VehicleRequest request) {
        ensureRegistrationAvailable(request.registrationNumber(), null);
        Customer customer = customerService.findEntity(request.customerId());
        Vehicle vehicle = Vehicle.builder()
                .customer(customer)
                .brand(request.brand())
                .model(request.model())
                .registrationNumber(normalizeRegistration(request.registrationNumber()))
                .build();
        return toResponse(vehicleRepository.save(vehicle));
    }

    public VehicleResponse update(Long id, VehicleRequest request) {
        Vehicle vehicle = findEntity(id);
        ensureRegistrationAvailable(request.registrationNumber(), id);
        Customer customer = customerService.findEntity(request.customerId());
        vehicle.setCustomer(customer);
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setRegistrationNumber(normalizeRegistration(request.registrationNumber()));
        return toResponse(vehicle);
    }

    public void delete(Long id) {
        Vehicle vehicle = findEntity(id);
        vehicleRepository.delete(vehicle);
    }

    private void ensureRegistrationAvailable(String registrationNumber, Long currentVehicleId) {
        vehicleRepository.findByRegistrationNumberIgnoreCase(normalizeRegistration(registrationNumber))
                .filter(existing -> !existing.getId().equals(currentVehicleId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Vehicle registration already exists: " + registrationNumber);
                });
    }

    private String normalizeRegistration(String registrationNumber) {
        return registrationNumber == null ? null : registrationNumber.trim().toUpperCase();
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getCustomer().getId(),
                vehicle.getCustomer().getName(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getRegistrationNumber(),
                predictionService.predictNextServiceDate(vehicle.getId())
        );
    }
}
