# Vehicle Rental System Backend

Spring Boot backend for a vehicle rental platform with JWT authentication, role-based access control, booking lifecycle management, dashboard statistics, PDF receipt generation, and email delivery.

## Features
- JWT-based login and authorization
- Role support: `ADMIN`, `USER`
- Vehicle catalog and filters (availability, rent, location)
- Admin vehicle management (add/list/delete)
- Booking creation, cancellation, and receipt download
- Automatic booking completion scheduler
- Dashboard analytics for admin users
- Receipt PDF generation (`openhtmltopdf`) and email attachment delivery

## Tech Stack
- Java 17
- Spring Boot 3.5.x
- Spring Security + JWT (`jjwt`)
- Spring Data JPA (Hibernate)
- MySQL 8
- Maven
- OpenHTMLtoPDF
- Spring Mail (SMTP)

## Project Structure
```text
src/main/java/com/vrs
|- controller
|- service / serviceImpl
|- repository
|- model
|- security
|- util
src/main/resources
|- application.properties
|- templates/booking-receipt.html
receipts/
```

## Prerequisites
- JDK 17+
- Maven 3.9+ (or use `./mvnw`)
- MySQL running locally

## Setup
1. Clone the repository.
2. Create database:
   ```sql
   CREATE DATABASE vehiclerentalsystem;
   ```
3. Update `src/main/resources/application.properties` with your local values.
4. Run:
   ```bash
   ./mvnw spring-boot:run
   ```
5. API base URL:
   ```text
   http://localhost:8080
   ```

## Configuration
Use this as a safe template for `application.properties`:

```properties
spring.application.name=vehicleRentalSystem
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/vehiclerentalsystem
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

jwt.secret=YOUR_LONG_RANDOM_SECRET
```

Important: rotate secrets if real passwords or JWT secrets were ever committed.

## Security and Access Rules
- Public:
  - `POST /api/users/register`
  - `POST /api/users/register-admin`
  - `POST /api/users/login`
  - `GET /api/vehicles/**`
- USER or ADMIN:
  - `/api/bookings/**`
- ADMIN only:
  - `/api/vehicles/admin/**`
  - `/api/dashboard/**`

Send JWT in header:
```text
Authorization: Bearer <token>
```

## API Endpoints

### Auth
- `POST /api/users/register`
- `POST /api/users/register-admin`
- `POST /api/users/login`
- `GET /api/users/{id}`

Register request:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

Login request:
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

Login response:
```json
{
  "token": "eyJhbGciOi...",
  "role": "USER"
}
```

### Vehicles
- `GET /api/vehicles`
- `GET /api/vehicles/id/{id}`
- `GET /api/vehicles/available/{status}`
- `GET /api/vehicles/rent/{maxRent}`
- `GET /api/vehicles/location/{city}`
- `POST /api/vehicles/admin/{adminId}` (ADMIN)
- `GET /api/vehicles/admin/{adminId}` (ADMIN)
- `DELETE /api/vehicles/{id}` (ADMIN)

Add vehicle request:
```json
{
  "brand": "Toyota",
  "model": "Innova",
  "rentPerDay": 2500,
  "location": {
    "id": 1
  }
}
```

### Bookings
- `POST /api/bookings/user/{userId}/vehicle/{vehicleId}`
- `GET /api/bookings`
- `GET /api/bookings/{id}`
- `PUT /api/bookings/cancel/{id}`
- `GET /api/bookings/receipt/{bookingId}`

Create booking request:
```json
{
  "startDate": "2026-03-01",
  "endDate": "2026-03-04"
}
```

### Dashboard (ADMIN)
- `GET /api/dashboard/stats`

Sample response:
```json
{
  "totalUsers": 25,
  "totalVehicles": 40,
  "availableVehicles": 12,
  "totalBookings": 100,
  "activeBookings": 8,
  "completedBookings": 80,
  "cancelledBookings": 12,
  "totalRevenue": 350000.0
}
```

## Booking Lifecycle
- On create: vehicle becomes unavailable and booking is `CONFIRMED`.
- On cancel: booking becomes `CANCELLED` and vehicle becomes available.
- Daily scheduler (`Asia/Kolkata`, midnight): past bookings are marked `COMPLETED` and vehicles are released.

## Build and Test
```bash
./mvnw clean test
./mvnw clean package
```

## Notes
- Receipts are generated under `receipts/`.
- `Location` is required when adding a vehicle (`location.id` must exist in DB).
- Recommended improvement for money fields: use `BigDecimal` instead of `double`.


## Author 
- Manish Wani