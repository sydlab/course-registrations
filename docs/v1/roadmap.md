# Roadmap

Education-first **course registration** product. Generalize only if a second domain or tenant forces it.

## Phase 0 — Close v1 (current)

- Stabilize health, list courses, add student
- Fix API/SQL bugs documented in [api.md](./api.md)
- Docs (`docs/v1`) + starter ADRs
- README runbook
- Keep placeholder DAOs off the happy path (inventory stubs were not ported)

**Exit:** [product-scope.md](./product-scope.md) definition of done.

## Phase 1 — Local shipability

- Docker Compose (app + MySQL)
- Optional: OpenAPI, basic CI, config via env for secrets
- Optional: schema migrations tool

## Phase 2 — Real registration

- Enroll / drop APIs on `ENROLLMENTS`
- Capacity checks (and waitlist only if needed)
- Keep domain language: student, course, enrollment

## Phase 3 — Roles

- Authentication
- Student / teacher / admin authorization
- Teacher/admin endpoints (rosters, catalog management)

## Phase 4 — Multi-institution

- `institution_id` (or equivalent) across data and APIs
- Still course-registration flavored per tenant

## Later (optional)

- Extract shared “catalog + registration” platform if another domain (L&D, events, classes) becomes a real customer
- Until then, do not rename the domain model for hypothetical reuse
