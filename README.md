#  Let's Play API

A **RESTful CRUD API** built with **Spring Boot** and **MongoDB**, providing secure user and product management functionalities.  
Implements **JWT authentication**, **role-based authorization**, **BCrypt password hashing**, and **input validation** following industry best practices.

---

##  Project Description

This project demonstrates how to build a **secure CRUD API** following **RESTful principles**.  
It includes:

- User registration, login, and full CRUD management  
- Product CRUD operations  
- Role-based and ownership-based access control  
- Centralized error handling and HTTPS encryption

---

##  Features

- **User Management:** Registration, login, and CRUD operations (admin access).  
- **Product Management:** Full CRUD operations for products.  
- **Authentication:** Secure token-based authentication using JWT.  
- **Authorization:** Role-based access control (`USER`, `ADMIN`) and ownership rules.  
- **Security:** BCrypt password hashing, HTTPS encryption, and DTO validation.  
- **Error Handling:** Global exception handling to prevent raw 5xx responses.

---

##  Technologies Used

- **Java 21**  
- **Spring Boot 3**  
- **Spring Security**  
- **Spring Data MongoDB**  
- **JWT (io.jsonwebtoken)**  
- **Maven**

---

##  Prerequisites

| Tool | Version | Notes |
|------|----------|-------|
| Java | 17+ | Recommended: 21 |
| Maven | 3.8+ | For build & run |
| MongoDB | latest | Running at `mongodb://localhost:27017` |

---

## Setup

1. **Clone the repository**
   ```sh
   git clone https://01.gritlab.ax/git/kovsiien/lets-play.git
   cd lets-play
   ```

2. **Generate a keystore for HTTPS**
   ```sh
   keytool -genkeypair -alias letsplay -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore.p12 -validity 3650 -storepass changeit
   ```

3. **Build and run**

   **For macOS / Linux:**
   ```bash
   ./mvnw clean install
   export JWT_SECRET=dev-jwt-secret-key-2025 && ./mvnw spring-boot:run
      ```

   **For Windows:**
      ```
   mvnw.cmd clean install
   set JWT_SECRET=dev-jwt-secret-key-2025 & mvnw.cmd spring-boot:run
      ```

4. **Use Postman to access the API**
   ```
   https://localhost:8443
   ```
       


## API Endpoints

**Authentication**
| Method | Endpoint         | Access | Description                    |
| ------ | ---------------- | ------ | ------------------------------ |
| `POST` | `/auth/register` | Public | Register a new user (see below)|
| `POST` | `/auth/login`    | Public | Authenticate and receive a JWT |

### Registering with a Role

To register a user with a specific role (e.g., ADMIN or USER), include the `role` field in your request body:

```json
{
  "name": "adminuser",
  "email": "admin@example.com",
  "password": "yourpassword",
  "role": "ADMIN"
}
```

If `role` is omitted or invalid, the user will be registered with the default `USER` role.


##  Security Measures

| Measure                   | Description                                                                    |
| ------------------------- | ------------------------------------------------------------------------------ |
| **Password Hashing**      | Passwords are hashed and salted using `BCryptPasswordEncoder`.                 |
| **Input Validation**      | Bean Validation (`@NotBlank`, `@Email`, etc.) validates all incoming data.     |
| **HTTPS**                 | Encrypts all data in transit for secure communication.                         |
| **Authorization**         | `@PreAuthorize` ensures only authorized users access protected endpoints.      |
| **Sensitive Information** | JWT secret is externalized; no passwords or secrets are returned in responses. |


## Dependency Injection

This project uses constructor-based dependency injection instead of field injection (@Autowired).


## Author

**Kateryna Ovsiienko**