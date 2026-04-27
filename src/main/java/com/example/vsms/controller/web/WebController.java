package com.example.vsms.controller.web;

import com.example.vsms.dto.BookingRequest;
import com.example.vsms.dto.CompleteBookingRequest;
import com.example.vsms.dto.CustomerRequest;
import com.example.vsms.dto.HistoryRequest;
import com.example.vsms.dto.VehicleRequest;
import com.example.vsms.entity.BookingStatus;
import com.example.vsms.entity.ServiceType;
import com.example.vsms.service.CustomerService;
import com.example.vsms.service.DashboardService;
import com.example.vsms.service.ReminderService;
import com.example.vsms.service.ServiceBookingService;
import com.example.vsms.service.ServiceHistoryService;
import com.example.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final DashboardService dashboardService;
    private final ReminderService reminderService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final ServiceBookingService bookingService;
    private final ServiceHistoryService historyService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("dashboard", dashboardService.getDashboard());
        model.addAttribute("reminders", reminderService.getDueSoon(30));
        return "dashboard";
    }

    @GetMapping("/customers")
    public String customers(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("customers", customerService.findAll(search));
        model.addAttribute("search", search);
        return "customers";
    }

    @PostMapping("/customers")
    public String createCustomer(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address,
            RedirectAttributes redirectAttributes
    ) {
        customerService.create(new CustomerRequest(name, phone, email, address));
        redirectAttributes.addFlashAttribute("success", "Customer saved successfully");
        return "redirect:/customers";
    }

    @PostMapping("/customers/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Customer deleted successfully");
        return "redirect:/customers";
    }

    @GetMapping("/vehicles")
    public String vehicles(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("vehicles", vehicleService.findAll(search, null));
        model.addAttribute("customers", customerService.findAll(null));
        model.addAttribute("search", search);
        return "vehicles";
    }

    @PostMapping("/vehicles")
    public String createVehicle(
            @RequestParam Long customerId,
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String registrationNumber,
            RedirectAttributes redirectAttributes
    ) {
        vehicleService.create(new VehicleRequest(customerId, brand, model, registrationNumber));
        redirectAttributes.addFlashAttribute("success", "Vehicle saved successfully");
        return "redirect:/vehicles";
    }

    @PostMapping("/vehicles/{id}/delete")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        vehicleService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Vehicle deleted successfully");
        return "redirect:/vehicles";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll(null));
        model.addAttribute("vehicles", vehicleService.findAll(null, null));
        model.addAttribute("serviceTypes", ServiceType.values());
        model.addAttribute("statuses", BookingStatus.values());
        return "bookings";
    }

    @PostMapping("/bookings")
    public String createBooking(
            @RequestParam Long vehicleId,
            @RequestParam LocalDate serviceDate,
            @RequestParam ServiceType serviceType,
            RedirectAttributes redirectAttributes
    ) {
        bookingService.create(new BookingRequest(vehicleId, serviceDate, serviceType, BookingStatus.PENDING));
        redirectAttributes.addFlashAttribute("success", "Service booking created successfully");
        return "redirect:/bookings";
    }

    @PostMapping("/bookings/{id}/status")
    public String updateBookingStatus(
            @PathVariable Long id,
            @RequestParam BookingStatus status,
            RedirectAttributes redirectAttributes
    ) {
        bookingService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Booking status updated");
        return "redirect:/bookings";
    }

    @PostMapping("/bookings/{id}/complete")
    public String completeBooking(
            @PathVariable Long id,
            @RequestParam String description,
            @RequestParam BigDecimal cost,
            RedirectAttributes redirectAttributes
    ) {
        bookingService.completeBooking(id, new CompleteBookingRequest(description, cost));
        redirectAttributes.addFlashAttribute("success", "Booking completed and history recorded");
        return "redirect:/history";
    }

    @PostMapping("/bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookingService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Booking deleted successfully");
        return "redirect:/bookings";
    }

    @GetMapping("/history")
    public String history(@RequestParam(required = false) Long vehicleId, Model model) {
        model.addAttribute("histories", historyService.findAll(vehicleId));
        model.addAttribute("vehicles", vehicleService.findAll(null, null));
        model.addAttribute("selectedVehicleId", vehicleId);
        return "history";
    }

    @PostMapping("/history")
    public String createHistory(
            @RequestParam Long vehicleId,
            @RequestParam String description,
            @RequestParam LocalDate serviceDate,
            @RequestParam BigDecimal cost,
            RedirectAttributes redirectAttributes
    ) {
        historyService.create(new HistoryRequest(vehicleId, description, serviceDate, cost));
        redirectAttributes.addFlashAttribute("success", "Service history added successfully");
        return "redirect:/history";
    }

    @PostMapping("/history/{id}/delete")
    public String deleteHistory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        historyService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Service history deleted successfully");
        return "redirect:/history";
    }
}
