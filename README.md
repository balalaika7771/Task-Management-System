# Task Management System - Deployment Guide

This guide explains how to deploy and run the Task Management System using Docker Compose.

## Prerequisites

Make sure the following tools are installed on your system:

1. **Docker**: Install Docker from [docker.com](https://www.docker.com/).
2. **Docker Compose**: Most Docker installations include Docker Compose. Verify it by running:
   ```bash
   docker-compose --version
   ```

---

## Project Setup

### 1. Clone the Repository

Clone the project repository to your local machine:

```bash
git clone <repository-url>
cd <project-directory>
```

### 2. Build the Project

Ensure the application is packaged as a JAR file (e.g., `application.jar`) in the project directory. If not, build it
using Maven or Gradle:

- For **Maven**:
  ```bash
  mvn clean package
  ```

- For **Gradle**:
  ```bash
  gradle build
  ```

Move the generated JAR file to the root of the project directory if it isn't already there.

---

## How to Run

### 1. Start the Services

Run the following command to start the application and the PostgreSQL database:

```bash
docker-compose up --build
```

- The `--build` flag ensures Docker rebuilds the app image if there are changes in the source code.

### 2. Access the Application

Once the services are running:

- **Application**:
  Open your browser and navigate to [http://localhost:8080](http://localhost:8080).
- **Database**:
  Connect to PostgreSQL using a database client (e.g., DBeaver, pgAdmin) or a command-line tool:
    - Host: `localhost`
    - Port: `5432`
    - Database: `Task_Management`
    - Username: `admin`
    - Password: `admin`

---

## Application Endpoints

- **Swagger UI**: Access the API documentation
  at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
- **Health Check**: Ensure the application is running by
  visiting [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health).

---

## Stopping the Services

To stop and remove the containers:

```bash
docker-compose down
```

---

## Troubleshooting

1. **Container Issues**:
    - Ensure Docker and Docker Compose are installed correctly.
    - Verify the services are running by checking container logs:
      ```bash
      docker-compose logs
      ```

2. **Database Connection Errors**:
    - Ensure the database credentials in the `docker-compose.yml` file match the application settings.

3. **Port Conflicts**:
    - If ports `8080` or `5432` are already in use, change them in the `docker-compose.yml` file.

---

## Customization

### Change Application Settings

Modify the environment variables in the `docker-compose.yml` file:

- `SPRING_DATASOURCE_URL`: Update the JDBC connection string if using an external database.
- `SPRING_DATASOURCE_USERNAME`: Change the database username.
- `SPRING_DATASOURCE_PASSWORD`: Change the database password.

### Extend the Configuration

Add additional services or volumes to the `docker-compose.yml` file as needed. For example, to add a monitoring tool or
a message broker.

---

You're all set! Run the application and enjoy using the Task Management System.