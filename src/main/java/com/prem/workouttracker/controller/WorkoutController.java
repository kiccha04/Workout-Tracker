package com.prem.workouttracker.controller;

import com.prem.workouttracker.dto.WorkoutRequest;
import com.prem.workouttracker.dto.WorkoutResponse;
import com.prem.workouttracker.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
@Tag(name = "Workouts", description = "CRUD and schedule workouts (user's own data only)")
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    @Operation(summary = "List all workouts for current user")
    public List<WorkoutResponse> list() {
        return workoutService.findAllByUser();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get workout by ID")
    public WorkoutResponse getById(@PathVariable Long id) {
        return workoutService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a workout")
    public ResponseEntity<WorkoutResponse> create(@Valid @RequestBody WorkoutRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a workout")
    public WorkoutResponse update(@PathVariable Long id, @Valid @RequestBody WorkoutRequest request) {
        return workoutService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a workout")
    public void delete(@PathVariable Long id) {
        workoutService.delete(id);
    }
}
