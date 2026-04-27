package com.example.vsms.controller.api;

import com.example.vsms.dto.DashboardResponse;
import com.example.vsms.dto.ReminderResponse;
import com.example.vsms.service.DashboardService;
import com.example.vsms.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardApiController {

    private final DashboardService dashboardService;
    private final ReminderService reminderService;

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {
        return dashboardService.getDashboard();
    }

    @GetMapping("/reminders")
    public List<ReminderResponse> reminders(@RequestParam(defaultValue = "7") int days) {
        return reminderService.getDueSoon(days);
    }
}
