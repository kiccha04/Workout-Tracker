package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.ExerciseResponse;
import com.prem.workouttracker.exception.ResourceNotFoundException;
import com.prem.workouttracker.model.Exercise;
import com.prem.workouttracker.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

	@Mock
	private ExerciseRepository exerciseRepository;

	@InjectMocks
	private ExerciseService exerciseService;

	@Test
	void findAll_returnsAllExercises() {
		Exercise e1 = Exercise.builder().id(1L).name("Squat").category("Strength").muscleGroup("Legs").build();
		Exercise e2 = Exercise.builder().id(2L).name("Bench Press").category("Strength").muscleGroup("Chest").build();
		when(exerciseRepository.findAll()).thenReturn(List.of(e1, e2));

		List<ExerciseResponse> result = exerciseService.findAll();

		assertThat(result).hasSize(2);
		assertThat(result.get(0).getName()).isEqualTo("Squat");
		assertThat(result.get(1).getName()).isEqualTo("Bench Press");
	}

	@Test
	void findById_returnsExerciseWhenExists() {
		Exercise e = Exercise.builder().id(1L).name("Deadlift").description("Barbell deadlift").category("Strength").muscleGroup("Back").build();
		when(exerciseRepository.findById(1L)).thenReturn(Optional.of(e));

		ExerciseResponse response = exerciseService.findById(1L);

		assertThat(response.getId()).isEqualTo(1L);
		assertThat(response.getName()).isEqualTo("Deadlift");
		assertThat(response.getMuscleGroup()).isEqualTo("Back");
	}

	@Test
	void findById_throwsWhenNotExists() {
		when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> exerciseService.findById(999L))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("999");
	}
}
