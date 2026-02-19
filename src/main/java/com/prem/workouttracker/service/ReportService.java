package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.ReportResponse;
import com.prem.workouttracker.dto.WorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final WorkoutService workoutService;

    /**
     * Returns total workouts, count for current week (Mon–Sun), and recent workout history.
     */
    @Transactional(readOnly = true)
    public ReportResponse getReport() {
        List<WorkoutResponse> all = workoutService.findAllByUser();
        long total = all.size();
        LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);
        int weeklyCount = (int) all.stream()
                .filter(w -> w.getScheduledDate() != null
                        && !w.getScheduledDate().isBefore(weekStart)
                        && !w.getScheduledDate().isAfter(weekEnd))
                .count();
        return ReportResponse.builder()
                .totalWorkouts(total)
                .weeklyCount(weeklyCount)
                .workoutHistory(all)
                .build();
    }
}
