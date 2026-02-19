package com.prem.workouttracker.service;

import com.prem.workouttracker.dto.AuthResponse;
import com.prem.workouttracker.dto.LoginRequest;
import com.prem.workouttracker.dto.RegisterRequest;
import com.prem.workouttracker.exception.BadRequestException;
import com.prem.workouttracker.model.User;
import com.prem.workouttracker.repository.UserRepository;
import com.prem.workouttracker.security.JwtUtil;
import com.prem.workouttracker.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        String token = jwtUtil.generateToken(principal.getEmail(), principal.getId());
        return AuthResponse.builder()
                .token(token)
                .userId(principal.getId())
                .email(principal.getEmail())
                .build();
    }
}
