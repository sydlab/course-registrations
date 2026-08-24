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
| Header | `requestId` (required; tracing only, not authorization) |
| Body | `Content-Type: application/json` with student fields below |
| Success | `200` + `Student added successfully` |
| Failure | `500` + `Failed to add student` (including duplicate email) |

The server generates `student_number` as `STU-{enrollmentYear}-{seq}` (3-digit per-year sequence). See [ADR 0004](../adr/0004-student-number-convention.md). `studentId` / `studentNumber` in the request body are ignored. If `enrollmentYear` is omitted, the current year in America/Phoenix (MST) is used.

### Student fields (request body)

| Field | Column | Notes |
|-------|--------|--------|
| `firstName` | `first_name` | required by schema |
| `lastName` | `last_name` | required by schema |
| `email` | `email` | unique |
| `enrollmentYear` | `enrollment_year` | integer; defaults to current year in America/Phoenix |

---

## Not in v1

- `POST /enrollments` (or equivalent)
- Drop / swap section
- Teacher or admin routes
- Standardized error body (e.g. RFC 7807)
- OpenAPI artifact (optional Phase 1)

When the contract is stable, prefer generating or authoring OpenAPI from this doc rather than the reverse for one-off field tweaks. Sticky policy choices stay in ADRs.
