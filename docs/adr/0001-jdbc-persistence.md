# ADR 0001: Use JDBC for persistence

- **Status:** Accepted
- **Date:** 2026-08-23

## Context

The app includes both `spring-boot-starter-jdbc` and `spring-boot-starter-data-jpa`. Implemented repositories use `JdbcTemplate` SQL. Closing v1 needs a clear persistence story without a rewrite.

## Decision

Keep **JDBC / `JdbcTemplate`** as the v1 persistence approach for course reads and student writes.

## Consequences

- SQL and schema stay explicit and easy to match to `data/` DDL docs
- JPA dependency may remain unused until a later deliberate migration
- Developers must maintain SQL strings carefully (column counts, keys)
