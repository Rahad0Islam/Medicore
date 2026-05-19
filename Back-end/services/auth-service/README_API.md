# Auth Service API (Local)

Base URL: http://localhost:8080

## Local config
- Database: set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` or use the defaults in application.properties.
- JWT: set `JWT_SECRET` to a 32+ character secret.

## Register
POST /api/auth/register

Request
{
  "email": "rahad@example.com",
  "password": "Password123",
  "name": "Md Rahad Islam",
  "role": "PATIENT"
}

Response 201
{
  "accessToken": "<jwt>",
  "refreshToken": "<jwt>",
  "tokenType": "Bearer",
  "expiresInMinutes": 30,
  "user": {
    "id": 1,
    "email": "rahad@example.com",
    "name": "Md Rahad Islam",
    "role": "PATIENT",
    "enabled": true
  }
}

## Login
POST /api/auth/login

Request
{
  "email": "rahad@example.com",
  "password": "Password123"
}

Response 200
{ ... same as register ... }

## Refresh
POST /api/auth/refresh

Request
{
  "refreshToken": "<jwt>"
}

Response 200
{ "accessToken": "<jwt>", "refreshToken": "<jwt>", "tokenType": "Bearer", "expiresInMinutes": 30, "user": { ... } }

## Me
GET /api/auth/me

Header
Authorization: Bearer <accessToken>

Response 200
{
  "id": 1,
  "email": "rahad@example.com",
  "name": "Md Rahad Islam",
  "role": "PATIENT",
  "enabled": true
}
