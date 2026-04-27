# VSMS API Endpoints

Base URL: `http://localhost:8080`

The REST API is open for local development. The Thymeleaf web UI uses form login.

## Customers

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/customers` | List customers. Optional `search` query. |
| GET | `/api/customers/{id}` | Get one customer. |
| POST | `/api/customers` | Create customer. |
| PUT | `/api/customers/{id}` | Update customer. |
| DELETE | `/api/customers/{id}` | Delete customer. |

Sample create request:

```json
{
  "name": "Amit Sharma",
  "phone": "+91 9876543210",
  "email": "amit@example.com",
  "address": "Bengaluru"
}
```

## Vehicles

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/vehicles` | List vehicles. Optional `search` and `customerId` query params. |
| GET | `/api/vehicles/{id}` | Get one vehicle. |
| POST | `/api/vehicles` | Create vehicle. |
| PUT | `/api/vehicles/{id}` | Update vehicle. |
| DELETE | `/api/vehicles/{id}` | Delete vehicle. |

Sample create request:

```json
{
  "customerId": 1,
  "brand": "Toyota",
  "model": "Innova",
  "registrationNumber": "KA01AB1234"
}
```

## Service Bookings

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/bookings` | List bookings. Optional `vehicleId` query. |
| GET | `/api/bookings/{id}` | Get one booking. |
| POST | `/api/bookings` | Book a service. |
| PUT | `/api/bookings/{id}` | Update booking. |
| PATCH | `/api/bookings/{id}/status?status=CONFIRMED` | Change status. |
| POST | `/api/bookings/{id}/complete` | Mark completed and create service history. |
| DELETE | `/api/bookings/{id}` | Delete booking. |

Allowed statuses: `PENDING`, `CONFIRMED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.

Allowed service types: `GENERAL_SERVICE`, `OIL_CHANGE`, `BRAKE_SERVICE`, `ENGINE_REPAIR`, `TIRE_REPLACEMENT`, `BATTERY_CHECK`, `INSPECTION`, `OTHER`.

Sample booking request:

```json
{
  "vehicleId": 1,
  "serviceDate": "2026-05-05",
  "serviceType": "GENERAL_SERVICE",
  "status": "PENDING"
}
```

Sample complete request:

```json
{
  "description": "General service, oil filter replacement, brake inspection",
  "cost": 4500.00
}
```

## Service History

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/history` | List history. Optional `vehicleId` query. |
| GET | `/api/history/{id}` | Get one history record. |
| POST | `/api/history` | Add a completed service record manually. |
| PUT | `/api/history/{id}` | Update history. |
| DELETE | `/api/history/{id}` | Delete history. |

Sample history request:

```json
{
  "vehicleId": 1,
  "description": "Battery replacement",
  "serviceDate": "2026-04-22",
  "cost": 6200.00
}
```

## Dashboard, Reminders, Invoices

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/dashboard` | Summary counts and total revenue. |
| GET | `/api/reminders?days=30` | Vehicles predicted to need service soon. |
| GET | `/api/invoices/history/{historyId}/pdf` | Download invoice PDF for a service history record. |
