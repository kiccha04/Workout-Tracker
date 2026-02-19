package com.prem.workouttracker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequest {

    @NotBlank(message = "Workout name is required")
    private String name;

    private LocalDate scheduledDate;
    private LocalTime scheduledTime;

    @Valid
    @NotNull
    private List<WorkoutExerciseRequest> exercises = new ArrayList<>();
}
