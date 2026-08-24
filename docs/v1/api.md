# v1 API contract

Unauthenticated HTTP API. Headers such as `user-id` and `requestId` are for tracing/logging only — not authorization.

Base URL (local): `http://localhost:8080` (default Spring Boot port unless overridden).

---

## `GET /health`

**Purpose:** Liveness of app + database connectivity.

| Item | Value |
|------|--------|
| Auth | None |
| Success body | `UP` (plain text) when `SELECT 1` succeeds |
| Failure body | `DOWN` when DB check fails |

---

## `GET /courses/all`

**Purpose:** Return the full course catalog.

| Item | Value |
|------|--------|
| Header | `user-id` (required by controller today; used for logging only) |
| Success | `200` + JSON array of `Course` |
| Empty catalog | `404` |

### Course fields (response)

JSON object fields from the `courses` table:

| Field | Column |
|-------|--------|
| `courseId` | `course_id` |
| `code` | `code` |
| `name` | `name` |
| `credits` | `credits` |
| `capacity` | `capacity` |
| `departmentId` | `department_id` |
| `instructorId` | `instructor_id` |

---

## `POST /students/add`

**Purpose:** Create a student row.

| Item | Value |
|------|--------|
| Headers (current code) | `requestId`; student fields incorrectly bound from headers today |
| Intended v1 contract | `Content-Type: application/json` body with student fields; `requestId` header for tracing |
| Success | `200` + `"Student added successfully"` (stabilize toward `201` in a later pass if desired) |
| Failure | `500` + `"Failed to add student"` |

### Known issues to fix in v1 close-out

1. Student is bound with `@RequestHeader Student student` — should be `@RequestBody`
2. `studentService.addStudent` is invoked twice
3. Insert SQL column/placeholder mismatch and missing `student_number` generation (TODO in repo)

Until those are fixed, treat this endpoint as **unstable**.

---

## Not in v1

- `POST /enrollments` (or equivalent)
- Drop / swap section
- Teacher or admin routes
- Standardized error body (e.g. RFC 7807)
- OpenAPI artifact (optional Phase 1)

When the contract is stable, prefer generating or authoring OpenAPI from this doc rather than the reverse for one-off field tweaks. Sticky policy choices stay in ADRs.
