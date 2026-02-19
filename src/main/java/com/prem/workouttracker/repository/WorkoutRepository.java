package com.prem.workouttracker.repository;

import com.prem.workouttracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUserIdOrderByScheduledDateDescScheduledTimeDesc(Long userId);

    List<Workout> findByUserIdAndScheduledDateBetweenOrderByScheduledDateAscScheduledTimeAsc(
            Long userId, LocalDate start, LocalDate end);
}
