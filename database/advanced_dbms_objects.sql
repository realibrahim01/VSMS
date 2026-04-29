USE vsms_db;

DROP VIEW IF EXISTS vw_vehicle_service_summary;
DROP VIEW IF EXISTS vw_monthly_revenue_summary;

CREATE VIEW vw_vehicle_service_summary AS
SELECT
    v.id AS vehicle_id,
    v.registration_number,
    v.brand,
    v.model,
    c.id AS customer_id,
    c.name AS customer_name,
    c.phone AS customer_phone,
    COUNT(DISTINCT sb.id) AS total_bookings,
    COUNT(DISTINCT sh.id) AS total_completed_services,
    COALESCE(SUM(sh.cost), 0.00) AS total_service_cost,
    MAX(sh.service_date) AS last_service_date
FROM vehicles v
JOIN customers c ON c.id = v.customer_id
LEFT JOIN service_bookings sb ON sb.vehicle_id = v.id
LEFT JOIN service_history sh ON sh.vehicle_id = v.id
GROUP BY v.id, v.registration_number, v.brand, v.model, c.id, c.name, c.phone;

CREATE VIEW vw_monthly_revenue_summary AS
SELECT
    YEAR(service_date) AS service_year,
    MONTH(service_date) AS service_month,
    COUNT(*) AS completed_services,
    SUM(cost) AS total_revenue,
    AVG(cost) AS average_service_cost
FROM service_history
GROUP BY YEAR(service_date), MONTH(service_date)
ORDER BY service_year DESC, service_month DESC;

DROP PROCEDURE IF EXISTS sp_add_customer_with_vehicle;
DELIMITER $$
CREATE PROCEDURE sp_add_customer_with_vehicle(
    IN p_customer_name VARCHAR(120),
    IN p_phone VARCHAR(20),
    IN p_email VARCHAR(150),
    IN p_address VARCHAR(500),
    IN p_brand VARCHAR(80),
    IN p_model VARCHAR(80),
    IN p_registration_number VARCHAR(30)
)
BEGIN
    DECLARE new_customer_id BIGINT;

    START TRANSACTION;

    INSERT INTO customers(name, phone, email, address)
    VALUES (p_customer_name, p_phone, p_email, p_address);

    SET new_customer_id = LAST_INSERT_ID();

    INSERT INTO vehicles(customer_id, brand, model, registration_number)
    VALUES (new_customer_id, p_brand, p_model, UPPER(TRIM(p_registration_number)));

    COMMIT;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_complete_booking;
DELIMITER $$
CREATE PROCEDURE sp_complete_booking(
    IN p_booking_id BIGINT,
    IN p_description VARCHAR(1000),
    IN p_cost DECIMAL(10, 2)
)
BEGIN
    DECLARE booking_vehicle_id BIGINT;
    DECLARE booking_service_date DATE;
    DECLARE booking_status VARCHAR(30);

    SELECT vehicle_id, service_date, status
    INTO booking_vehicle_id, booking_service_date, booking_status
    FROM service_bookings
    WHERE id = p_booking_id;

    IF booking_vehicle_id IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Booking not found';
    END IF;

    IF booking_status = 'CANCELLED' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cancelled booking cannot be completed';
    END IF;

    START TRANSACTION;

    UPDATE service_bookings
    SET status = 'COMPLETED'
    WHERE id = p_booking_id;

    INSERT INTO service_history(vehicle_id, description, service_date, cost)
    VALUES (booking_vehicle_id, p_description, booking_service_date, p_cost);

    COMMIT;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS trg_normalize_vehicle_registration;
DELIMITER $$
CREATE TRIGGER trg_normalize_vehicle_registration
BEFORE INSERT ON vehicles
FOR EACH ROW
BEGIN
    SET NEW.registration_number = UPPER(TRIM(NEW.registration_number));
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS trg_vehicle_registration_before_update;
DELIMITER $$
CREATE TRIGGER trg_vehicle_registration_before_update
BEFORE UPDATE ON vehicles
FOR EACH ROW
BEGIN
    SET NEW.registration_number = UPPER(TRIM(NEW.registration_number));
END $$
DELIMITER ;
