# v1 data model

Canonical design lives under `data/` (ER diagram, DDL, DML, setup notes). This doc states what **v1 APIs** use versus what is **design-only**.

## Designed schema (from ER)

Entities in `data/regis_db.mmd`:

- `USERS`
- `DEPARTMENTS`
- `INSTRUCTORS`
- `COURSES`
- `STUDENTS`
- `ENROLLMENTS`

Relationships: departments own instructors and courses; instructors teach courses; students enroll in courses via `ENROLLMENTS`.

## Used by v1 APIs

| Entity | Usage |
|--------|--------|
| `COURSES` | Read via list-courses |
| `STUDENTS` | Insert via add-student |

## Design-only in v1 (not exposed)

| Entity | Notes |
|--------|--------|
| `ENROLLMENTS` | Core of Phase 2 registration |
| `INSTRUCTORS` / `DEPARTMENTS` | Needed for richer catalog later |
| `USERS` | Auth/identity later |

## Java models vs product path

| Model | v1 product path |
|-------|-----------------|
| `Course`, `Student` | Yes |
| `Enrollment`, `Instructor`, `Department` | Modeled; no API |
| `Customer` | Unrelated / orphan — quarantine in close-out |

## Persistence approach

- Runtime path: **JDBC** (`JdbcTemplate`) — see [ADR 0001](../adr/0001-jdbc-persistence.md)
- Schema application: **manual** SQL from [`data/database-setup.md`](../../data/database-setup.md) (no Flyway/Liquibase in v1)
- App assumes database `regis_db` and local-dev credentials from that guide already exist

## Multi-institution

No `institution_id` (or equivalent) in v1. Single logical institution per deployment — [ADR 0003](../adr/0003-single-institution-v1.md).
