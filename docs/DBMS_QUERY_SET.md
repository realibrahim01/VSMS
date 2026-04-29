# VSMS DBMS Query Set

Use these queries during demo or viva.

## 1. Show all customers and their vehicles

```sql
SELECT c.name AS customer_name, v.brand, v.model, v.registration_number
FROM customers c
JOIN vehicles v ON v.customer_id = c.id
ORDER BY c.name, v.registration_number;
```

## 2. Show pending and confirmed bookings

```sql
SELECT sb.id, v.registration_number, sb.service_date, sb.service_type, sb.status
FROM service_bookings sb
JOIN vehicles v ON v.id = sb.vehicle_id
WHERE sb.status IN ('PENDING', 'CONFIRMED')
ORDER BY sb.service_date;
```

## 3. Revenue per vehicle

```sql
SELECT v.registration_number, SUM(sh.cost) AS total_revenue
FROM vehicles v
JOIN service_history sh ON sh.vehicle_id = v.id
GROUP BY v.id, v.registration_number
ORDER BY total_revenue DESC;
```

## 4. Most frequently serviced vehicles

```sql
SELECT v.registration_number, COUNT(sh.id) AS service_count
FROM vehicles v
LEFT JOIN service_history sh ON sh.vehicle_id = v.id
GROUP BY v.id, v.registration_number
ORDER BY service_count DESC, v.registration_number;
```

## 5. Monthly revenue summary view

```sql
SELECT * FROM vw_monthly_revenue_summary;
```

## 6. Vehicle service summary view

```sql
SELECT * FROM vw_vehicle_service_summary;
```

## 7. Add customer and vehicle using stored procedure

```sql
CALL sp_add_customer_with_vehicle(
    'Sana Khan',
    '+91 9000011111',
    'sana.khan@example.com',
    'Aurangabad',
    'Kia',
    'Seltos',
    'MH20JK7890'
);
```

## 8. Complete a booking using stored procedure

```sql
CALL sp_complete_booking(
    1,
    'General service completed with oil and filter replacement',
    4800.00
);
```
