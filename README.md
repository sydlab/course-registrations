# course-registrations

API for managing student course registrations (education-first).

v1 is a small closable slice: **health check**, **list courses**, and **add student**.

## Prerequisites

- Java 17
- MySQL 8.x on `localhost:3306`
- A MySQL admin user that can `CREATE DATABASE` and `CREATE USER`
- `curl` (or any HTTP client)

The repo includes the Maven Wrapper (`./mvnw` / `.\mvnw.cmd`). You do not need a global Maven install.

## Local runbook

Follow this path on a fresh machine. Full SQL lives in [database/database-setup.md](database/database-setup.md).

### 1. Create the database and app user

Locked local-dev names:

| Item | Value |
|------|--------|
| Database | `course_registrations` |
| App user | `app_user` @ `localhost` |
| JDBC URL | `jdbc:mysql://localhost:3306/course_registrations` |

Pick a password on your machine. Type that value into the `CREATE USER` statement in [database/database-setup.md](database/database-setup.md). MySQL does **not** expand `${COURSE_REG_DB_PASSWORD}` if you paste that placeholder as-is.

This password is **local-dev only**. It is not a production secrets model (no vault, no CI secret store). Do not commit it.

### 2. Apply schema and seed data

From the repo root, as `app_user`:

```bash
mysql -u app_user -p course_registrations < database/course_registrations.ddl
mysql -u app_user -p course_registrations < database/course_registrations.dml
```

Seed data is required for a non-empty catalog (`GET /courses/all` returns `200`). An empty `courses` table returns `404`.

### 3. Export the same password and start the app

macOS / Linux:

```bash
export COURSE_REG_DB_PASSWORD='your-local-password'
./mvnw spring-boot:run
```

Windows (PowerShell):

```powershell
$env:COURSE_REG_DB_PASSWORD = 'your-local-password'
.\mvnw.cmd spring-boot:run
```

Committed config (`src/main/resources/application.yaml`) reads `username: app_user` and `password: ${COURSE_REG_DB_PASSWORD}`. The app listens on `http://localhost:8080`.

### 4. Health

```bash
curl http://localhost:8080/health
```

Expect `UP` when MySQL answers `SELECT 1`, and `DOWN` when it does not. The process still starts if the database is down (`initialization-fail-timeout: -1`).

### 5. Catalog

`user-id` is required for logging only, not authorization.

```bash
curl -H "user-id: demo" http://localhost:8080/courses/all
```

Expect `200` and a JSON array after seed DML. Empty catalog: `404`.

### 6. Add student

`requestId` is required for tracing only. The server generates `student_number` as `STU-{year}-{seq}`.

```bash
curl -X POST http://localhost:8080/students/add \
  -H "Content-Type: application/json" \
  -H "requestId: demo-1" \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@student.edu","enrollmentYear":2026}'
```

Expect `200` and `Student added successfully`. Duplicate email: `500` and `Failed to add student`.

Contract details: [docs/v1/api.md](docs/v1/api.md).

## Not in v1

These are later phases — see [roadmap](docs/v1/roadmap.md):

- Enroll / drop / waitlist
- Authentication and roles
- Docker Compose, CI, OpenAPI, Flyway/Liquibase
- Multi-institution / tenant id
- Secret managers or production credential handling

## Design docs

- [v1 design index](docs/v1/README.md)
- [Architecture decision records](docs/adr/README.md)

## Agents

Delivery on `main` uses the Cursor HITL loop (PM → Eng → Review) from [SydLabs9/agent-projects](https://github.com/SydLabs9/agent-projects) **`agent-workflow` @ `v0.1.3`**.

See [AGENTS.md](./AGENTS.md) and `.cursor/rules/`. Approval words: `create`, `start`, `send`, `go`, `approve`.
