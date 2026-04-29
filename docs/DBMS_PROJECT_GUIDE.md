# VSMS as a DBMS Project

## How to Present It

If you want the same VSMS project to look mostly like a DBMS project, present it in this order:

1. Problem statement
2. Entities and relationships
3. Relational schema
4. Constraints and normalization
5. SQL implementation
6. Queries, views, procedures, and triggers
7. Frontend/backend as supporting tools

The Java Spring Boot application becomes the interface layer, while the database design becomes the main academic focus.

## Project Title Suggestion

**Database-Centric Vehicle Service Management System**

Alternative:

**Design and Implementation of a Vehicle Service Management Database System**

## Core DBMS Learning Points in VSMS

- Entity-relationship modeling
- Primary key and foreign key constraints
- One-to-many relationships
- Normalization up to 3NF
- Indexing for query performance
- Views for reporting
- Stored procedures for reusable business transactions
- Triggers for data consistency
- Aggregate queries for analytics

## Main Tables

| Table | Purpose |
| --- | --- |
| `customers` | Stores customer details |
| `vehicles` | Stores vehicle details linked to a customer |
| `service_bookings` | Stores scheduled service appointments |
| `service_history` | Stores completed service records and costs |

## Recommended DBMS-Focused Features to Demonstrate

### 1. Relationship Design

- One customer can own many vehicles
- One vehicle can have many service bookings
- One vehicle can have many service history entries

### 2. Constraints

- `PRIMARY KEY` on every table
- `FOREIGN KEY` relationships
- `UNIQUE` on `registration_number`
- `NOT NULL` fields for critical attributes

### 3. SQL Objects

Use these files:

- `database/schema.sql`
- `database/advanced_dbms_objects.sql`
- `database/sample_data.sql`

### 4. Analytical Queries

Examples:

- Total revenue by month
- Vehicles serviced most frequently
- Customers with multiple vehicles
- Pending bookings by status
- Average service cost per vehicle

## Suggested Viva / Presentation Angle

Instead of saying:

> "This is a Spring Boot project."

Say:

> "This is a DBMS-based Vehicle Service Management System implemented with MySQL as the primary focus, and a Java Spring Boot interface built on top of that database."

## Suggested Chapter Names for a DBMS Report

1. Introduction
2. Problem Definition
3. ER Model
4. Relational Schema Design
5. Normalization
6. SQL Implementation
7. Queries, Views, Procedures, and Triggers
8. Result Analysis
9. Conclusion

## Suggested Demo Flow

1. Show ER diagram
2. Explain tables and keys
3. Show schema SQL
4. Insert sample records
5. Run views and summary queries
6. Run stored procedure for booking completion
7. Show frontend only at the end as an interface to the database
