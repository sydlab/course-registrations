# v1 data model

Canonical schema scripts live under `database/`. The ER diagram lives under `docs/data/`. This doc states what **v1 APIs** use versus what is **design-only**.

## Designed schema (from ER)

Entities in [`docs/data/course_registrations.mmd`](../data/course_registrations.mmd):

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
| `Course`, `Student` | Yes — catalog read and add-student |

`Enrollment`, `Instructor`, `Department`, and `Customer` are **not** Java types on `main`. Inventory leftovers (`CourseDao`, customer stubs, unused JPA / `RestClient`) were not ported. Schema-only entities remain in the ER/DDL as design-only.

## Persistence approach

- Runtime path: **JDBC** (`JdbcTemplate`) — see [ADR 0001](../adr/0001-jdbc-persistence.md)
- Schema application: **manual** SQL from [`database/database-setup.md`](../../database/database-setup.md) (no Flyway/Liquibase in v1)
- App assumes database `course_registrations` and a local password supplied via env (see that guide)

## Multi-institution

No `institution_id` (or equivalent) in v1. Single logical institution per deployment — [ADR 0003](../adr/0003-single-institution-v1.md).
