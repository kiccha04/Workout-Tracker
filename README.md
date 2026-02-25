# 🏋️ Workout Tracker REST API

A Spring Boot REST API for tracking workouts, exercises, and basic progress for individual users.  
The application uses **JWT-based authentication** to securely manage user-specific workout data.

---

## 📌 Overview

This project demonstrates how to build a **secure backend application** using Spring Boot and Spring Security.  
Users can register, log in, and manage their workouts. All APIs are protected using **JWT (JSON Web Tokens)** and follow a clean layered architecture.

---

## 🛠 Tech Stack

- **Language**: Java  
- **Framework**: Spring Boot  
- **Security**: Spring Security + JWT  
- **Database**: MySQL  
- **ORM**: Spring Data JPA / Hibernate  
- **Build Tool**: Maven  
- **API Documentation**: Swagger (springdoc-openapi)  
- **Testing**: JUnit 5, Mockito, H2 (for tests)

---

## 🔐 Authentication & Security

- User registration and login using email and password  
- Passwords encrypted using **BCrypt**  
- Stateless authentication using **JWT (Bearer Token)**  
- Custom JWT filter for token validation  
- Users can access **only their own workout data**

---

## ✨ Features

### 👤 Authentication
- Register new users  
- Login and receive JWT token  
- Secure API access using `Authorization: Bearer <token>`

### 🏃 Exercise Catalog
- Predefined list of exercises  
- Read-only access for users  

### 📅 Workouts
- Create, update, delete workouts  
- Add exercises with sets, reps, and weight  
- Schedule workouts by date and time  
- Workouts are scoped to the authenticated user  

### 📊 Reports
- Total workout count  
- Weekly workout count  
- Workout history for the current user  

### 📘 API Documentation
- Swagger UI enabled  
- JWT authentication supported in Swagger  

---

## 🧱 Project Structure
com.prem.workouttracker
├── config # Security and Swagger configuration
├── security # JWT utility, filter, user details
├── controller # REST controllers
├── service # Business logic
├── repository # JPA repositories
├── model # Entity classes
├── dto # Request and response DTOs
└── exception # Global exception handling


---

## 🗄 Database

- Uses **MySQL**  
- JPA entities for User, Workout, Exercise, and WorkoutExercise  
- Relationships ensure user-specific data access  

Create database:
sql

## CREATE DATABASE workout_tracker;
▶️ Running the Application

Clone the repository

Configure MySQL credentials in application.properties

Run the application:

./mvnw spring-boot:run

Open Swagger UI:

http://localhost:8080/swagger-ui.html
🎯 Learning Outcomes

Implemented JWT-based authentication in Spring Boot

Secured REST APIs using Spring Security

Designed layered backend architecture

Used DTOs and global exception handling

Built user-scoped APIs for real-world use cases

🚀 Future Enhancements

Refresh token support

Role-based access control

Dockerization

Cloud deployment

Advanced workout analytics

👤 Author

Prem T V
Java Backend Developer
