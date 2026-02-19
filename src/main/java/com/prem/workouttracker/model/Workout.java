package com.prem.workouttracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "scheduled_time")
    private LocalTime scheduledTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    @Builder.Default
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
