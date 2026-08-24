# v1 architecture

## Stack

| Layer | Choice |
|-------|--------|
| Language | Java 17 |
| Framework | Spring Boot 4.x |
| HTTP | Spring Web MVC |
| Persistence (used) | Spring JDBC (`JdbcTemplate`) + MySQL |
| Persistence (present, unused) | `spring-boot-starter-data-jpa` |
| Validation | Starter present; not enforced on v1 endpoints yet |
| Build | Maven Wrapper |
| Outbound HTTP | `RestClient` bean scaffold; unused in v1 product flows |

## Logical layout

```text
HTTP → RegistrationsController
         → CourseService / StudentService
              → CoursesRepo / StudentRepo (JDBC)
                   → MySQL (course_registrations)
```

Config:

- `DatabaseConfig` — `DataSource` + `JdbcTemplate`
- `HttpClientConfig` — outbound `RestClient` (not on the v1 happy path)

## Request flows (v1)

### Health

`GET /health` → `JdbcTemplate` `SELECT 1` → `"UP"` / `"DOWN"`

### List courses

`GET /courses/all` + `user-id` header → `CourseService` → `CoursesRepo` → `List<Course>`  
Empty list maps to HTTP 404.

### Add student

`POST /students/add` → `StudentService` → `StudentRepo` JDBC insert → success/failure response  
v1 close-out must fix request binding and duplicate service calls (see [api.md](./api.md)).

## Boundaries

- **In process:** all v1 business logic runs in this service
- **External DB:** MySQL must exist and be migrated manually before startup
- **No auth gateway** in v1
- **No UI** in v1

## Deliberate non-goals in the architecture

- Do not introduce a generic “Participant / Offering” abstraction in v1
- Do not add institution/tenant columns until multi-org work starts
- Prefer fixing the JDBC path over adopting JPA for v1 close-out
