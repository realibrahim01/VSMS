# DBMS Report Content for VSMS

## Project Title

**Database-Centric Vehicle Service Management System**

## Abstract

The Vehicle Service Management System (VSMS) is a database-centric application designed to manage customer records, vehicle details, service bookings, and completed service history in an organized and reliable manner. The project focuses on relational database design using MySQL, where entities such as customers, vehicles, service bookings, and service history are mapped into normalized tables with proper primary key and foreign key constraints. The system demonstrates important DBMS concepts such as entity-relationship modeling, normalization, referential integrity, indexing, views, stored procedures, triggers, and analytical SQL queries. In addition to the database implementation, a Java Spring Boot interface is provided to interact with the database using a web application. The frontend and backend are supportive components, while the core contribution of the project lies in the design and implementation of an efficient and maintainable service management database. The system reduces manual paperwork, improves data consistency, enables service tracking, and generates useful reports such as revenue summaries and vehicle service status. Thus, the project is suitable for demonstrating both practical DBMS concepts and their application in a real-world domain.

## 1. Introduction

Vehicle service centers handle a large amount of structured information, including customer details, vehicle ownership records, service schedules, maintenance history, and billing data. Managing this information manually often leads to redundancy, inconsistent records, and difficulty in generating reports. A Database Management System can solve these issues by storing information in a structured relational form with clearly defined constraints and query support.

The Vehicle Service Management System is therefore developed as a DBMS-focused project. It organizes service center operations around a relational schema and uses SQL-based features to ensure data consistency, reduce duplication, and support reporting.

## 2. Objectives

- To design a relational database for vehicle service management.
- To define clear relationships between customers, vehicles, service bookings, and service history.
- To apply normalization techniques to reduce redundancy.
- To enforce data integrity using constraints and keys.
- To create SQL views, stored procedures, and triggers for practical DBMS functionality.
- To support reporting and analytics using SQL queries.

## 3. Problem Statement

Manual vehicle service management systems are slow, error-prone, and difficult to scale. Records are often scattered across notebooks, spreadsheets, or separate systems. As a result, tracking past services, checking booking status, identifying repeat customers, or calculating revenue becomes difficult. A well-designed DBMS is needed to centralize the data and make operations efficient.

## 4. ER Model

The project contains four major entities:

- Customer
- Vehicle
- Service Booking
- Service History

Relationships:

- One customer can own many vehicles.
- One vehicle can have many service bookings.
- One vehicle can have many service history records.

## 5. Relational Schema

### customers

- `id` (PK)
- `name`
- `phone`
- `email`
- `address`

### vehicles

- `id` (PK)
- `customer_id` (FK)
- `brand`
- `model`
- `registration_number` (UNIQUE)

### service_bookings

- `id` (PK)
- `vehicle_id` (FK)
- `service_date`
- `service_type`
- `status`

### service_history

- `id` (PK)
- `vehicle_id` (FK)
- `description`
- `service_date`
- `cost`

## 6. Normalization

The schema is normalized up to 3NF:

- 1NF: all attributes are atomic
- 2NF: all non-key attributes depend fully on the primary key
- 3NF: no transitive dependency is stored unnecessarily

Customer information is separated from vehicle information, and service bookings are separated from completed history to avoid redundancy and update anomalies.

## 7. Constraints and Integrity Rules

- Primary keys uniquely identify rows.
- Foreign keys preserve parent-child relationships.
- `registration_number` is unique for every vehicle.
- Important fields use `NOT NULL`.
- Triggers normalize registration number formatting.
- Procedures enforce booking completion rules.

## 8. SQL Features Implemented

### Views

- `vw_vehicle_service_summary`
- `vw_monthly_revenue_summary`

### Stored Procedures

- `sp_add_customer_with_vehicle`
- `sp_complete_booking`

### Triggers

- `trg_normalize_vehicle_registration`
- `trg_vehicle_registration_before_update`

## 9. Sample Queries

- Display all customers and their vehicles
- Find pending bookings
- Show revenue per vehicle
- Show monthly revenue summary
- Show vehicles serviced most frequently

## 10. Implementation Support Layer

Although the project is DBMS-focused, a Java Spring Boot application is built over the database to provide a user interface and REST APIs. This layer helps demonstrate how the database can be used in a practical application.

## 11. Result

The resulting system successfully stores and manages all core vehicle service data in a normalized relational database. It supports insertion, update, deletion, search, summaries, and reports. The database design is stable, maintainable, and suitable for a real-world service center scenario.

## 12. Conclusion

The Vehicle Service Management System demonstrates the effective use of DBMS concepts in solving a practical business problem. By focusing on relational schema design, normalization, constraints, SQL programming, and reporting, the project highlights the central role of a database in service management software.

## 13. Future Scope

- add inventory and spare parts tables
- add payment and billing tables
- add mechanic assignment tables
- add audit logging tables
- add email/SMS reminder integration
- expand analytical reporting and predictive maintenance logic
