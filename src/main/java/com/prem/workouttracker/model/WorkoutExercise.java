package com.prem.workouttracker.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Links a workout to an exercise with sets, reps, and weight.
 * One row = one exercise in a workout with same sets/reps/weight (simple design).
 */
@Entity
@Table(name = "workout_exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private Integer sets;
    private Integer reps;
    private Double weight; // in kg
}
