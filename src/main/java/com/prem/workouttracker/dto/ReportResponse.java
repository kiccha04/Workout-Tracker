package com.prem.workouttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private long totalWorkouts;
    private int weeklyCount;
    private List<WorkoutResponse> workoutHistory;
}
