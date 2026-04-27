package com.example.vsms.repository;

import com.example.vsms.entity.BookingStatus;
import com.example.vsms.entity.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {

    long countByStatus(BookingStatus status);

    List<ServiceBooking> findByVehicleIdOrderByServiceDateDesc(Long vehicleId);

    List<ServiceBooking> findByStatusAndServiceDateBetweenOrderByServiceDateAsc(
            BookingStatus status,
            LocalDate startDate,
            LocalDate endDate
    );
}
