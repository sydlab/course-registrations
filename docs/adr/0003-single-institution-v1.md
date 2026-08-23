# ADR 0003: Single institution in v1

- **Status:** Accepted
- **Date:** 2026-08-23

## Context

The product may later be offered to multiple organizations, but the current schema and APIs have no tenant/institution dimension. Multi-tenancy affects nearly every table and query.

## Decision

v1 assumes **one institution per deployment**. No `institution_id` (or equivalent) in schema or APIs yet.

## Consequences

- Simpler data model and docs for v1
- Multi-org requires a later migration and API changes (Phase 4)
- Domains outside education remain a reuse story, not a v1 abstraction
