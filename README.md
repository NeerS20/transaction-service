# Transaction-service

A simple Spring Boot service to manage **Accounts** and **Transactions** for a cardholder system. This service implements 3 endpoints: create account, get account, and create transaction.

---

## Features

* Create and retrieve **Accounts**
* Create **Transactions** associated with accounts
* Each transaction has an **OperationType** (normal purchase, withdrawal, credit voucher, purchase with installments)
* Supports negative and positive transaction amounts based on type
* Input validation and error handling
* Docker-ready for easy execution
* JUnit tests included for core functionality

---

## Tech Stack

* **Backend:** Java 25, Spring Boot 4
* **Database:** PostgreSQL
* **Migration:** Flyway
* **Testing:** JUnit, Spring Boot Test
* **Containerization:** Docker

---

## Architecture Overview

```
            ┌─────────────┐
            │ REST        │
            │ Controllers │
            └─────┬───────┘
                  │
            ┌─────▼───────┐
            │ Service     │
            │ Layer       │
            └─────┬───────┘
                  │
            ┌─────▼───────┐
            │ Repository  │
            │ Layer       │
            └─────┬───────┘
                  │
            ┌─────▼───────┐
            │ PostgreSQL  │
            └─────────────┘
```

---

## Setup Instructions

1. **Clone the repository**

```bash
git clone <your-repo-url>
cd transaction-service
```

2. **Configure database in `application.properties`**

```properties
spring.datasource.url=<db_url>
spring.datasource.username=<db_username>
spring.datasource.password=<db_password>
```

3. **Run migrations (Flyway)**
   Flyway will automatically run migration scripts in `src/main/resources/db/migration`.

4. **Build and run the service**

```bash
./gradlew bootRun
```

5. **(Optional) Run with Docker**

```bash
docker build -t transaction-service .
docker run -p 8080:8080 transaction-service
```

---

## API Endpoints

### 1. Create Account

**POST** `/accounts`
**Request Body:**

```json
{
  "document_number": "12345678900"
}
```

**Response Example:**

```json
{
  "account_id": 1,
  "document_number": "12345678900"
}
```

---

### 2. Get Account

**GET** `/accounts/{accountId}`
**Response Example:**

```json
{
  "account_id": 1,
  "document_number": "12345678900"
}
```

---

### 3. Create Transaction

**POST** `/transactions`
**Request Body:**

```json
{
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45
}
```

**Response Example:**

```json
{
  "transaction_id": 1,
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45,
  "event_date": "2026-03-20T12:30:00"
}
```

---

## Testing

Run tests with:

```bash
./gradlew test
```

---

## Notes

* Transactions of type **purchase** or **withdrawal** are stored as negative amounts.
* Transactions of type **credit voucher** are stored as positive amounts.
* Only **create** and **get** endpoints are implemented for this step.

---
