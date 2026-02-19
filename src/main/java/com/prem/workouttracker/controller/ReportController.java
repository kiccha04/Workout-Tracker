package com.prem.workouttracker.controller;

import com.prem.workouttracker.dto.ReportResponse;
import com.prem.workouttracker.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Workout history and stats")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "Get report: total workouts, weekly count, history")
    public ReportResponse getReport() {
        return reportService.getReport();
    }
}
