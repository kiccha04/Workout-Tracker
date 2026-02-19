package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.AuthResponse;
import com.prem.workouttracker.dto.LoginRequest;
import com.prem.workouttracker.dto.RegisterRequest;
import com.prem.workouttracker.exception.BadRequestException;
import com.prem.workouttracker.model.User;
import com.prem.workouttracker.repository.UserRepository;
import com.prem.workouttracker.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtUtil jwtUtil;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private Authentication authentication;

	@InjectMocks
	private AuthService authService;

	@Test
	void register_createsUserAndReturnsToken() {
		RegisterRequest request = new RegisterRequest("user@test.com", "password123", "Test User");
		when(userRepository.existsByEmail("user@test.com")).thenReturn(false);
		when(passwordEncoder.encode("password123")).thenReturn("encoded");
		when(userRepository.save(any(User.class))).thenAnswer(inv -> {
			User u = inv.getArgument(0);
			u.setId(1L);
			return u;
		});
		when(jwtUtil.generateToken("user@test.com", 1L)).thenReturn("jwt-token");

		AuthResponse response = authService.register(request);

		assertThat(response.getEmail()).isEqualTo("user@test.com");
		assertThat(response.getToken()).isEqualTo("jwt-token");
		assertThat(response.getUserId()).isEqualTo(1L);
		verify(userRepository).save(any(User.class));
	}

	@Test
	void register_throwsWhenEmailExists() {
		RegisterRequest request = new RegisterRequest("user@test.com", "password123", null);
		when(userRepository.existsByEmail("user@test.com")).thenReturn(true);

		assertThatThrownBy(() -> authService.register(request))
				.isInstanceOf(BadRequestException.class)
				.hasMessageContaining("already registered");
		verify(userRepository).existsByEmail("user@test.com");
	}

	@Test
	void login_returnsTokenForValidCredentials() {
		LoginRequest request = new LoginRequest("user@test.com", "password123");
		User user = User.builder().id(1L).email("user@test.com").password("encoded").build();
		com.prem.workouttracker.security.UserPrincipal principal = new com.prem.workouttracker.security.UserPrincipal(user);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(principal);
		when(jwtUtil.generateToken("user@test.com", 1L)).thenReturn("jwt-token");

		AuthResponse response = authService.login(request);

		assertThat(response.getEmail()).isEqualTo("user@test.com");
		assertThat(response.getToken()).isEqualTo("jwt-token");
		assertThat(response.getUserId()).isEqualTo(1L);
	}
}
