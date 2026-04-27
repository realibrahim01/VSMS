package com.example.vsms.controller.api;

import com.example.vsms.dto.HistoryRequest;
import com.example.vsms.dto.HistoryResponse;
import com.example.vsms.service.ServiceHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryApiController {

    private final ServiceHistoryService historyService;

    @GetMapping
    public List<HistoryResponse> findAll(@RequestParam(required = false) Long vehicleId) {
        return historyService.findAll(vehicleId);
    }

    @GetMapping("/{id}")
    public HistoryResponse findById(@PathVariable Long id) {
        return historyService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HistoryResponse create(@Valid @RequestBody HistoryRequest request) {
        return historyService.create(request);
    }

    @PutMapping("/{id}")
    public HistoryResponse update(@PathVariable Long id, @Valid @RequestBody HistoryRequest request) {
        return historyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        historyService.delete(id);
    }
}
