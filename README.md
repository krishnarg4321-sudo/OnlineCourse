# E-Learning Course Enrolment Portal

Full-stack LMS built with Spring Boot + PostgreSQL + React (Vite/Tailwind).

## Tech Stack
- Backend: Java 17, Spring Boot 3, Spring Security JWT, JPA/Hibernate, Flyway
- DB: PostgreSQL
- Frontend: React, Vite, Tailwind CSS, React Router v6, Axios
- Testing: JUnit 5, Mockito
- API docs: Swagger (springdoc)

## Backend Setup
1. Ensure Java 17+ and PostgreSQL are installed.
2. Create DB: `onlinecourse`.
3. Configure env vars (or defaults from `application.yml`):
   - `DB_URL=jdbc:postgresql://localhost:5432/onlinecourse`
   - `DB_USERNAME=postgres`
   - `DB_PASSWORD=postgres`
   - `JWT_SECRET=<long secret at least 32 chars>`
   - `CORS_ALLOWED_ORIGINS=http://localhost:5173,https://example.com`
4. Run backend:
   ```bash
   mvn spring-boot:run
   ```

Flyway migrations auto-create schema and seed demo data.

## Frontend Setup
```bash
cd frontend
npm install
npm run dev
```
Optional env: `VITE_API_BASE_URL=http://localhost:8080/api`

## Demo Accounts (password: `password`)
- Admin: `admin@portal.com`
- Instructor: `instructor1@portal.com`
- Student: `student1@portal.com`

## Swagger
- http://localhost:8080/swagger-ui.html

## Build/Test
```bash
mvn test
mvn package
cd frontend && npm run build
```

## Troubleshooting
- **JWT key error**: set a longer `JWT_SECRET`.
- **Flyway/JPA validate error**: ensure PostgreSQL is reachable and empty or reset schema.
- **CORS issues**: include frontend origin in `CORS_ALLOWED_ORIGINS`.
