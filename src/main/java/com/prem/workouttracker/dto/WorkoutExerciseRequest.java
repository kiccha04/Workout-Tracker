package com.prem.workouttracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseRequest {

    @NotNull(message = "Exercise ID is required")
    private Long exerciseId;

    private Integer sets;
    private Integer reps;
    private Double weight;
}
