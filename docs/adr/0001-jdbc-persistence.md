# ADR 0001: Use JDBC for persistence

- **Status:** Accepted
- **Date:** 2026-08-23

## Context

The v1 service uses **Spring JDBC** (`JdbcTemplate`) for course reads and student writes. An older inventory tree also had unused JPA; that dependency was not brought onto `main`.

## Decision

Keep **JDBC / `JdbcTemplate`** as the v1 persistence approach for course reads and student writes.

## Consequences

- SQL and schema stay explicit and easy to match to `database/` DDL docs
- JPA is not a v1 dependency; adding it needs a new ADR
- Developers must maintain SQL strings carefully (column counts, keys)
