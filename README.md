# 📧 Reusable Spring Boot Email Service with Kafka Integration

**Send, track, and stream emails with ease — powered by Spring Boot, Kafka, and JavaMailSender.**

---

## 🚀 What is this?

This is a **production-ready, reusable microservice** built with **Spring Boot** that handles email delivery in distributed systems using **Apache Kafka** as the messaging backbone.

It acts as an **email gateway** for your ecosystem:

- ✅ **Consumes** email payloads (e.g., OTPs, notifications, alerts) from a Kafka topic  
- 📤 **Sends** the emails using Spring's `JavaMailSender`  
- 📦 **Publishes** metadata of successfully sent emails to a Kafka topic — enabling downstream services to track, audit, or trigger next steps  

---

## 🔧 How it Works

1. **Producer Service** sends an email request (JSON) to a Kafka topic — e.g., `emails`
2. 📥 This Email Service **consumes** the request via a Kafka listener
3. 📬 It **sends** the email using SMTP (via Gmail, Mailgun, etc.)
4. 📤 Upon success, it **produces** an event to a `sent-emails` Kafka topic — fully decoupled

---

## 💡 Use Cases

- 🔐 Send OTPs or account verification emails  
- 📣 Notify users on status changes or updates  
- 📊 Feed email metadata to analytics, audit logs, or notification dashboards  
- 🧩 Integrate as a plug-and-play email microservice across your platform  

---

## 📦 Features

- ✅ Kafka Consumer for receiving email requests  
- ✅ JavaMailSender with configurable SMTP  
- ✅ Kafka Producer for publishing sent email info  
- ✅ Profile-based configs for local, Docker, and cloud  
- ✅ Lightweight, stateless, container-ready  
- ✅ Easily extendable: support for HTML, attachments, templating, etc.  

---

## ⚙️ Tech Stack

- **Spring Boot**  
- **Apache Kafka**  
- **JavaMailSender**  
- **Spring Kafka**  
- **Lombok**, **H2 (for local dev/testing)**  

---

## 🐳 Run with Docker

```bash
docker-compose up --build
```

## 🔐 Configuration via .env
```bash
JAVA.MAIL.SENDER.EMAIL=your.email@example.com
JAVA.MAIL.SENDER.APP.PASSWORD=your_app_password
KAFKA.BOOTSTRAP.SERVERS=kafka:9092
KAFKA.GROUPS.EMAILS=emailGroup
KAFKA.TOPICS.CONSUME.EMAILS=emails
KAFKA.TOPICS.PRODUCE.OTP=sent-emails
```

## 🛠️ Extending This Service

-	✅ Add HTML templating (Thymeleaf/Freemarker)
-	✅ Include attachments or inline images
-	✅ Introduce retry policies and dead letter queues
-	✅ Integrate with cloud email APIs (SendGrid, SES, Mailgun)

## ❤️ Built For
Microservice architectures that value:
	•	Loose coupling
	•	Async communication
	•	Centralized responsibilities

“Plug this in once — and never worry about sending emails again.”

