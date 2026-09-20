# v1 product scope

## Intent

Ship a **small, closable course-registrations API** — not a real-school production system. Suitable as a starter that another institution could adopt later.

**Product identity:** course registration (students, courses, departments, instructors, enrollments as the domain language).

## Users

| Role | v1 |
|------|-----|
| Student | Implicit consumer of catalog, student-create, enroll, and drop APIs |
| Teacher | Out of scope (future) |
| Admin | Out of scope (future) |
| Auth | None — headers such as `user-id` / `requestId` are tracing only |

## In scope

### Functional

- Health check against MySQL
- List all courses
- Add a student
- Enroll an existing student in a course, with capacity enforcement
- Drop an existing enrollment so the seat no longer counts toward capacity
- Manual DB setup using existing `database/` DDL/DML docs

### Engineering close-out

- Fix known bugs on the five endpoints (see [api.md](./api.md))
- Align student insert SQL with the schema (`student_number`, column counts)
- Keep placeholder DAOs and unrelated stubs off the happy path (`CourseDao` / `Customer` were not ported onto `main`)
- Document config via env names; do not commit password values
- Publish `docs/v1` and starter ADRs
- Root README runbook: prerequisites, DB setup, run app, sample requests

## Out of scope

- Waitlist
- Prerequisites, schedule conflicts, grades, payments, transcripts
- Authentication and RBAC
- Teacher / admin endpoints
- Multi-tenant / institution id
- Web UI
- Docker Compose, CI, OpenAPI artifact, Flyway/Liquibase (Phase 1+)
- Outbound HTTP via `RestClient`
- Migrating persistence from JDBC to JPA

## Multi-institution / other domains

- **v1:** single hard-coded institution
- **Later:** multi-org and optional generalization to other “catalog + registration” domains — only when a second use case appears ([roadmap.md](./roadmap.md))

## Definition of done

1. Five endpoints behave correctly and match [api.md](./api.md)
2. Fresh MySQL + `database/` scripts + app run path works from the root README
3. `docs/v1` and initial ADRs are present
4. Known gaps are listed for adopters
5. No intentional double-calls or placeholder DAOs on the happy path
