# Amazing Bookstore: A Microservices E-commerce Platform

Amazing Bookstore is a modern e-commerce platform built on a distributed, cloud-native architecture. It leverages Spring Boot and a polyglot persistence approach (SQL & NoSQL) to create a scalable, resilient, and maintainable online book retail system. The project showcases best practices in TDD, clean code, event-driven communication via Kafka, and service discovery with Eureka, all orchestrated through a central API Gateway.

-----

## 🏢 Business Overview

The core purpose of this project is to build a fully functional online bookstore. The system is designed to handle the entire lifecycle of an e-commerce transaction, from product catalog management and inventory control to order processing and fulfillment. By adopting a microservices architecture, the platform is built for scalability and independent feature development, mirroring the structure of modern, large-scale web applications.

-----

## 💻 Technology Stack

This project utilizes a curated selection of modern, robust technologies to build a cloud-ready application suite.

| Category | Technology/Library | Version | Purpose |
| --- | --- | --- | --- |
| **Language & Framework** | Java | 21 | Core application language |
| | Spring Boot | 3.3.4+ | Foundation for building microservices |
| **Databases** | MySQL | | Relational data storage for `product-service` |
| | Oracle | | Relational data storage for `inventory-service` |
| | MongoDB | | NoSQL document storage for `order-service` |
| **Microservice Infrastructure** | Spring Cloud Gateway | 2023.0.2+ | Single entry point, routing, and cross-cutting concerns (future: security) |
| | Spring Cloud Netflix Eureka | 2023.0.2+ | Service registration and discovery |
| | Spring Cloud Config Server | 2023.0.2+ | Centralized external configuration management |
| **Asynchronous Communication** | Apache Kafka | | Event-driven communication for decoupling services (e.g., order confirmation) |
| | Spring Kafka | | Integration library for Kafka in Spring applications |
| **Data & Persistence** | Spring Data JPA & Flyway | | ORM and database schema migration for SQL databases |
| | Spring Data MongoDB | | Repository support for MongoDB |
| **Developer Productivity** | Lombok | 1.18.38 | Boilerplate code reduction (getters, setters, builders) |
| | MapStruct | 1.5.3.Final | High-performance bean mapping |
| **API & Documentation** | Springdoc OpenAPI | 2.5.0 | Automatic generation of API documentation (Swagger UI) |
| **Testing** | JUnit 5, Mockito | | Unit and integration testing framework |

-----

## 🏗️ Architecture

The system is designed as a set of independent, collaborating microservices, each with its own distinct responsibility and data store. This separation of concerns allows for independent development, deployment, and scaling.

#### Core Services

* **API Gateway (`api-gateway`)**: The single entry point for all external client requests. It handles routing to downstream services and is the designated layer for implementing cross-cutting concerns like security (Keycloak integration planned). 
* **Discovery Server (`discovery-server`)**: Utilizes Netflix Eureka to provide a dynamic service registry.  All microservices register themselves upon startup, allowing for robust, location-transparent inter-service communication.
* **Config Server (`config-server`)**: Provides centralized and environment-specific configuration for all services from a Git repository, enabling dynamic configuration updates without service restarts. 

#### Business Logic Services

* **Product Service (`product-service`)**: Manages the book catalog, including details like titles, authors, and descriptions. It uses **MySQL** for persistence and exposes a REST API for CRUD operations. 
* **Inventory Service (`inventory-service`)**: Tracks stock levels and prices for each book. It also manages the stock reservation lifecycle during the order process. It uses an **Oracle** database for transactional integrity. 
* **Order Service (`order-service`)**: Handles the creation and management of customer orders. It communicates with other services to reserve stock and retrieve product information. Due to the complex and nested nature of order documents, it uses **MongoDB** for flexible data storage. 

#### Communication

* **Synchronous (REST)**: For direct request/response interactions (e.g., fetching product details), services use **Spring Cloud OpenFeign**, a declarative REST client that integrates seamlessly with Eureka for service discovery.
* **Asynchronous (Events)**: For decoupling processes, the system uses **Apache Kafka**. For example, when an order is created, the `order-service` publishes an `OrderPlacedEvent`. The `inventory-service` listens for this event to confirm a stock reservation, ensuring that the services remain loosely coupled. 

-----

## ✨ Key Features & Implementation Highlights

This project is not just a collection of services; it's a demonstration of modern software engineering practices.

