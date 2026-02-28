# 🚗 Vehicle Rental System – Spring Boot Backend





A secure and scalable Vehicle Rental Management Backend built using Spring Boot, implementing JWT authentication, role-based access control, booking lifecycle automation, dashboard analytics, PDF receipt generation, and email integration.


## 📌 Overview

**This project demonstrates a real-world backend system with:**

- 🔐 JWT-based Authentication
- 👥 Role-Based Access Control (ADMIN / USER)
- 🚘 Vehicle Management
- 📅 Booking Lifecycle Management
- 📊 Revenue & Booking Dashboard Analytics
- 🧾 PDF Receipt Generation
- 📧 Email Notification Integration
- ⏱ Scheduled Booking Completion




**Designed using layered architecture principles and RESTful API standards.**

## 🏗 Architecture


> Controller Layer
>        ↓
> Service Interface
>        ↓
> Service Implementation
>        ↓
> Repository Layer (JPA)
>        ↓
> MySQL Database

<br>

> Security Layer:
Spring Security
JWT Filter
CustomUserDetailsService
Stateless Session Policy

## 🛠 Tech Stack
**Backend**
- Java 17+
- Spring Boot 3+
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Security
- JWT (JSON Web Token)
- BCrypt Password Encoder
- Role-Based Access Control

**Utilities**

- OpenHTMLtoPDF (PDF generation)
- JavaMailSender (Email)
- Lombok
- Jakarta Validation

## 🔐 Authentication & Authorization

- Stateless JWT authentication
- Role-based route protection
- BCrypt password hashing
- Roles
- ADMIN
- USER

## 📄 API Documentation
### 🔓 Authentication APIs
**Register User**

>POST /api/users/register
Request Body
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}

**Login**

>POST /api/users/login
Request Body
{
  "email": "john@example.com",
  "password": "password123"
}

**Response**

>{
  "token": "jwt_token_here",
  "role": "USER",
  "id": 1,
  "email": "john@example.com"
}

**🚘 Vehicle APIs**
**Get All Vehicles**
>GET /api/vehicles

**Filter by Availability**
>GET /api/vehicles/available/{true|false}

**Filter by Rent**
>GET /api/vehicles/rent/{maxRent}

**Filter by Location**
>GET /api/vehicles/location/{city}

**Add Vehicle (ADMIN)**
>POST /api/vehicles/admin/{adminId}
>Authorization: Bearer <JWT>

**📅 Booking APIs**

**Create Booking**
>POST /api/bookings/user/{userId}/vehicle/{vehicleId}
Authorization: Bearer <JWT>

**Cancel Booking**
>PUT /api/bookings/cancel/{id}

**Download Receipt**
>GET /api/bookings/receipt/{bookingId}

Returns generated PDF file.

**📊 Dashboard APIs (ADMIN)**
>GET /api/dashboard/stats
Authorization: Bearer <JWT>

**Returns:**

- Total users
- Total vehicles
- Booking statistics
- Revenue

## 🗄 Database Design
### Entities
- User
- Vehicle
- Booking
- Location
### Enums
> Role → ADMIN, USER
> BookingStatus → CONFIRMED, CANCELLED, COMPLETED

## ⚙ Configuration

**Update application.properties:**

> spring.datasource.url=jdbc:mysql://localhost:3306/vehiclerentalsystem
spring.datasource.username=root
spring.datasource.password={YOUR_PASSWORD}

> spring.jpa.hibernate.ddl-auto=update

> jwt.secret=your_secret_key

### ▶️ Running the Application
1️⃣ Clone the Repository

       > git clone https://github.com/your_username/Vehicle-Booking-Platform-Backend.git
       
2️⃣ Configure Database

### Create MySQL database:

> CREATE DATABASE vehiclerentalsystem;

3️⃣ Run Application
> mvn spring-boot:run

Server runs on:

> http://localhost:8080

🧪 Testing
🔍 Manual API Testing

You can test APIs using:
- Postman
- Thunder Client
- cURL





## 📈 Key Features

> ✔ Secure JWT Authentication
✔ Role-Based Access Control
✔ Booking Lifecycle Automation
✔ Revenue Analytics
✔ Scheduled Task Execution
✔ PDF Receipt Generation
✔ Email Integration
✔ Layered Architecture

## 🚀 Future Improvements

- Replace monetary double fields with BigDecimal
- Add optimistic locking to prevent double booking
- Implement refresh-token mechanism
- Add Swagger/OpenAPI documentation
- Add Docker support
- Add CI/CD pipeline
- Add cloud storage for receipts
- Add payment gateway integration



## 👨‍💻 Author
  MANISH WANI
