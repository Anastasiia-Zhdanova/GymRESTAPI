# 🏋️ Gym Management REST API

## 📝 Project Overview

This project implements a multi-layered Java application using **Spring Boot** and **Hibernate/JPA** to provide a full-featured **RESTful API** for managing users (Trainees/Trainers) and training sessions.

The system is built on modern principles, featuring **Spring Security** and **Redis** for stateful session management, and incorporating **AOP** for request tracing.

---

## 🏗️ Project Structure (Layered Architecture)

| Package/File | Purpose (Layer) | Key Components |
| :--- | :--- | :--- |
| **`GymApplication.java`** | **Core Application** | Entry point for running the Spring Boot application. |
| **`config`** | **Configuration/Security** | `WebSecurityConfig`, `RedisConfig`, `LoggingAspect` (AOP). |
| **`controller`** | **API Layer (REST)** | `AuthenticationController`, `TraineeController`, `TrainerController`. |
| **`dto`** | **Data Contracts** | `*Request` and `*Response` classes (Validation, Serialization). |
| **`mapper`** | **Mapping** | `TraineeMapper`, `TrainerMapper` (MapStruct interfaces). |
| **`service`** | **Business Logic** | `AuthService`, `TraineeService`, `TrainerService` (`@Service`). |
| **`dao`** | **Data Access** | `UserDAO`, `TraineeDAO`, `TrainerDAO` (`@Repository`). |
| **`entity`** | **Persistence Model** | `User`, `Trainee`, `Trainer`, `Training`, `TrainingType` (JPA/Hibernate). |
| **`exception`** | **Error Handling** | `GlobalExceptionHandler`, Custom Exceptions. |
| **`util`** | **Utilities** | `PasswordUtil`, `UsernameUtil`, `HibernateUtil`. |
| **`src/main/resources`** | **Resources** | `application.yml` (Spring/DB/Redis), `logback.xml` (Logging config). |

---

## 🚀 Technologies and Setup

### Prerequisites

1.  **Java Development Kit (JDK) 17+**
2.  **PostgreSQL Server** (Running)
3.  **Redis Server** (Running on default port `6379`)
4.  **Maven 3.6+**

### Launching the API

1.  **Clean build and package the application:**
    ```bash
    mvn clean install
    ```

2.  **Run the Spring Boot application:**
    ```bash
    mvn spring-boot:run
    ```

The API will be running on the default port: `http://localhost:8080`.

---

## 🗺️ API Usage and Documentation

### Swagger Documentation

The full interactive API documentation is available here for testing all **17 endpoints**:

* **URL:** `http://localhost:8080/swagger-ui.html`

### Key Endpoints

Authentication is handled via **Redis Sessions**. After a successful `POST /login`, a session cookie (`JSESSIONID`) must be included in all protected requests.

| Req. | Description | Method | Path | Auth Required |
| :--- | :--- | :--- | :--- | :--- |
| 1, 2 | **Registration** (Trainee/Trainer) | `POST` | `/api/v1/auth/{type}/register` | ❌ |
| 3 | **Login** (Establish Session) | `POST` | `/api/v1/auth/login` | ❌ |
| 4 | **Change Password** | `PUT` | `/api/v1/auth/change-password` | ✅ |
| 7 | **Delete Trainee** (Cascade) | `DELETE`| `/api/v1/trainees/{username}` | ✅ |
| 14 | **Add Training** | `POST` | `/api/v1/trainings` | ✅ |
| 15, 16 | **Activate/Deactivate** | `PATCH` | `/api/v1/{type}s/{username}/status` | ✅ |
| 17 | **Get Training Types** | `GET` | `/api/v1/training-types` | ❌ |

---

## 🛡️ Engineering Excellence

* **Security:** Authentication relies on **Spring Security** and **BCrypt** hashing for password storage.
* **Traceability (AOP):** A unique **Transaction ID (TID)** is generated and logged for every REST request, enabling end-to-end tracing across the system.
* **Error Handling:** Custom exceptions are centrally managed by `GlobalExceptionHandler`, ensuring predictable and standardized HTTP status responses.
* **Code Quality:** Adherence to SOLID principles, DTO validation via **Jakarta Validation**, and efficient object mapping using **MapStruct**.