package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.WorkoutRequest;
import com.prem.workouttracker.dto.WorkoutResponse;
import com.prem.workouttracker.exception.ResourceNotFoundException;
import com.prem.workouttracker.model.Exercise;
import com.prem.workouttracker.model.User;
import com.prem.workouttracker.model.Workout;
import com.prem.workouttracker.model.WorkoutExercise;
import com.prem.workouttracker.repository.ExerciseRepository;
import com.prem.workouttracker.repository.UserRepository;
import com.prem.workouttracker.repository.WorkoutRepository;
import com.prem.workouttracker.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

	@Mock
	private WorkoutRepository workoutRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ExerciseRepository exerciseRepository;

	@InjectMocks
	private WorkoutService workoutService;

	private void setCurrentUser(Long userId) {
		User user = User.builder().id(userId).email("u@t.com").password("x").build();
		UserPrincipal principal = new UserPrincipal(user);
		Authentication auth = Mockito.mock(Authentication.class);
		when(auth.getPrincipal()).thenReturn(principal);
		SecurityContext context = Mockito.mock(SecurityContext.class);
		when(context.getAuthentication()).thenReturn(auth);
		SecurityContextHolder.setContext(context);
	}

	@Test
	void findById_returnsWorkoutWhenOwnedByUser() {
		setCurrentUser(1L);
		User user = User.builder().id(1L).email("u@t.com").build();
		Exercise ex = Exercise.builder().id(10L).name("Squat").build();
		Workout workout = Workout.builder().id(100L).user(user).name("Leg Day").build();
		WorkoutExercise we = WorkoutExercise.builder().id(1L).workout(workout).exercise(ex).sets(3).reps(10).weight(60.0).build();
		workout.getWorkoutExercises().add(we);
		when(workoutRepository.findById(100L)).thenReturn(Optional.of(workout));

		WorkoutResponse response = workoutService.findById(100L);

		assertThat(response.getId()).isEqualTo(100L);
		assertThat(response.getName()).isEqualTo("Leg Day");
		assertThat(response.getExercises()).hasSize(1);
		assertThat(response.getExercises().get(0).getExerciseName()).isEqualTo("Squat");
	}

	@Test
	void findById_throwsWhenWorkoutBelongsToOtherUser() {
		setCurrentUser(1L);
		User otherUser = User.builder().id(99L).email("other@t.com").build();
		Workout workout = Workout.builder().id(100L).user(otherUser).name("Other").build();
		when(workoutRepository.findById(100L)).thenReturn(Optional.of(workout));

		assertThatThrownBy(() -> workoutService.findById(100L))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void delete_removesWorkoutWhenOwnedByUser() {
		setCurrentUser(1L);
		User user = User.builder().id(1L).build();
		Workout workout = Workout.builder().id(100L).user(user).name("W").build();
		when(workoutRepository.findById(100L)).thenReturn(Optional.of(workout));

		workoutService.delete(100L);

		verify(workoutRepository).delete(workout);
	}
}
