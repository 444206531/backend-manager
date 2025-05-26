# Backend Admin System Framework

A Spring Boot-based backend framework for admin systems, built with Java 17, Spring MVC, MyBatisPlus, and MySQL.

## Features

*   **Spring Boot 3.x** (using Java 17)
*   **Spring MVC** for RESTful APIs
*   **MyBatisPlus** for simplified data persistence
*   **MySQL** default database integration
*   **Lombok** for reduced boilerplate code
*   **Global Exception Handling** for consistent error responses
*   **Request/Response Logging** for debugging and monitoring
*   Sample CRUD module (`User`) to demonstrate usage

## Prerequisites

*   Java Development Kit (JDK) 17 or later
*   Maven 3.6.x or later
*   MySQL Server (ensure it's running and accessible)

## Project Structure

```
admin-system/
├── pom.xml                 # Maven project configuration
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── adminsystem/
    │   │               ├── AdminSystemApplication.java # Main application class
    │   │               ├── config/         # Spring configurations (MyBatisPlus, WebMvc)
    │   │               ├── controller/     # REST API controllers
    │   │               ├── entity/         # Domain objects/entities
    │   │               ├── exception/      # Global exception handlers
    │   │               ├── interceptor/    # Request interceptors (e.g., logging)
    │   │               ├── mapper/         # MyBatisPlus mapper interfaces
    │   │               └── service/        # Service layer (interfaces and implementations)
    │   └── resources/
    │       ├── application.properties  # Spring Boot configuration (database, server port)
    │       └── static/                 # Static assets (if any)
    │       └── templates/              # View templates (if any, for server-side rendering)
    └── test/
        └── java/                   # Unit and integration tests
```

## Configuration

1.  **Database Setup**:
    *   Ensure you have a MySQL instance running.
    *   Create a database for this application, e.g., `admin_system`.
    *   Open `src/main/resources/application.properties`.
    *   Update the following properties with your MySQL details:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/admin_system?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
        spring.datasource.username=your_mysql_username
        spring.datasource.password=your_mysql_password
        ```
    *   Replace `admin_system` if you used a different database name.
    *   Replace `your_mysql_username` and `your_mysql_password` with your actual MySQL credentials.

2.  **Server Port**:
    *   The application runs on port `8080` by default. You can change this in `application.properties`:
        ```properties
        server.port=8080
        ```

## Building and Running the Application

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd admin-system
    ```

2.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, you can run the packaged JAR file:
    ```bash
    java -jar target/admin-system-0.0.1-SNAPSHOT.jar
    ```

The application should now be running and accessible at `http://localhost:8080`.

## Adding a New Module/Entity

To add a new feature (e.g., managing "Products"):

1.  **Entity**: Create `Product.java` in `com.example.adminsystem.entity`.
    ```java
    // Example Product.java
    package com.example.adminsystem.entity;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;

    @Data
    public class Product {
        @TableId(type = IdType.AUTO)
        private Long id;
        private String name;
        private Double price;
    }
    ```
2.  **Mapper**: Create `ProductMapper.java` in `com.example.adminsystem.mapper` (extends `BaseMapper<Product>`).
    ```java
    // Example ProductMapper.java
    package com.example.adminsystem.mapper;
    import com.baomidou.mybatisplus.core.mapper.BaseMapper;
    import com.example.adminsystem.entity.Product;
    // @Mapper // Not strictly needed if @MapperScan is configured
    public interface ProductMapper extends BaseMapper<Product> {}
    ```
3.  **Service**:
    *   Create `ProductService.java` (interface) in `com.example.adminsystem.service` (extends `IService<Product>`).
    *   Create `ProductServiceImpl.java` (implementation) in `com.example.adminsystem.service` (extends `ServiceImpl<ProductMapper, Product>` and implements `ProductService`).
4.  **Controller**: Create `ProductController.java` in `com.example.adminsystem.controller` with REST endpoints for Product.
5.  **Database Table**: Ensure you have a corresponding table in your MySQL database (e.g., `product` or `sys_product` if you follow a naming convention). MyBatisPlus can also be configured for auto table creation/update for development, but this is usually disabled in production.

## API Endpoints

The sample `User` module provides the following endpoints:

*   `POST /api/users`: Create a new user.
*   `GET /api/users/{id}`: Get a user by ID.
*   `GET /api/users`: Get all users.
*   `PUT /api/users/{id}`: Update an existing user.
*   `DELETE /api/users/{id}`: Delete a user by ID.

---

This framework provides a starting point. You can extend it by adding more features like security (Spring Security), validation, caching, etc.
