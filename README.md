# Spring Boot JWT Authentication Practice

This repository is a practice project based on the [Inflearn course on Spring Boot & JWT](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/dashboard).  
The goal is to understand how JWT (JSON Web Token) works in authentication and authorization using Spring Boot and Spring Security.

---
<br><br>
## 📝 About the Course

> "JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties." — [jwt.io](https://jwt.io)

JWT is a widely adopted token-based authentication method.  
This tutorial demonstrates how to implement **sign-up**, **login**, and **authorization** logic using Spring Security.

---

## 🚀 What I Learned

- Basics of **Spring Security**
- How JWT works and how to use it for **authentication & authorization**
- Building **sign-up** and **login** APIs
- Role-based access control using authorities (`USER`, `ADMIN`)

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (`io.jsonwebtoken:jjwt`)
- H2 Database (for development/testing)
- Gradle

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── me/silvernine/jwttutorial/
│   │       ├── config/         # Spring Security configuration
│   │       ├── controller/     # API controllers
│   │       ├── dto/            # Request/Response DTOs
│   │       ├── entity/         # JPA entities
│   │       ├── exception/      # Custom exceptions
│   │       ├── handler/        # Global exception handler
│   │       ├── jwt/            # JWT token provider, filter, etc.
│   │       ├── repository/     # Spring Data JPA repositories
│   │       ├── service/        # Business logic
│   │       └── util/           # Utility classes
│   └── resources/
│       ├── application.yml     # Application configuration
│       └── data.sql            # Initial test data (e.g., default user/roles)
```

---

## 🔐 Key Features

- JWT access token generation and validation
- Custom JWT authentication filter
- Login and sign-up API endpoints
- Role-based authorization with protected routes

---

## 💬 Notes

This project was built for study purposes while following the Inflearn tutorial.  
It helped me understand the authentication flow and security configuration with Spring Boot.


