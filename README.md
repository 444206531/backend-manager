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

---

## Frontend Admin System (Vue 3 + TypeScript + Element Plus)

The frontend application is located in the `frontend/` directory. It's built with Vue 3, TypeScript, Vite, and uses Element Plus for UI components.

### Frontend Prerequisites

*   Node.js (LTS version, e.g., 18.x or 20.x)
*   npm or yarn (or pnpm)

### Frontend Project Structure (`frontend/`)

```
frontend/
├── public/                 # Static assets (favicon, etc.)
├── src/
│   ├── api/                # API service modules (Axios)
│   ├── assets/             # CSS, images, fonts
│   ├── components/         # Reusable Vue components
│   ├── layouts/            # Main application layout(s)
│   ├── router/             # Vue Router configuration
│   ├── store/              # Pinia state management stores
│   ├── types/              # TypeScript type definitions
│   ├── views/              # Page-level Vue components
│   ├── App.vue             # Root Vue component
│   ├── main.ts             # Application entry point
│   └── shims-vue.d.ts      # Vue component type shims
├── index.html              # Vite entry HTML
├── package.json            # Project dependencies and scripts
├── vite.config.ts          # Vite configuration (build, dev server, proxy)
├── tsconfig.json           # TypeScript configuration
└── README.md               # (Optional: could have a more detailed frontend-specific README here)
```

### Frontend Setup and Configuration

1.  **Navigate to the frontend directory:**
    ```bash
    cd frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    # or
    # yarn install
    # or
    # pnpm install
    ```

3.  **Backend API Configuration (for Development):**
    The frontend development server (Vite) is configured to proxy API requests from `/api` to the backend server. By default, it assumes the backend is running at `http://localhost:8080`.
    If your backend runs on a different port or host, update the `target` in the `server.proxy` section of `frontend/vite.config.ts`:
    ```typescript
    // frontend/vite.config.ts
    // ...
    server: {
      proxy: {
        '/api': {
          target: 'http://your-backend-host:your-backend-port', // Change this
          changeOrigin: true,
        }
      }
    }
    // ...
    ```

4.  **Backend API Configuration (for Production):**
    When building the frontend for production, the `baseURL` for API calls in `frontend/src/api/axiosInstance.ts` is set to `/api`. This assumes your production deployment will serve the frontend and backend under the same domain, with API requests to `/api/...` correctly routed to the backend service (e.g., via a reverse proxy like Nginx).
    If your production API is on a completely different domain, you'll need to adjust `axiosInstance.defaults.baseURL` in `frontend/src/api/axiosInstance.ts` to the absolute API URL before building for production.

### Running the Frontend Development Server

1.  Ensure the backend application is running and accessible (especially if the frontend makes API calls on startup).
2.  Navigate to the `frontend/` directory.
3.  Run the Vite development server:
    ```bash
    npm run dev
    # or
    # yarn dev
    # or
    # pnpm dev
    ```
    The application should typically be available at `http://localhost:5173` (Vite's default port, but it will show the correct URL in the terminal).

### Building the Frontend for Production

1.  Navigate to the `frontend/` directory.
2.  Run the build script:
    ```bash
    npm run build
    # or
    # yarn build
    # or
    # pnpm build
    ```
    This will generate a `dist/` directory within `frontend/` containing the optimized static assets for deployment.

### Frontend Linting and Type Checking

*   To run TypeScript type checking (as part of the build script):
    ```bash
    npm run build 
    # (vue-tsc --noEmit is part of the build script)
    ```
*   For standalone type checking:
    ```bash
    # Add to package.json scripts: "type-check": "vue-tsc --noEmit"
    # Then run: npm run type-check
    ```
*   Linting (ESLint, Prettier) is not yet configured in this base framework but can be added.
