
---

# Time Deposit API

A RESTful API for managing **time deposits**, built using **Hexagonal Architecture (Ports & Adapters)**.
The system exposes endpoints to retrieve all time deposits and to update their balances in bulk.

---

## 📌 Features

* Retrieve all time deposits
* Update balances of all time deposits
* Clean separation of concerns using Hexagonal Architecture
* OpenAPI (Swagger) contract for API documentation
* Framework-agnostic core domain

---

## 🏗 Architecture Overview

This project follows **Hexagonal Architecture**, also known as **Ports and Adapters**.

### Core Principles

* The **domain** is independent of frameworks and technologies
* External concerns (HTTP, database, Swagger) are implemented as **adapters**
* Dependencies always point **inward** toward the domain

```
        ┌─────────────────────────┐
        │     Inbound Adapters     │
        │  (REST, OpenAPI, HTTP)  │
        └────────────┬────────────┘
                     │
        ┌────────────▼────────────┐
        │     Application Layer   │
        │      (Use Cases)        │
        └────────────┬────────────┘
                     │
        ┌────────────▼────────────┐
        │        Domain           │
        │ (Entities & Business)  │
        └────────────┬────────────┘
                     │
        ┌────────────▼────────────┐
        │    Outbound Adapters    │
        │   (DB, Persistence)    │
        └─────────────────────────┘
```

---

## 📂 Project Structure

```
project-root/
├── domain/                         # Core business logic (framework-free)
│   ├── model/                      # Domain entities
│   └── port/
│       ├── in/                     # Use case interfaces
│       └── out/                    # Repository interfaces
│
├── application/                    # Use case implementations
│   └── service/
│
├── adapters/
│   ├── inbound/
│   │   └── rest/
│   │       ├── controller/         # REST controllers
│   │       └── openapi/
│   │           └── time-deposit-api.yaml
│   │
│   └── outbound/
│       └── persistence/
│           └── repository/         # DB adapters
│
├── infrastructure/                 # Framework & configuration
│
└── README.md
```

---

## 🔌 API Endpoints

### Retrieve all time deposits

```
GET /time-deposits
```

**Response Schema**

```json
[
  {
    "id": "td-001",
    "planType": "FIXED",
    "balance": 10500.75,
    "days": 180,
    "withdrawals": 2
  }
]
```

---

### Update balances of all time deposits

```
PUT /time-deposits
```

**Description**

* Recalculates and updates balances for all time deposits
* Business rules are handled in the domain layer
* No request body required

---

## 📘 OpenAPI / Swagger

* The OpenAPI contract is located at:

```
adapters/inbound/rest/openapi/time-deposit-api.yaml
```

* This contract defines:

    * Available endpoints
    * Request/response schemas
    * HTTP methods and status codes

You can use it with:

* Swagger UI
* Redoc
* OpenAPI Generator
* Postman import

---

## 🔁 Data Flow (Request Lifecycle)

```
HTTP Request
   ↓
REST Controller (Inbound Adapter)
   ↓
Application Service (Use Case)
   ↓
Domain Model (Business Logic)
   ↓
Persistence Port
   ↓
Database Adapter
```

---

## 🧪 Testing Strategy (Recommended)

* **Domain**: unit tests (pure business logic)
* **Application**: use case tests with mocked ports
* **Adapters**: integration tests (REST + DB)
* **Contract**: OpenAPI validation tests

---

## 🚀 Getting Started

1. Clone the repository
2. Review the OpenAPI contract
3. Implement outbound adapters (DB)
4. Wire adapters in the infrastructure layer
5. Run the application and access Swagger UI

---

## 📚 Glossary

* **Inbound Adapter**: Handles incoming requests (HTTP, REST)
* **Outbound Adapter**: Handles external systems (DB, messaging)
* **Port**: Interface defining communication with the domain
* **Domain**: Core business rules and entities

---

## ✅ Design Goals

* Maintainable
* Testable
* Framework-independent domain
* Clear API contract
* Scalable architecture

---

If you want, I can:

* Tailor this README to **Spring Boot / Node / NestJS**
* Add **run instructions**
* Add **Swagger UI screenshots**
* Include **architecture decision records (ADR)**

Just tell me 👍
