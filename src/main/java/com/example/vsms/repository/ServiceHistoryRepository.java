package com.example.vsms.repository;

import com.example.vsms.entity.ServiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {

    List<ServiceHistory> findByVehicleIdOrderByServiceDateDesc(Long vehicleId);

    Optional<ServiceHistory> findFirstByVehicleIdOrderByServiceDateDesc(Long vehicleId);

    @Query("select coalesce(sum(h.cost), 0.00) from ServiceHistory h")
    BigDecimal sumTotalRevenue();
}
