# Language Translation Chatbot for Rural Banking

A full-stack educational project using Java 17, Spring Boot, React, MySQL, JPA/Hibernate and REST APIs.

## Features

- English, Telugu and Hindi language selection
- Banking questions for account balance, loans, ATM and digital payments
- REST API between React and Spring Boot
- MySQL persistence using JPA/Hibernate
- Conversation history
- Required-field validation
- Meaningful HTTP 400/201/500 responses
- Loading, success, validation and error states
- Responsive UI
- Clear history
- Git/GitHub ready

## Requirements

Install:

- Java 17+
- Maven 3.9+
- Node.js 20+
- MySQL 8+

## 1. Create MySQL database

Open MySQL Workbench or MySQL command line:

```sql
CREATE DATABASE rural_banking;
```

No table creation is needed. Hibernate creates the table automatically.

## 2. Configure backend

Open:

`backend/src/main/resources/application.properties`

Change:

```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

to your MySQL password.

If your MySQL username is not `root`, also change:

```properties
spring.datasource.username=root
```

## 3. Start Spring Boot

Open a terminal:

```bash
cd backend
mvn spring-boot:run
```

Backend:

`http://localhost:8080`

Test:

`http://localhost:8080/api/chat/history`

## 4. Start React

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

Open the URL shown by Vite, normally:

`http://localhost:5173`

## API endpoints

### Send chat

```http
POST /api/chat
Content-Type: application/json
```

Request:

```json
{
  "language": "Telugu",
  "message": "How can I check my account balance?"
}
```

Response:

```json
{
  "id": 1,
  "language": "Telugu",
  "userMessage": "How can I check my account balance?",
  "botResponse": "మీ ఖాతా బ్యాలెన్స్‌ను ATM, మొబైల్ బ్యాంకింగ్ లేదా బ్యాంక్ బ్రాంచ్ ద్వారా చెక్ చేసుకోవచ్చు.",
  "createdAt": "2026-10-05T10:30:00"
}
```

### Conversation history

```http
GET /api/chat/history
```

### Clear history

```http
DELETE /api/chat/history
```

## Suggested Git workflow

```bash
git init
git add .
git commit -m "Initial rural banking chatbot project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/language-translation-chatbot.git
git push -u origin main
```

## Project architecture

```text
React UI
   |
   | HTTP/JSON
   v
Spring Boot Controller
   |
   v
Chat Service
   |
   v
JPA Repository
   |
   v
MySQL
```

## Important note

This is an educational chatbot. The built-in translation/answer logic is intentionally simple and rule-based. For a production banking chatbot, use an approved translation/NLP service, authentication, authorization, audit logging, encryption and bank-approved content. Never enter real PINs, passwords, OTPs or other confidential banking information into this demo.
