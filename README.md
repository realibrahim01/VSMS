# Vehicle Service Management System (VSMS)

VSMS is a complete Java Spring Boot project for managing vehicle service customers, vehicles, service bookings, service history, reminders, and PDF invoices. It includes REST APIs and a Thymeleaf web UI.

## Tech Stack

- Java 17
- Spring Boot 3.3
- Spring Data JPA and Hibernate
- MySQL
- Thymeleaf
- Spring Security form login
- Lombok
- OpenPDF for invoice generation

## Step-by-Step Backend Setup

1. Install Java 17 or newer.
2. Install Maven 3.9 or newer.
3. Install and start MySQL.
4. Create the database manually if you prefer SQL-first setup:

```sql
SOURCE database/schema.sql;
```

You can also let Hibernate create/update tables automatically because `spring.jpa.hibernate.ddl-auto=update` is enabled.

5. Update database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vsms_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
```

6. Run the backend:

```bash
mvn spring-boot:run
```

7. Open the web UI:

```text
http://localhost:8080
```

Default users:

| Username | Password | Role |
| --- | --- | --- |
| `admin` | `admin123` | ADMIN |
| `user` | `user123` | USER |

## Frontend Pages

| Page | URL |
| --- | --- |
| Dashboard | `/` |
| Customers | `/customers` |
| Vehicles | `/vehicles` |
| Bookings | `/bookings` |
| Service History | `/history` |

## Project Structure

```text
src/main/java/com/example/vsms
  config/       Spring Security configuration
  controller/   REST and web controllers
  dto/          Request and response objects
  entity/       JPA entities and enums
  exception/    Global REST exception handling
  repository/   Spring Data JPA repositories
  service/      Business logic
```

## Main Features

- Customer CRUD
- Vehicle CRUD with customer relationship
- Service booking lifecycle
- Completed service history
- Dashboard counts and revenue
- Search/filter for customers, vehicles, and history
- Basic service reminders
- Next service date prediction from service history
- PDF invoice generation
- Web UI with form login
- REST API documentation and Postman collection

## REST API

See `docs/API_ENDPOINTS.md`.

Import `postman/vsms.postman_collection.json` into Postman for ready-made API requests.

## Running Tests

```bash
mvn test
```

The test profile uses an in-memory H2 database configured in `src/test/resources/application-test.properties`.

## Notes for Production Hardening

- Replace in-memory users with database-backed users.
- Move credentials to environment variables or a secret manager.
- Add JWT if the REST API will be consumed by external clients.
- Set `spring.jpa.hibernate.ddl-auto=validate` and use Flyway or Liquibase migrations.
- Restrict `/api/**` with authentication before exposing the app outside local development.
