# ADR 0004: Student number convention

- **Status:** Accepted
- **Date:** 2026-08-23

## Context

`students.student_number` is unique and required. Seed data uses values such as `STU-2025-001`. An inventory TODO proposed `STU-YYYY-XX` with `XX` as the enrollment month, which would collide for a second student in the same month.

The default enrollment year (when the client omits `enrollmentYear`) must not depend on the JVM's default timezone.

## Decision

Generate `student_number` as **`STU-{year}-{seq}`**:

- `{year}` is `enrollmentYear` from the request, or the current year in **America/Denver** (Mountain Time) when omitted
- `{seq}` is a 3-digit per-year sequence (`001`, `002`, …)

Do not use month-only suffixes. Ignore `studentId` and `studentNumber` on the request body.

## Consequences

- Numbers stay unique under the schema constraint and match local seed shape
- Default year around New Year follows MST/MDT, not UTC or the host timezone
- Sequence generation is in-process (fine for v1 single instance); concurrent inserts of the same year could race later
