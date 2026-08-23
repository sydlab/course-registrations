# ADR 0002: Defer authentication to a later phase

- **Status:** Accepted
- **Date:** 2026-08-23

## Context

v1 aims to close the existing health, list-courses, and add-student slice. Headers such as `user-id` and `requestId` already appear for logging. Full auth would expand scope into identity, roles, and security configuration.

## Decision

Ship v1 **without authentication or RBAC**. Treat existing identity-like headers as **tracing only**, not authorization.

## Consequences

- Faster v1 close-out; APIs are open on the network where deployed
- Not suitable for real institutional production without Phase 3 work
- Teacher/admin features wait until auth exists
