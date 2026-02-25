# 🏋️ Workout Tracker REST API

A Spring Boot REST API for tracking workouts, exercises, and basic progress for individual users.  
The application uses **JWT-based authentication** to securely manage user-specific workout data.

---

## 📌 Overview

This project demonstrates how to build a **secure backend application** using Spring Boot and Spring Security.  
Users can register, log in, and manage their workouts. All APIs are protected using **JWT (JSON Web Tokens)**.

---

## 🛠 Tech Stack

- **Language**: Java  
- **Framework**: Spring Boot  
- **Security**: Spring Security + JWT  
- **Database**: MySQL  
- **ORM**: Spring Data JPA / Hibernate  
- **Build Tool**: Maven  
- **API Docs**: Swagger (springdoc-openapi)  
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