* **Polyglot Persistence**: The strategic use of different databases (MySQL, Oracle, MongoDB) tailored to the specific needs of each microservice demonstrates a deep understanding of data modeling. `product-service` uses MySQL for its structured catalog data, while `order-service` leverages MongoDB's flexible document model for complex orders.
* **Event-Driven Choreography**: The order confirmation process is a prime example of an event-driven pattern. The `order-service` creates a reservation and places an order, then fires an `OrderPlacedEvent`. The `inventory-service` independently consumes this event to finalize the reservation. This avoids tight coupling and improves system resilience. 
* **Test-Driven Development (TDD)**: The project emphasizes a TDD approach for creating new features, as evidenced by the comprehensive unit and integration tests, particularly in the `product-service`. 
* **Clean Code & Design Patterns**:
    * **Builder Pattern**: Used for creating complex request objects like `BookRequest`.
    * **Repository Pattern**: Abstracted through Spring Data JPA and MongoDB repositories for clean data access.
    * **Separation of Concerns**: Clear separation between controllers, services, and repositories in each microservice.
* **Robust Configuration**: The use of Spring Cloud Config Server allows for managing configurations across all services in a centralized Git repository. This includes sensitive data (like database credentials) which can be managed per environment (dev, prod).
* **Code Quality & Maintainability**:
    * **Custom Validation Annotations**: The project includes custom annotations (e.g., `@BookCategory`, `@LanguageCode`) for robust and reusable input validation.
    * **MapStruct**: For generating high-performance, boilerplate-free mapping code between DTOs and entities.

-----

## 📊 Project Status

#### ✅ Completed

* **Core Infrastructure**: API Gateway, Config Server, and Discovery Server are set up and operational.
* **Service Implementation**: `product-service`, `inventory-service`, and `order-service` have been implemented with their core functionalities.
* **End-to-End Order Flow**: The system successfully handles a complete order creation flow:
    1.  A request to `order-service` triggers a stock reservation in `inventory-service`.
    2.  Product details are fetched from `product-service`.
    3.  An order document is created in MongoDB.
    4.  An `OrderPlacedEvent` is published to Kafka.
    5.  `inventory-service` consumes the event and confirms the reservation.
* **Inventory Management**: Implemented stock reservation, release, and cleanup mechanisms, including a scheduler for expiring reservations. 
* **API Documentation**: `product-service` utilizes Springdoc OpenAPI for automated Swagger documentation. 

-----

## 🚀 Future Roadmap

This project is an ongoing effort with a clear vision for expansion.

#### To Be Done (Immediate Next Steps)

* **Implement Spring Security & Keycloak**:
    * Secure the API Gateway and all downstream microservices.
    * Implement role-based access control (RBAC) for different user types (customer, employee).
    * Set up a Keycloak server for centralized Identity and Access Management (IAM).
* **Finalize Saga Pattern**: Implement compensating transactions. For example, if payment processing fails after an order is confirmed, a `PaymentFailedEvent` should trigger the release of the stock reservation and cancellation of the order.
* **Complete Fallbacks**: Fully implement Resilience4j circuit breakers and fallbacks for all Feign clients to gracefully handle service unavailability.

#### Planned Future Services

* **User Service**: A dedicated service for managing customer data (profiles, addresses). It will have its own database, separating customer information from employee data for security and scalability.
* **Employee Service**: A service for managing internal users (employees, administrators) with different roles and permissions.
* **Notification Service**: A service to handle sending emails or SMS messages to customers (e.g., order confirmation, shipping updates).
* **Payment Service**: Integration with a payment provider like Stripe or PayPal.

-----

## ⚙️ Setup & Running the Project

1.  **Prerequisites**:
    * Java 21
    * Docker & Docker Compose
    * Maven
    * Running instances of MySQL, Oracle, and MongoDB.
2.  **Configuration**:
    * Set up the configuration repository for the **Config Server**.
    * Provide necessary environment variables (e.g., database credentials, `GIT_USERNAME`/`GIT_TOKEN` for the Config Server).
3.  **Run Kafka**:
    * Navigate to the project root and run `docker-compose up -d` to start Kafka and Kafdrop.
4.  **Launch Order**:
    * Start the applications in the following order:
        1.  `config-server`
        2.  `discovery-server`
        3.  `product-service`, `inventory-service`, `order-service`
        4.  `api-gateway`
5.  **Access**:
    * **Eureka Dashboard**: `http://localhost:8761`
    * **API Gateway**: `http://localhost:8080`
    * **Kafdrop (Kafka UI)**: `http://localhost:9000`