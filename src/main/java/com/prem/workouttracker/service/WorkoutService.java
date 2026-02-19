package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.WorkoutRequest;
import com.prem.workouttracker.dto.WorkoutResponse;
import com.prem.workouttracker.dto.WorkoutExerciseResponse;
import com.prem.workouttracker.exception.ResourceNotFoundException;
import com.prem.workouttracker.model.Exercise;
import com.prem.workouttracker.model.User;
import com.prem.workouttracker.model.Workout;
import com.prem.workouttracker.model.WorkoutExercise;
import com.prem.workouttracker.repository.ExerciseRepository;
import com.prem.workouttracker.repository.UserRepository;
import com.prem.workouttracker.repository.WorkoutRepository;
import com.prem.workouttracker.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    private Long getCurrentUserId() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponse> findAllByUser() {
        Long userId = getCurrentUserId();
        return workoutRepository.findByUserIdOrderByScheduledDateDescScheduledTimeDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WorkoutResponse findById(Long id) {
        Workout workout = getWorkoutOwnedByCurrentUser(id);
        return toResponse(workout);
    }

    @Transactional
    public WorkoutResponse create(WorkoutRequest request) {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        Workout workout = Workout.builder()
                .user(user)
                .name(request.getName())
                .scheduledDate(request.getScheduledDate())
                .scheduledTime(request.getScheduledTime())
                .build();
        for (var exReq : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exReq.getExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise", exReq.getExerciseId()));
            WorkoutExercise we = WorkoutExercise.builder()
                    .workout(workout)
                    .exercise(exercise)
                    .sets(exReq.getSets())
                    .reps(exReq.getReps())
                    .weight(exReq.getWeight())
                    .build();
            workout.getWorkoutExercises().add(we);
        }
        workout = workoutRepository.save(workout);
        return toResponse(workout);
    }

    @Transactional
    public WorkoutResponse update(Long id, WorkoutRequest request) {
        Workout workout = getWorkoutOwnedByCurrentUser(id);
        workout.setName(request.getName());
        workout.setScheduledDate(request.getScheduledDate());
        workout.setScheduledTime(request.getScheduledTime());
        workout.getWorkoutExercises().clear();
        for (var exReq : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exReq.getExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise", exReq.getExerciseId()));
            WorkoutExercise we = WorkoutExercise.builder()
                    .workout(workout)
                    .exercise(exercise)
                    .sets(exReq.getSets())
                    .reps(exReq.getReps())
                    .weight(exReq.getWeight())
                    .build();
            workout.getWorkoutExercises().add(we);
        }
        workout = workoutRepository.save(workout);
        return toResponse(workout);
    }

    @Transactional
    public void delete(Long id) {
        Workout workout = getWorkoutOwnedByCurrentUser(id);
        workoutRepository.delete(workout);
    }

    private Workout getWorkoutOwnedByCurrentUser(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", workoutId));
        if (!workout.getUser().getId().equals(getCurrentUserId())) {
            throw new ResourceNotFoundException("Workout", workoutId);
        }
        return workout;
    }

    private WorkoutResponse toResponse(Workout w) {
        List<WorkoutExerciseResponse> exercises = w.getWorkoutExercises().stream()
                .map(we -> WorkoutExerciseResponse.builder()
                        .id(we.getId())
                        .exerciseId(we.getExercise().getId())
                        .exerciseName(we.getExercise().getName())
                        .sets(we.getSets())
                        .reps(we.getReps())
                        .weight(we.getWeight())
                        .build())
                .collect(Collectors.toList());
        return WorkoutResponse.builder()
                .id(w.getId())
                .name(w.getName())
                .scheduledDate(w.getScheduledDate())
                .scheduledTime(w.getScheduledTime())
                .createdAt(w.getCreatedAt())
                .exercises(exercises)
                .build();
    }
}
