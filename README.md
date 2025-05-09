# HealthyBites - Backend REST API

## Description

**HealthyBites** is an application focused on managing healthy eating. This repository contains the backend REST API, which is developed using **Spring Boot**. The backend handles operations related to users, the MySQL database, and application authentication.

The links to the other repositories are as follows:

- [Frontend Web](https://github.com/DomeNieto/HealthyBites-app-web.git)
- [Mobile Frontend](https://github.com/DomeNieto/HealthyBites-app-movil.git)

Below, you will find the steps to install and run the backend server, as well as the Docker container required for the project.

---

## Prerequisites

Before you start, make sure you have the following tools installed:

- **Java 17** or higher
- **Maven** (for dependency management)
- **Spring Tool Suite 4 (STS4)** (as the recommended IDE)
- **Docker** and **docker-compose** (to create the MySQL database container)

### Docker Installation

1.  **Install Docker**:
    To install Docker and Docker Compose, refer to the following links:
    
    - [Download Docker](https://www.docker.com/get-started)
    - [Install Docker Compose](https://docs.docker.com/compose/install/)

3.  **Start Docker Compose**:
    Once you have Docker installed, you need to create a container for MySQL. To do this, navigate to the directory where the `docker-compose.yml` file is located and run the following command:

    ```bash
    docker compose up 
    ```

This will create and start a Docker container for the MySQL database. You can verify that it is running with the following command:

```bash
docker ps
```

This will allow the backend to connect to the MySQL database in the container.

---

## Backend Installation and Configuration

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/DomeNieto/HealthyBites-api.git
cd HealthyBites-api
```

---

### 2. Database Configuration

In your `src/main/resources/application.properties` file, configure the connection to the MySQL database. Ensure that the credentials and URL are correct:

```properties
spring.application.name=HealthyBites-api

spring.jpa.hibernate.ddl-auto=update

# Database connection 
spring.datasource.url=jdbc:mysql://localhost/HealthyBitesBBDD?serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.dbcp2.driver-class-name=com.mysql.cj.jdbc.Driver

# Server port
server.port=8081

# Show SQL queries in console
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Control non-existent route errors
spring.web.mvc.throw-exception-if-no-handler-found=true
spring.web.resources.add-mappings=false

# Initial SQL data loading
spring.sql.init.mode=never
spring.jpa.defer-datasource-initialization=true
spring.sql.init.data-locations=classpath:sql/data.sql

# Swagger / OpenAPI
springdoc.swagger-ui.path=/doc/swagger-ui.html

springdoc.api-docs.enabled=true
springdoc.swagger-ui.csrf.enabled=true

springdoc.packages-to-scan=com.healthybites

springdoc.swagger-ui.default-models-expand-depth=2
```
---

### 3. Running the Backend
To run the application in a development environment using **Spring Tool Suite 4**:

1.  Import the Maven project into STS4:
    -  Go to `File > Import...`.
    -   Select `Maven > Existing Maven Projects` and click `Next`.
    -   Browse to the root directory of your cloned `HealthyBites-api` project.
    -   Ensure the `pom.xml` is selected and click `Finish`.
2.  Once the project is imported and built , locate the main application class. You can find this in the `src/main/java` directory, under your main package.
3.  To run the application, right-click on the project folderin the *Package Explorer* .
4.  Select **"Run As > Spring Boot App"**.
