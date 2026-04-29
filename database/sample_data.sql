USE vsms_db;

INSERT INTO customers (name, phone, email, address) VALUES
('Amit Sharma', '+91 9876543210', 'amit.sharma@example.com', 'Pune'),
('Neha Patil', '+91 9988776655', 'neha.patil@example.com', 'Mumbai'),
('Rahul Deshmukh', '+91 9123456780', 'rahul.deshmukh@example.com', 'Nashik');

INSERT INTO vehicles (customer_id, brand, model, registration_number) VALUES
(1, 'Toyota', 'Innova', 'MH12AB1234'),
(1, 'Honda', 'City', 'MH14CD5678'),
(2, 'Hyundai', 'Creta', 'MH01EF9012'),
(3, 'Maruti Suzuki', 'Baleno', 'MH15GH3456');

INSERT INTO service_bookings (vehicle_id, service_date, service_type, status) VALUES
(1, '2026-05-10', 'GENERAL_SERVICE', 'PENDING'),
(2, '2026-05-12', 'OIL_CHANGE', 'CONFIRMED'),
(3, '2026-05-15', 'BRAKE_SERVICE', 'IN_PROGRESS'),
(4, '2026-05-18', 'INSPECTION', 'PENDING');

INSERT INTO service_history (vehicle_id, description, service_date, cost) VALUES
(1, 'General service and oil filter replacement', '2026-01-10', 4500.00),
(1, 'Brake pad inspection and cleaning', '2025-07-11', 2100.00),
(2, 'Engine oil change and wheel alignment', '2026-02-05', 3200.00),
(3, 'Brake service with disc inspection', '2026-03-18', 5200.00),
(4, 'Battery health check and inspection', '2025-11-22', 1800.00);
