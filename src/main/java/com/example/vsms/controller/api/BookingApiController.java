package com.example.vsms.controller.api;

import com.example.vsms.dto.BookingRequest;
import com.example.vsms.dto.BookingResponse;
import com.example.vsms.dto.CompleteBookingRequest;
import com.example.vsms.dto.HistoryResponse;
import com.example.vsms.entity.BookingStatus;
import com.example.vsms.service.ServiceBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingApiController {

    private final ServiceBookingService bookingService;

    @GetMapping
    public List<BookingResponse> findAll(@RequestParam(required = false) Long vehicleId) {
        return bookingService.findAll(vehicleId);
    }

    @GetMapping("/{id}")
    public BookingResponse findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        return bookingService.create(request);
    }

    @PutMapping("/{id}")
    public BookingResponse update(@PathVariable Long id, @Valid @RequestBody BookingRequest request) {
        return bookingService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public BookingResponse updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return bookingService.updateStatus(id, status);
    }

    @PostMapping("/{id}/complete")
    public HistoryResponse complete(@PathVariable Long id, @Valid @RequestBody CompleteBookingRequest request) {
        return bookingService.completeBooking(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }
}
