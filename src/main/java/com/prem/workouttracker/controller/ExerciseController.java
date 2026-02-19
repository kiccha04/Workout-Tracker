package com.prem.workouttracker.controller;

import com.prem.workouttracker.dto.ExerciseResponse;
import com.prem.workouttracker.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
@Tag(name = "Exercises", description = "Reference list of exercises (seeded)")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    @Operation(summary = "List all exercises")
    public List<ExerciseResponse> list() {
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exercise by ID")
    public ExerciseResponse getById(@PathVariable Long id) {
        return exerciseService.findById(id);
    }
}
