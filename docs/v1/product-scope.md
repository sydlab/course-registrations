# v1 product scope

## Intent

Ship a **small, closable course-registrations API** — not a real-school production system. Suitable as a starter that another institution could adopt later.

**Product identity:** course registration (students, courses, departments, instructors, enrollments as the domain language).

## Users

| Role | v1 |
|------|-----|
| Student | Implicit consumer of catalog + student-create APIs |
| Teacher | Out of scope (future) |
| Admin | Out of scope (future) |
| Auth | None — headers such as `user-id` / `requestId` are tracing only |

## In scope

### Functional

- Health check against MySQL
- List all courses
- Add a student
- Manual DB setup using existing `database/` DDL/DML docs

### Engineering close-out

- Fix known bugs on the three endpoints (see [api.md](./api.md))
- Align student insert SQL with the schema (`student_number`, column counts)
- Remove or quarantine dead code on the happy path (`CourseDao` stub, unrelated customer stubs)
- Document config via env names; do not commit password values
- Publish `docs/v1` and starter ADRs
- Root README runbook: prerequisites, DB setup, run app, sample requests

## Out of scope

- Enroll / drop / waitlist / capacity enforcement in the API
- Prerequisites, schedule conflicts, grades, payments, transcripts
- Authentication and RBAC
- Teacher / admin endpoints
- Multi-tenant / institution id
- Web UI
- Docker Compose, CI, OpenAPI artifact, Flyway/Liquibase (Phase 1+)
- Real outbound HTTP via `RestClient` (scaffold may remain unused)
- Migrating persistence from JDBC to JPA

## Multi-institution / other domains

- **v1:** single hard-coded institution
- **Later:** multi-org and optional generalization to other “catalog + registration” domains — only when a second use case appears ([roadmap.md](./roadmap.md))

## Definition of done

1. Three endpoints behave correctly and match [api.md](./api.md)
2. Fresh MySQL + `database/` scripts + app run path works from the root README
3. `docs/v1` and initial ADRs are present
4. Known gaps are listed for adopters
5. No intentional double-calls or placeholder DAOs on the happy path
