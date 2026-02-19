package com.prem.workouttracker.config;

import com.prem.workouttracker.model.Exercise;
import com.prem.workouttracker.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the database with exercise reference data on startup (when not in test profile).
 */
@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) {
        if (exerciseRepository.count() > 0) {
            return; // already seeded
        }
        List<Exercise> exercises = List.of(
                Exercise.builder().name("Bench Press").description("Barbell chest press on flat bench")
                        .category("Strength").muscleGroup("Chest").build(),
                Exercise.builder().name("Squat").description("Barbell back squat")
                        .category("Strength").muscleGroup("Legs").build(),
                Exercise.builder().name("Deadlift").description("Conventional barbell deadlift")
                        .category("Strength").muscleGroup("Back").build(),
                Exercise.builder().name("Overhead Press").description("Standing barbell shoulder press")
                        .category("Strength").muscleGroup("Shoulders").build(),
                Exercise.builder().name("Barbell Row").description("Bent-over barbell row")
                        .category("Strength").muscleGroup("Back").build(),
                Exercise.builder().name("Pull-up").description("Bodyweight pull-up")
                        .category("Strength").muscleGroup("Back").build(),
                Exercise.builder().name("Push-up").description("Standard push-up")
                        .category("Strength").muscleGroup("Chest").build(),
                Exercise.builder().name("Lunges").description("Walking or standing lunges")
                        .category("Strength").muscleGroup("Legs").build(),
                Exercise.builder().name("Bicep Curl").description("Dumbbell or barbell bicep curl")
                        .category("Strength").muscleGroup("Biceps").build(),
                Exercise.builder().name("Tricep Dips").description("Bench or bar tricep dips")
                        .category("Strength").muscleGroup("Triceps").build(),
                Exercise.builder().name("Plank").description("Front plank hold")
                        .category("Strength").muscleGroup("Core").build(),
                Exercise.builder().name("Running").description("Treadmill or outdoor run")
                        .category("Cardio").muscleGroup("Full Body").build(),
                Exercise.builder().name("Cycling").description("Stationary or outdoor cycling")
                        .category("Cardio").muscleGroup("Legs").build(),
                Exercise.builder().name("Stretching").description("General flexibility stretching")
                        .category("Flexibility").muscleGroup("Full Body").build()
        );
        exerciseRepository.saveAll(exercises);
    }
}
