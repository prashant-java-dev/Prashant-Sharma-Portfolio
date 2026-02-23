# 👨‍💻 Prashant Sharma - Full Stack Portfolio

[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot%203.2-red?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/Frontend-React%2018-blue?style=flat-square&logo=react)](https://reactjs.org/)
[![Railway](https://img.shields.io/badge/Deployment-Railway-black?style=flat-square&logo=railway)](https://railway.app/)

A professional, production-ready developer portfolio showcasing full-stack capabilities, modern design patterns, and clean code architecture.

---

## 🚀 Key Highlights for Recruiters
*   **Asynchronous Processing:** Contact form submissions utilize Spring's `@Async` to ensure zero UI lag during email delivery.
*   **Secure API Architecture:** Implements centralized Exception Handling and Input Validation to ensure data integrity and prevent information leakage.
*   **Production Configuration:** Environment-based setup using `.env` for secrets management and CORS control.
*   **Hybrid File Serving:** Robust resume distribution merging backend API flexibility with frontend static asset performance.

---

## 🛠️ Tech Stack
*   **Backend:** Java 17, Spring Boot, Spring Mail, Jakarta Validation, Maven.
*   **Frontend:** React (Vite), Tailwind CSS, Lucide React, Axios.
*   **Database:** MongoDB (Integrated for health monitoring and future scalability).

---

## 📂 Project Structure
*   `backend/prashant_portfolio/`: The core Spring Boot engine.
*   `frontend/`: The high-performance React UI.

---

## ⚙️ Local Development

### 1. Backend Setup
1.  Navigate to `backend/prashant_portfolio`
2.  Configure `.env` based on `.env.example`:
    ```ini
    SERVER_PORT=8080
    MAIL_USERNAME=your_gmail@gmail.com
    MAIL_PASSWORD=your_app_password
    MAIL_TO=recipient@gmail.com
    ALLOWED_ORIGINS=http://localhost:3000
    ```
3.  Launch: `./mvnw spring-boot:run`

### 2. Frontend Setup
1.  Navigate to `frontend/`
2.  Install & Start:
    ```bash
    npm install
    npm run dev
    ```

---

## 🛡️ Security & Best Practices
*   **Centralized Error Handling:** Managed via `@RestControllerAdvice` for uniform error responses.
*   **Zero Hardcoding:** All sensitive data (credentials, API URLs) is externalized via environment variables.
*   **Non-Blocking operations:** Heavy tasks (like SMTP) never block the main request thread.
*   **Responsive UI:** Fully optimized for Mobile, Tablet, and Desktop resolutions.

---

## 📈 Future Scalability
*   The project is designed with a **Repository Pattern** ready for MongoDB integration to store inquiry logs or dynamic project details.
*   Ready for **Dockerization** for automated scaling.

---
*Created by [Prashant Sharma](https://github.com/prashant-java-dev)*
