package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.ExerciseResponse;
import com.prem.workouttracker.exception.ResourceNotFoundException;
import com.prem.workouttracker.model.Exercise;
import com.prem.workouttracker.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public List<ExerciseResponse> findAll() {
        return exerciseRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExerciseResponse findById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", id));
        return toResponse(exercise);
    }

    private ExerciseResponse toResponse(Exercise e) {
        return ExerciseResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .category(e.getCategory())
                .muscleGroup(e.getMuscleGroup())
                .build();
    }
}
