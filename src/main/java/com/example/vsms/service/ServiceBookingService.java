package com.example.vsms.service;

import com.example.vsms.dto.BookingRequest;
import com.example.vsms.dto.BookingResponse;
import com.example.vsms.dto.CompleteBookingRequest;
import com.example.vsms.dto.HistoryResponse;
import com.example.vsms.entity.BookingStatus;
import com.example.vsms.entity.ServiceBooking;
import com.example.vsms.entity.ServiceHistory;
import com.example.vsms.entity.Vehicle;
import com.example.vsms.exception.ResourceNotFoundException;
import com.example.vsms.repository.ServiceBookingRepository;
import com.example.vsms.repository.ServiceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceBookingService {

    private final ServiceBookingRepository bookingRepository;
    private final ServiceHistoryRepository historyRepository;
    private final VehicleService vehicleService;
    private final ServiceHistoryService historyService;

    @Transactional(readOnly = true)
    public List<BookingResponse> findAll(Long vehicleId) {
        List<ServiceBooking> bookings = vehicleId == null
                ? bookingRepository.findAll()
                : bookingRepository.findByVehicleIdOrderByServiceDateDesc(vehicleId);
        return bookings.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ServiceBooking findEntity(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service booking not found with id " + id));
    }

    @Transactional(readOnly = true)
    public BookingResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public BookingResponse create(BookingRequest request) {
        Vehicle vehicle = vehicleService.findEntity(request.vehicleId());
        ServiceBooking booking = ServiceBooking.builder()
                .vehicle(vehicle)
                .serviceDate(request.serviceDate())
                .serviceType(request.serviceType())
                .status(request.status() == null ? BookingStatus.PENDING : request.status())
                .build();
        return toResponse(bookingRepository.save(booking));
    }

    public BookingResponse update(Long id, BookingRequest request) {
        ServiceBooking booking = findEntity(id);
        Vehicle vehicle = vehicleService.findEntity(request.vehicleId());
        booking.setVehicle(vehicle);
        booking.setServiceDate(request.serviceDate());
        booking.setServiceType(request.serviceType());
        booking.setStatus(request.status() == null ? booking.getStatus() : request.status());
        return toResponse(booking);
    }

    public BookingResponse updateStatus(Long id, BookingStatus status) {
        ServiceBooking booking = findEntity(id);
        booking.setStatus(status);
        return toResponse(booking);
    }

    public HistoryResponse completeBooking(Long id, CompleteBookingRequest request) {
        ServiceBooking booking = findEntity(id);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled bookings cannot be completed");
        }
        booking.setStatus(BookingStatus.COMPLETED);

        ServiceHistory history = ServiceHistory.builder()
                .vehicle(booking.getVehicle())
                .description(request.description())
                .serviceDate(booking.getServiceDate())
                .cost(request.cost())
                .build();
        return historyService.toResponse(historyRepository.save(history));
    }

    public void delete(Long id) {
        bookingRepository.delete(findEntity(id));
    }

    private BookingResponse toResponse(ServiceBooking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getVehicle().getId(),
                booking.getVehicle().getRegistrationNumber(),
                booking.getVehicle().getCustomer().getName(),
                booking.getServiceDate(),
                booking.getServiceType(),
                booking.getStatus()
        );
    }
}
