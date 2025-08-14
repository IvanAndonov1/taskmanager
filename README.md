# Task Manager API

A simple Spring Boot REST API for managing personal tasks, with support for user registration, authentication, and role-based authorization.

## Features

- JWT Authentication (Login & Registration)
- Role-based Access Control (USER & ADMIN)
- Full CRUD for Tasks (per authenticated user)
- Secured with Spring Security
- Dockerized (App + MySQL)
- Built with Maven

---

## API Endpoints

### Authentication

| Method | Endpoint        | Description       |
|--------|------------------|-------------------|
| POST   | `/auth/register` | Register new user |
| POST   | `/auth/login`    | Get JWT token     |

### Tasks (Authenticated Users Only)

| Method | Endpoint      | Description       |
|--------|---------------|-------------------|
| GET    | `/tasks`      | Get all tasks     |
| POST   | `/tasks`      | Create a new task |
| PUT    | `/tasks/{id}` | Update task       |
| DELETE | `/tasks/{id}` | Delete task       |

---

## Running with Docker

1. **Build the project:**
   ```bash
   mvn clean package
   ```

2. **Start MySQL container:**
   ```bash
   docker run --name mysql-db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=taskmanager -p 3307:3306 -d mysql:8
   ```

3. **Build the Docker image for the app:**
   ```bash
   docker build -t taskmanager-app .
   ```

4. **Run the app container:**
   ```bash
   docker run --name taskmanager -p 8080:8080 taskmanager-app
   ```

---

## Configuration

Ensure your `application.properties` contains the following:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/taskmanager
spring.datasource.username=root
spring.datasource.password=secret

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## Using the JWT Token

After logging in, copy the `token` from `/auth/login` and include it in your request headers:

```
Authorization: Bearer <your_token_here>
```

---

## Project Structure

```
.
├── src
│   └── main
│       ├── java
│       │   └── com.IvanAndonov1.taskmanager
│       │       ├── controller
│       │       ├── exception
│       │       ├── model
│       │       ├── repository
│       │       ├── security
│       │       └── service
│       └── resources
│           └── application.properties
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- Maven
- Docker

---

## Author

**Ivan Andonov**  
[GitHub Profile](https://github.com/IvanAndonov1)

---

## License

This project is licensed under the MIT License.
