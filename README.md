# Spring Boot + MongoDB CRUD API (Let's Play)

## 1. Project Overview
This project is a **RESTful CRUD API** built with **Spring Boot** and **MongoDB**. It provides functionalities for managing **users** and **products**, secured with JWT-based authentication and role-based authorization (**USER** vs **ADMIN**). The API is protected by HTTPS with a self-signed SSL certificate.

The API follows best practices: input validation, password hashing with BCrypt, DTO usage, global exception handling (no raw 5XX errors), and clean layered architecture.

### Key Features
- **User Management:** CRUD operations for users (ADMIN only)
- **Product Management:** CRUD operations for products (public GET, modifications require auth)
- **Authentication & Authorization:** JWT login/register, role checks
- **Security:** BCrypt password hashing, DTOs, validation, SSL
- **Error Handling:** Global exception handler with proper HTTP codes

---

## 2. Project Structure
```
src/
 ├── main/
 │    ├── java/com/example/letsplay/
 │    │    ├── controller/         # REST controllers (Auth, Users, Products)
 │    │    ├── converter/          # Entity ↔ DTO mappers
 │    │    ├── exceptions/         # Custom exception classes
 │    │    ├── model/              # MongoDB entities (User, Product)
 │    │    ├── repository/         # Spring Data MongoDB repositories
 │    │    ├── response/           # Standardized API responses
 │    │    ├── security/           # JWT, SecurityConfig, filters
 │    │    ├── services/           # Business logic layer
 │    │    ├── utils/              # Utility classes
 │    │    └── LetsplayApplication.java   # Main application entry point
 │    └── resources/
 │         ├── application.properties     # App configuration
 │         └── letsplay.p12               # SSL certificate (after generation)
 ├── test/java/com/example/letsplay/
 │    └── LetsplayApplicationTests.java   # Test suite
 ├── API-Documentation.md
 ├── README.md
 ├── pom.xml
 ├── mvnw / mvnw.cmd
```

---

## 3. Prerequisites
- **Java (JDK):** 21+
- **Maven:** 3.8+
- **MongoDB:** Community Edition, default port 27017
- **API Client:** Postman or curl

---

## 4. SSL Setup

### Generate a Self-Signed SSL Certificate
Navigate to `src/main/resources` and run:
```bash
keytool -genkeypair -alias letsplay -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore letsplay.p12 -validity 365 \
  -dname "CN=localhost, OU=gritlab, O=gritlab, L=Mariehamn, S=Åland, C=FI" \
  -storepass <your-password> -keypass <your-password>
```

 Replace `<your-password>` with a strong one. Configure it in `application.properties`:
```properties
server.port=8443
server.ssl.enabled=true
server.ssl.key-store=classpath:letsplay.p12
server.ssl.key-store-password=<your-password>
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=letsplay
```

---

## 5. Configure MongoDB
Export credentials:
```bash
export MONGO_USER=<username>
export MONGO_PASSWORD=<password>
```

In `application.properties`:
```properties
spring.data.mongodb.uri=mongodb://${MONGO_USER}:${MONGO_PASSWORD}@localhost:27017/letsplay_db
```

---

## 6. Build & Run
```bash
./mvnw clean install
./mvnw spring-boot:run
```
Application runs at: `https://localhost:8443`

---

## 7. Endpoints

###  Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/v1/auth/register` | Public | Register new user |
| POST | `/api/v1/auth/login` | Public | Login, returns JWT |

###  Users (ADMIN only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/users` | List users |
| GET | `/api/v1/users/{id}` | Get user by id |
| POST | `/api/v1/users` | Create user |
| PUT | `/api/v1/users/{id}` | Update user |
| DELETE | `/api/v1/users/{id}` | Delete user |

###  Products
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/v1/products` | Public | List products |
| GET | `/api/v1/products/{id}` | Public | Get product by id |
| POST | `/api/v1/products` | USER, ADMIN | Create product |
| PUT | `/api/v1/products/{id}` | ADMIN | Update product |
| DELETE | `/api/v1/products/{id}` | ADMIN | Delete product |

---

## 8. Role Matrix
| Role | Permissions |
|------|-------------|
| USER | Register/login, view products, create products |
| ADMIN | Full access: users & products management |

---

## 9. Example Requests

### Register
```bash
curl -X POST https://localhost:8443/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"Secret123","fullName":"Test User"}'
```

### Login
```bash
curl -X POST https://localhost:8443/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"Secret123"}'
```
Response:
```json
{ "accessToken": "<JWT_TOKEN>" }
```

### Public Products
```bash
curl https://localhost:8443/api/v1/products
```

### Authenticated Create Product
```bash
curl -X POST https://localhost:8443/api/v1/products \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"iPhone 14","description":"128GB","price":799.99,"stock":5}'
```

---

## 10. Security
- BCrypt password hashing
- DTO validation (`@NotBlank`, `@Email`, `@Size`, `@Positive`)
- No sensitive data in responses
- Global exception handling with 400/401/403/404/409
- HTTPS enabled with SSL

---

## 11. Testing
- Use Postman or curl for manual testing
- Automated tests in `LetsplayApplicationTests.java`

---

 This README is aligned with the given project structure and audit requirements.

