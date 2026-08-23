# Course registrations — v1 design

High-level design for **v1**: a closable slice of a **student course registration** API.

This product is education-first (students, courses, later enrollments). Other domains (training, events, bookings) are a future reuse story, not the v1 design driver.

## Documents

| Doc | Contents |
|-----|----------|
| [product-scope.md](./product-scope.md) | Users, in/out of scope, definition of done |
| [architecture.md](./architecture.md) | Stack, layers, request flow |
| [api.md](./api.md) | Endpoint contract and known issues |
| [data-model.md](./data-model.md) | Schema vs what v1 APIs use |
| [roadmap.md](./roadmap.md) | v1 close-out and later phases |

Key decisions that go beyond the API contract live in [`docs/adr/`](../adr/).

## What “v1 closed” means

- `GET /health`, `GET /courses/all`, and `POST /students/add` work and are documented
- MySQL setup via existing `data/` scripts is documented in the root README
- Dead stubs are removed or clearly marked off the happy path
- Gaps (enroll, auth, Docker, multi-org, UI) are explicit so adopters are not surprised
