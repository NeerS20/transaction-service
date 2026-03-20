# Transaction Service

A Spring Boot microservice for managing accounts and transactions with PostgreSQL and Flyway migrations.

---

## Features

* Create and retrieve **Accounts** and **Transactions**
* ACID-compliant transactions with **OperationType** tracking
* Database migration via **Flyway**
* Validation and error handling
* Docker-ready for easy deployment

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

## Setup

1. **Clone the repository**

```bash
git clone <your-repo-url>
cd transaction-service
```

2. **Configure `application.properties`**

```properties
spring.datasource.url=<database_url>
spring.datasource.username=<db_username>
spring.datasource.password=<db_password>
```

3. **Build and run locally**

```bash
./gradlew bootRun
```

---

## Database

Flyway handles migrations automatically. Place `.sql` scripts in:

```text
src/main/resources/db/migration
```

Example migration files:

```text
V1__create_account_table.sql
V2__create_transaction_table.sql
```

---

## API Endpoints

### Accounts

| Method | Endpoint           | Description          |
| ------ | ------------------ | -------------------- |
| POST   | /api/accounts      | Create a new account |
| GET    | /api/accounts/{id} | Get account by ID    |

**Example Requests**

* **Create Account**

```json
POST /api/accounts
Content-Type: application/json

{
"documentNumber": "1235"
}
```

* **Get Account**

```json
GET /api/accounts/1
```

Response:

```json
{
  "accountId": 1,
  "documentNumber": "1235"
}
```

### Transactions

| Method | Endpoint               | Description              |
| ------ | ---------------------- | ------------------------ |
| POST   | /api/transactions      | Create a new transaction |

**Example Requests**

* **Create Transaction**

```json
POST /api/transactions
Content-Type: application/json
{
"accountId": 1,
"operationTypeId": 4,
"amount": 120.45
}
```
---

## Testing

Run tests with:

```bash
./gradlew test
```

---

## Deployment

* **Build JAR**

```bash
./gradlew bootJar
```

* **Run with Docker**

```bash
docker build -t transaction-service .
docker run -p 8080:8080 transaction-service
```
