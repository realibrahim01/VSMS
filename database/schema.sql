CREATE DATABASE IF NOT EXISTS vsms_db;
USE vsms_db;

CREATE TABLE customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(150),
    address VARCHAR(500)
);

CREATE TABLE vehicles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    brand VARCHAR(80) NOT NULL,
    model VARCHAR(80) NOT NULL,
    registration_number VARCHAR(30) NOT NULL,
    CONSTRAINT uk_vehicle_registration UNIQUE (registration_number),
    CONSTRAINT fk_vehicle_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE CASCADE
);

CREATE TABLE service_bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    service_date DATE NOT NULL,
    service_type VARCHAR(40) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    CONSTRAINT fk_booking_vehicle
        FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
        ON DELETE CASCADE
);

CREATE TABLE service_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    description VARCHAR(1000) NOT NULL,
    service_date DATE NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_history_vehicle
        FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_vehicle_customer ON vehicles(customer_id);
CREATE INDEX idx_booking_vehicle ON service_bookings(vehicle_id);
CREATE INDEX idx_booking_status_date ON service_bookings(status, service_date);
CREATE INDEX idx_history_vehicle_date ON service_history(vehicle_id, service_date);
