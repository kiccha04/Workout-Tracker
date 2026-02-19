package com.prem.workouttracker.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Reference data for exercises (seeded). Not user-specific.
 */
@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String category;   // e.g. Strength, Cardio, Flexibility
    private String muscleGroup; // e.g. Chest, Back, Legs
}
