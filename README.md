# 📧 Spring Boot Email Service

**A production-ready, reusable email microservice with Kafka integration, OAuth2 security, and template support.**

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 🚀 What is this?

This is a **production-ready, reusable microservice** built with **Spring Boot** that handles email delivery in
distributed systems. It supports both **REST API** and **Apache Kafka** as messaging options.

It acts as an **email gateway** for your ecosystem:

- ✅ **REST API** for direct email sending
- ✅ **Kafka Consumer** for async email processing from message queues
- ✅ **Template Support** with Thymeleaf for beautiful HTML emails
- ✅ **OAuth2/JWT Security** with AWS Cognito integration
- ✅ **Bulk Email** support with failure tracking
- ✅ **Retry Mechanism** for failed emails
- ✅ **Scheduling** for deferred email delivery

---

## ✨ Features

### 📬 Email Capabilities

- **Template-based emails** - Beautiful HTML emails using Thymeleaf templates
- **Plain text emails** - Simple text-based email sending
- **OTP emails** - Auto-generated 6-digit OTP with Kafka publishing
- **Bulk sending** - Send to multiple recipients with failure tracking
- **Contact form emails** - Pre-built template for contact form submissions
- **Inline attachments** - Support for logos and images in emails

### 🔐 Security

- **OAuth2 Resource Server** - JWT token validation
- **AWS Cognito Integration** - Client credentials flow support
- **Token validation endpoint** - Verify tokens programmatically
- **Configurable security** - Enable/disable via properties

### 📨 Messaging

- **Kafka Integration** - Consume email requests from Kafka topics
- **Event Publishing** - Publish sent email metadata to downstream services
- **REST API** - Direct HTTP endpoints for email operations

### 🛠️ Operations

- **Retry mechanism** - Automatic retry for failed emails (configurable max attempts)
- **Job scheduling** - Cron-based email processing
- **H2 Console** - Built-in database viewer for development
- **Global error handling** - Consistent error responses

---

## 🏗️ Architecture

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   REST Client   │────▶│  Email Service  │────▶│   SMTP Server   │
└─────────────────┘     └─────────────────┘     └─────────────────┘
                               │
┌─────────────────┐           │           ┌─────────────────┐
│  Kafka Producer │──────────▶│◀──────────│   H2 Database   │
└─────────────────┘           │           └─────────────────┘
                               │
                        ┌──────▼──────┐
                        │Kafka Consumer│
                        └─────────────┘
```

---

## 📦 Tech Stack

| Technology            | Purpose                   |
|-----------------------|---------------------------|
| **Spring Boot 3.3.5** | Application framework     |
| **Spring Security**   | OAuth2/JWT authentication |
| **Spring Kafka**      | Message queue integration |
| **Spring Mail**       | Email sending via SMTP    |
| **Thymeleaf**         | HTML email templates      |
| **OpenFeign**         | HTTP client for Cognito   |
| **H2 Database**       | Development database      |
| **Lombok**            | Boilerplate reduction     |
| **MapStruct**         | Object mapping            |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (optional, for Kafka)
- Gmail App Password or SMTP credentials

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/email-service.git
cd email-service
```

### 2️⃣ Configure Environment Variables

Create a `.env` file or set environment variables:

```env
# SMTP Configuration (Gmail example)
JAVA_MAIL_SENDER_EMAIL=your.email@gmail.com
JAVA_MAIL_SENDER_APP_PASSWORD=your_app_password

# Admin Recipients (for contact form emails)
APP_EMAIL_ADMIN_1=admin1@example.com
APP_EMAIL_ADMIN_2=admin2@example.com

# Security (set to false to disable authentication)
APP_SECURITY_ENABLED=false

# Kafka (optional)
KAFKA_ENABLED=false
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# AWS Cognito (optional, for OAuth2)
COGNITO_CLIENT_ID=your_client_id
COGNITO_CLIENT_SECRET=your_client_secret
```

### 3️⃣ Run the Application

**Using Maven:**

```bash
./mvnw spring-boot:run
```

**Using Docker:**

```bash
docker-compose -f docker-compose.local.yaml up --build
```

### 4️⃣ Access the Service

- **API Base URL:** `http://localhost:8080`
- **H2 Console:** `http://localhost:8080/h2-console`

---

## 📡 API Endpoints

### Email Endpoints (Authenticated when security enabled)

