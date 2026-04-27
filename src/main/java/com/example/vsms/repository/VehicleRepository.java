package com.example.vsms.repository;

import com.example.vsms.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByRegistrationNumberIgnoreCase(String registrationNumber);

    boolean existsByRegistrationNumberIgnoreCase(String registrationNumber);

    List<Vehicle> findByRegistrationNumberContainingIgnoreCaseOrBrandContainingIgnoreCaseOrModelContainingIgnoreCase(
            String registrationNumber,
            String brand,
            String model
    );

    List<Vehicle> findByCustomerId(Long customerId);
}
