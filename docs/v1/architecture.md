# v1 architecture

## Stack

| Layer | Choice |
|-------|--------|
| Language | Java 17 |
| Framework | Spring Boot 4.x |
| HTTP | Spring Web MVC |
| Persistence | Spring JDBC (`JdbcTemplate`) + MySQL |
| Build | Maven Wrapper |

## Logical layout

```text
HTTP → HealthController
         → JdbcTemplate (SELECT 1)

HTTP → CourseController
         → CourseService
              → CoursesRepo (JDBC)
                   → MySQL (course_registrations)

HTTP → StudentController
         → StudentService
              → StudentRepo (JDBC)
                   → MySQL (course_registrations)
```

Config:

- `DatabaseConfig` — `DataSource` + `JdbcTemplate`

## Request flows (v1)

### Health

`GET /health` → `JdbcTemplate` `SELECT 1` → `"UP"` / `"DOWN"`

### List courses

`GET /courses/all` + `user-id` header → `CourseController` → `CourseService` → `CoursesRepo` → `List<Course>`  
Empty list maps to HTTP 404. Response fields match the `courses` table: `courseId`, `code`, `name`, `credits`, `capacity`, `departmentId`, `instructorId`.

### Add student

`POST /students/add` + `requestId` header + JSON body → `StudentController` → `StudentService` → `StudentRepo` JDBC insert.  
Success is HTTP 200 with `Student added successfully`. Persistence failures (including duplicate email) map to HTTP 500. `student_number` is generated as `STU-{year}-{seq}` — see [ADR 0004](../adr/0004-student-number-convention.md).

## Boundaries

- **In process:** all v1 business logic runs in this service
- **External DB:** MySQL must exist and be migrated manually before startup
- **No auth gateway** in v1
- **No UI** in v1

## Deliberate non-goals in the architecture

- Do not introduce a generic “Participant / Offering” abstraction in v1
- Do not add institution/tenant columns until multi-org work starts
- Prefer fixing the JDBC path over adopting JPA for v1 close-out