| Method | Endpoint                             | Description               |
|--------|--------------------------------------|---------------------------|
| `POST` | `/api/v1/mail/template/contact-form` | Send contact form email   |
| `POST` | `/api/v1/mail/template/send`         | Send template-based email |
| `POST` | `/api/v1/mail/template/bulk`         | Bulk send emails          |
| `POST` | `/api/v1/mail/otp`                   | Send OTP email            |

### Auth Endpoints (Public)

| Method | Endpoint                | Description      |
|--------|-------------------------|------------------|
| `POST` | `/api/v1/auth/token`    | Get OAuth2 token |
| `POST` | `/api/v1/auth/validate` | Validate token   |

### Template Endpoints (Public)

| Method | Endpoint            | Description              |
|--------|---------------------|--------------------------|
| `GET`  | `/api/v1/templates` | List available templates |

---

## 📝 API Examples

### Send Contact Form Email

```bash
curl -X POST http://localhost:8080/api/v1/mail/template/contact-form \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "subject": "Project Inquiry",
    "message": "I would like to discuss a project."
  }'
```

### Send Template Email

```bash
curl -X POST http://localhost:8080/api/v1/mail/template/send \
  -H "Content-Type: application/json" \
  -d '{
    "templateName": "otp-mail",
    "subject": "Your OTP Code",
    "recipients": ["user@example.com"],
    "variables": {
      "otp": "123456"
    }
  }'
```

### Bulk Send Emails

```bash
curl -X POST http://localhost:8080/api/v1/mail/template/bulk \
  -H "Content-Type: application/json" \
  -d '{
    "templateName": "notification",
    "subject": "Important Update",
    "recipients": [
      "user1@example.com",
      "user2@example.com",
      "user3@example.com"
    ],
    "variables": {
      "message": "This is an important notification."
    }
  }'
```

---

## 📧 Email Templates

Templates are located in `src/main/resources/templates/`:

| Template                 | Description               |
|--------------------------|---------------------------|
| `otp-mail.html`          | OTP verification email    |
| `contact-form-mail.html` | Contact form notification |

### Creating Custom Templates

1. Create an HTML file in `src/main/resources/templates/`
2. Use Thymeleaf syntax for variables:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<h1>Hello, <span th:text="${name}">User</span>!</h1>
<p th:text="${message}">Your message here</p>
</body>
</html>
```

3. Send email using your template:

```json
{
  "templateName": "your-template",
  "subject": "Your Subject",
  "recipients": [
    "user@example.com"
  ],
  "variables": {
    "name": "John",
    "message": "Welcome to our service!"
  }
}
```

---

## 🔐 Security Configuration

### Disable Security (Development)

```yaml
app:
  security:
    enabled: false
```

### Enable Security with AWS Cognito

```yaml
app:
  security:
    enabled: true

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://cognito-idp.{region}.amazonaws.com/{user-pool-id}
          jwk-set-uri: https://cognito-idp.{region}.amazonaws.com/{user-pool-id}/.well-known/jwks.json
```

---

## 🐳 Docker Deployment

### Build Image

```bash
docker build -t email-service .
```

### Run with Docker Compose

```bash
docker-compose -f docker-compose.local.yaml up -d
```

### Environment Variables for Docker

```yaml
environment:
  - JAVA_MAIL_SENDER_EMAIL=your.email@gmail.com
  - JAVA_MAIL_SENDER_APP_PASSWORD=your_app_password
  - APP_SECURITY_ENABLED=false
  - KAFKA_ENABLED=false
```

---

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### 1️⃣ Fork & Clone

```bash
git clone https://github.com/yourusername/email-service.git
cd email-service
```

### 2️⃣ Create a Branch

```bash
git checkout -b feature/your-feature-name
```

### 3️⃣ Make Changes

- Follow existing code style
- Add tests for new features
- Update documentation

### 4️⃣ Run Tests

```bash
./mvnw test
```

### 5️⃣ Submit Pull Request

- Push your branch
- Create a Pull Request with clear description
- Wait for review

### 💡 Contribution Ideas

- [ ] Add support for SendGrid/AWS SES
- [ ] Implement dead letter queue for failed emails
- [ ] Add email tracking (open/click rates)
- [ ] Create more email templates
- [ ] Add rate limiting
- [ ] Implement email scheduling UI
- [ ] Add support for file attachments
- [ ] Create Prometheus metrics

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Spring Boot Team
- Apache Kafka
- Thymeleaf

---

**⭐ Star this repo if you find it useful!**

*"Plug this in once — and never worry about sending emails again."*
