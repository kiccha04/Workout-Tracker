# Workout Tracker API

A RESTful backend for tracking workout plans, scheduled workouts, and basic progress reports.

Built with **Spring Boot 4**, **Spring Security 7**, **JWT**, **MySQL**, **Spring Data JPA**, and **springdoc-openapi**.

---

## Tech Stack

- **Language**: Java 25
- **Framework**: Spring Boot 4.0.2 (Spring Framework 7)
- **Security**: Spring Security 7 + JWT (jjwt 0.12.x)
- **Database**: MySQL 8 (with Spring Data JPA / Hibernate)
- **Build**: Maven (wrapper included)
- **API Docs**: springdoc-openapi + Swagger UI
- **Tests**: JUnit 5, Mockito, Spring Boot Test, H2 in-memory

---

## Features

- **User Authentication**
  - Register (`/api/auth/register`) and login (`/api/auth/login`) with email + password
  - JWT-based stateless authentication (`Authorization: Bearer <token>`)

- **Exercise Catalog**
  - Seeded reference data for common exercises (name, description, category, muscle group)
  - Read-only endpoints to list exercises

- **Workouts**
  - CRUD for workouts, scoped to the authenticated user
  - Each workout can contain multiple exercises with sets, reps, and weight
  - Simple scheduling via date + time fields

- **Reports**
  - Summary for the current user:
    - Total workout count
    - Weekly workout count (Mon–Sun)
    - Workout history list

- **API Documentation**
  - Swagger UI with JWT support

---

## Project Structure

Root package: `com.prem.workouttracker`

- `config`
  - `SecurityConfig` – Spring Security 6 style configuration (JWT filter, stateless sessions, URL rules)
  - `OpenApiConfig` – OpenAPI / Swagger UI configuration with Bearer JWT
  - `DataSeeder` – Seeds initial exercise data on startup (non-test profile)

- `security`
  - `JwtUtil` – JWT generation/validation
  - `JwtAuthFilter` – Reads Bearer tokens and populates SecurityContext
  - `UserDetailsServiceImpl`, `UserPrincipal` – Spring Security user details

- `model`
  - `User` – email, encoded password, name, createdAt
  - `Exercise` – reference data (name, description, category, muscleGroup)
  - `Workout` – belongs to `User`, has name, scheduled date/time
  - `WorkoutExercise` – joins `Workout` and `Exercise` with sets/reps/weight

- `repository`
  - `UserRepository`, `ExerciseRepository`, `WorkoutRepository`, `WorkoutExerciseRepository`

- `dto`
  - Auth DTOs: `RegisterRequest`, `LoginRequest`, `AuthResponse`
  - Workout DTOs: `WorkoutRequest`, `WorkoutResponse`, `WorkoutExerciseRequest`, `WorkoutExerciseResponse`
  - `ExerciseResponse`, `ReportResponse`

- `service`
  - `AuthService` – registration + login
  - `WorkoutService` – all workout CRUD logic, scoped to current user
  - `ExerciseService` – exercise read APIs
  - `ReportService` – builds user report from workouts

- `controller`
  - `AuthController` – `/api/auth/**`
  - `WorkoutController` – `/api/workouts/**`
  - `ExerciseController` – `/api/exercises/**`
  - `ReportController` – `/api/reports`

- `exception`
  - `ResourceNotFoundException`, `BadRequestException`, `GlobalExceptionHandler`

---

## Setup

### 1. Prerequisites

- Java 25 (JDK 25+)
- Maven 3.8+ (or use `mvnw`/`mvnw.cmd`)
- MySQL 8 running locally

### 2. Database

Create a database (or let Hibernate create it via the connection string):

CREATE DATABASE workout_tracker;
