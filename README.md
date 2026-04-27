
---

# 🚀 Event-Driven Order Processing System

> A production-style backend system built with Spring Boot and Kafka that demonstrates how real-world systems handle scale, failures, and asynchronous workflows.

---

## ⚡ Why This Project Exists

Most backend projects stop at CRUD APIs.

This project goes beyond that by solving real-world problems like:

* Service decoupling using Kafka
* Handling failures without blocking the system
* Ensuring data consistency in distributed systems
* Supporting multiple consumers (fan-out pattern)

---

## 🧠 What This Project Demonstrates

* Event-driven microservices architecture
* Kafka-based asynchronous communication
* Idempotent consumers to handle duplicate messages
* Retry with exponential backoff
* Dead Letter Topic (DLT) for failure isolation
* Database per service (true microservice design)

---

## 🏗️ System Design Highlights

* Order Service does **not know** about Inventory or Notification
* Kafka acts as the **event backbone**
* Services are independently scalable and deployable
* Failures are isolated using retry + DLT

---

## 🔥 Real-World Scenarios Handled

| Scenario                | Solution           |
| ----------------------- | ------------------ |
| Service failure         | Retry with backoff |
| Invalid data            | Direct DLT         |
| Duplicate events        | Idempotency        |
| Downstream service down | Kafka buffering    |

---

## 🎯 What Makes This Production-Ready

* No tight coupling between services
* Failure does NOT break the system
* Supports horizontal scaling
* Clean layered architecture
* Proper logging and monitoring

---

---

## 🧱 Architecture

```text
┌─────────────────────────────────────────────────────────────┐
│                        CLIENT                               │
│                  POST /api/v1/orders                        │
└───────────────────────┬─────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                   ORDER SERVICE  :8080                      │
│                                                             │
│   Controller → Service → Repository → PostgreSQL :5432      │
│                    ↓                                        │
│         Publishes OrderCreatedEvent                         │
└───────────────────────┬─────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────────┐
│               KAFKA BROKER  :9092                           │
│            Topic: order-created                             │
│         ┌──────────────┴──────────────┐                     │
│         │                            │                      │
└─────────┼────────────────────────────┼──────────────────────┘
          │                            │
          ▼                            ▼
┌──────────────────────┐   ┌──────────────────────────┐
│  INVENTORY SERVICE   │   │   NOTIFICATION SERVICE   │
│       :8081          │   │          :8082            │
│                      │   │                           │
│  group: inventory-   │   │  group: notification-     │
│  service-group       │   │  service-group            │
│                      │   │                           │
│  → Reserve stock     │   │  → Send confirmation      │
│  → PostgreSQL :5433  │   │  → PostgreSQL :5434       │
│  → Retry + DLT       │   │  → Retry + DLT            │
└──────────────────────┘   └───────────────────────────┘
```
## 🔍 Deep Dive: Event Flow

1. Client sends order request
2. Order Service saves data in PostgreSQL
3. Publishes `OrderCreatedEvent` to Kafka
4. Kafka stores event in topic
5. Inventory Service consumes event → reserves stock
6. Notification Service consumes event → processes notification
7. If failure occurs:

    * Message retried with exponential backoff
    * On repeated failure → sent to Dead Letter Topic
    * Stored in `failed_events` table

---

## 🔁 Failure Handling Strategy

* Retryable errors → retried (DB down, timeout)
* Non-retryable errors → sent to DLT
* No infinite loops
* No system blocking

---

## 📊 Kafka Behavior

* Same topic → multiple consumers
* Different consumer groups → independent processing
* Partition ensures ordering per key

---

---

## ⚙️ Tech Stack

| Technology         | Purpose               |
| ------------------ | --------------------- |
| Java 17            | Language              |
| Spring Boot 3.2.5  | Framework             |
| Spring Data JPA    | ORM                   |
| Spring Kafka       | Kafka integration     |
| PostgreSQL 15      | Database              |
| Apache Kafka       | Messaging             |
| Zookeeper          | Kafka coordination    |
| Lombok             | Boilerplate reduction |
| Jakarta Validation | Input validation      |
| SpringDoc OpenAPI  | Swagger documentation |
| Docker Compose     | Infrastructure        |

---

## 🧩 Services

| Service              | Port | DB Port | Description                          |
| -------------------- | ---- | ------- | ------------------------------------ |
| order-service        | 8080 | 5432    | Creates orders, publishes events     |
| inventory-service    | 8081 | 5433    | Consumes events, updates stock       |
| notification-service | 8082 | 5434    | Consumes events, sends notifications |
| Kafka                | 9092 | —       | Message broker                       |
| Kafka UI (Kafbat)    | 8090 | —       | Monitoring dashboard                 |

---

## ✨ Key Features

* Event-driven microservices architecture
* Fan-out pattern (multiple consumers per event)
* Database per service
* Idempotent consumers
* Retry with exponential backoff
* Dead Letter Topic (DLT) handling
* Validation + global exception handling
* Swagger API documentation

---

---

## 🎤 Interview Talking Points

If you're reviewing this project, here’s what I focused on:

* Designed an event-driven system using Kafka
* Implemented fan-out pattern for multiple consumers
* Solved duplicate processing using idempotency
* Handled failures using retry + Dead Letter Topic
* Ensured loose coupling between services
* Built system that continues processing even when parts fail

---

---

## 📦 Prerequisites

* Java 17+
* Maven 3.8+
* Docker Desktop

---

## ▶️ How to Run

### 1. Clone repo

```bash
git clone https://github.com/Aryan-Singh-Tomar/Event-Driven-Order-Processing-System.git
cd Event-Driven-Order-Processing-System/event-order-managment-system
```

---

### 2. Start infrastructure

```bash
docker compose up -d
```

Check:

```bash
docker ps
```

---

### 3. Create Kafka topic

```bash
docker exec -it order-kafka kafka-topics \
  --create \
  --topic order-created \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

---

### 4. Run services

```bash
# Order Service
cd orderservice && mvn spring-boot:run

# Inventory Service
cd inventory-service && mvn spring-boot:run

# Notification Service
cd notification-service && mvn spring-boot:run
```

---

## 📚 API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Create Order Example

```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Aryan Tomar",
    "productName": "Mechanical Keyboard",
    "quantity": 2,
    "price": 4999.99
  }'
```

---

### ✅ Expected Response

```json
{
  "id": 1,
  "customerName": "Aryan Tomar",
  "productName": "Mechanical Keyboard",
  "quantity": 2,
  "price": 4999.99,
  "status": "CREATED",
  "createdAt": "2024-01-15T10:30:00"
}
```

---

## 🔄 What Happens Internally

1. Order saved in PostgreSQL
2. Kafka event published
3. Inventory service updates stock
4. Notification service processes message

---

## 📊 Monitoring

| URL                                                                            | Purpose      |
| ------------------------------------------------------------------------------ | ------------ |
| [http://localhost:8090](http://localhost:8090)                                 | Kafka UI     |
| [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | Swagger      |
| [http://localhost:8080/health](http://localhost:8080/health)                   | Health check |

---

## 🛡️ Resilience (Retry + DLT)

* Retries: 3 times (1s → 2s → 4s)
* Failed messages → Dead Letter Topic
* Stored in `failed_events` table
* View via Kafka UI

---

## 📁 Project Structure

```text
event-order-managment-system/
├── docker-compose.yml
├── orderservice/
├── inventory-service/
└── notification-service/
```

---

## 🌿 Git Strategy

```text
main ← day-1 ← day-2 ← ... ← day-10
```

---

## 👨‍💻 Author

**Aryan Tomar**

---

