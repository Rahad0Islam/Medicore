# MediCore Postman API Guide

## Base URLs
- Auth service: `http://localhost:8001/api/auth`
- User service: `http://localhost:8002/api/users`
- Appointment service: `http://localhost:8003`

## Auth Service

### Register
- Method: `POST`
- URL: `http://localhost:8001/api/auth/register`
- Body:
```json
{
  "email": "patient1@medicore.com",
  "password": "Password@123",
  "name": "Rahad",
  "role": "PATIENT"
}
```
- Success: `201 Created`
```json
{
  "accessToken": "eyJ...",
  "refreshToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresInMinutes": 30,
  "user": {
    "id": 1,
    "email": "patient1@medicore.com",
    "name": "Rahad",
    "role": "PATIENT",
    "enabled": true
  }
}
```

### Login
- Method: `POST`
- URL: `http://localhost:8001/api/auth/login`
- Body:
```json
{
  "email": "patient1@medicore.com",
  "password": "Password@123"
}
```

### Refresh Token
- Method: `POST`
- URL: `http://localhost:8001/api/auth/refresh`
- Body:
```json
{
  "refreshToken": "eyJ..."
}
```

### Logout
- Method: `POST`
- URL: `http://localhost:8001/api/auth/logout`
- Body:
```json
{
  "refreshToken": "eyJ..."
}
```
- Success: `204 No Content`

### Me
- Method: `GET`
- URL: `http://localhost:8001/api/auth/me`
- Header: `Authorization: Bearer <accessToken>`

## User Service

### Create Patient Profile
- Method: `POST`
- URL: `http://localhost:8002/api/users/patients`
- Header: `Authorization: Bearer <accessToken>`
- Body:
```json
{
  "phone": "01700000000",
  "gender": "Male",
  "dateOfBirth": "1999-05-10",
  "address": "Dhaka, Bangladesh",
  "enabled": true
}
```

### Get Patient By ID
- Method: `GET`
- URL: `http://localhost:8002/api/users/patients/1`

### List Patients
- Method: `GET`
- URL: `http://localhost:8002/api/users/patients`

### Create Doctor Profile
- Method: `POST`
- URL: `http://localhost:8002/api/users/doctors`
- Header: `Authorization: Bearer <accessToken>`
- Body:
```json
{
  "phone": "01800000000",
  "specialization": "Cardiology",
  "department": "Heart Care",
  "licenseNumber": "BMDC-12345",
  "enabled": true
}
```

### Search Doctors
- Method: `GET`
- URL: `http://localhost:8002/api/users/doctors/search?query=cardio`

### Pharmacists
- Method: `GET`
- URL: `http://localhost:8002/api/users/pharmacists`

### Admins
- Method: `GET`
- URL: `http://localhost:8002/api/users/admins`

## Appointment Service

### Book Appointment
- Method: `POST`
- URL: `http://localhost:8003/appointments/book`
- Body:
```json
{
  "patientId": "1",
  "doctorId": "10",
  "appointmentDate": "2026-06-15",
  "timeSlot": "09:30-10:00",
  "reason": "Follow-up checkup"
}
```
- Success: `201 Created`
```json
{
  "id": 1,
  "patientId": "1",
  "doctorId": "10",
  "appointmentDate": "2026-06-15",
  "timeSlot": "09:30-10:00",
  "serialNumber": 1,
  "status": "BOOKED",
  "reason": "Follow-up checkup",
  "createdAt": "2026-06-10T...Z",
  "updatedAt": "2026-06-10T...Z"
}
```

### Get Appointment By ID
- Method: `GET`
- URL: `http://localhost:8003/appointments/1`

### Cancel Appointment
- Method: `PUT`
- URL: `http://localhost:8003/appointments/1/cancel`

### Get Queue
- Method: `GET`
- URL: `http://localhost:8003/appointments/queue?doctorId=10&date=2026-06-15&status=BOOKED`

### Doctor Daily Schedule
- Method: `GET`
- URL: `http://localhost:8003/doctor-schedules/10?date=2026-06-15&status=BOOKED`

## Suggested Header Setup
- `Content-Type: application/json`
- `Authorization: Bearer <accessToken>` for protected endpoints

## Typical Flow
1. Register a patient or login with an existing account.
2. Copy the `accessToken` from auth response.
3. Call user-service endpoints with the Bearer token.
4. Create an appointment through appointment-service.
5. Use queue and schedule endpoints to verify serial ordering.