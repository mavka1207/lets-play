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

##  Setup & Run

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd lets-play

2. **Build the project**
   ```bash
   ./mvn clean install

3. **Run the application**
   ```bash
   ./mvn spring-boot:run

4. **Access the API**
   ```arduino
    https://localhost:8443  

       
## Run with Maven Wrapper

If Maven is not installed globally, you can still build and run the project using the provided Maven Wrapper.

**For macOS / Linux:**
```bash
./mvnw clean install
./mvnw spring-boot:run

**For Windows:**
mvnw.cmd clean install
mvnw.cmd spring-boot:run

##  API Endpoints

**Authentication**
| Method | Endpoint         | Access | Description                    |
| ------ | ---------------- | ------ | ------------------------------ |
| `POST` | `/auth/register` | Public | Register a new user            |
| `POST` | `/auth/login`    | Public | Authenticate and receive a JWT |


**Users**
| Method   | Endpoint      | Access          | Description    |
| -------- | ------------- | --------------- | -------------- |
| `GET`    | `/users`      | `ADMIN`         | Get all users  |
| `GET`    | `/users/{id}` | `ADMIN`         | Get user by ID |
| `PUT`    | `/users/{id}` | `ADMIN` or Self | Update user    |
| `DELETE` | `/users/{id}` | `ADMIN`         | Delete user    |


**Products**
| Method   | Endpoint         | Access             | Description          |
| -------- | ---------------- | ------------------ | -------------------- |
| `GET`    | `/products`      | Public             | Get all products     |
| `GET`    | `/products/{id}` | Public             | Get product by ID    |
| `POST`   | `/products`      | Authenticated User | Create a new product |
| `PUT`    | `/products/{id}` | `ADMIN` or Owner   | Update a product     |
| `DELETE` | `/products/{id}` | `ADMIN` or Owner   | Delete a product     |


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

**Why this matters:**

   **Immutability:** Dependencies are declared final and cannot be reassigned.

   **Clarity:** All dependencies are visible and required in the constructor.

   **Testability:** Easier to pass mock objects for unit testing.

## Author

**Kateryna Ovsiienko**