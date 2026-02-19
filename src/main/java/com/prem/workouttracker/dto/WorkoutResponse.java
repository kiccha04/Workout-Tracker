package com.prem.workouttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResponse {

    private Long id;
    private String name;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private Instant createdAt;
    private List<WorkoutExerciseResponse> exercises = new ArrayList<>();
}
