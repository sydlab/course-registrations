# course-registrations

API for managing student course registrations (education-first).

## Delivery approach

`main` is rebuilt in **small PRs** via the Cursor HITL agent loop (PM → Eng → Review), using [SydLabs9/agent-projects](https://github.com/SydLabs9/agent-projects) **`agent-workflow` @ `v0.1.0`**.

Full application inventory (for porting) lives on branch `chore/merge-main-history` (includes `docs/v1` and ADRs).

## Agents

After bootstrap: see [AGENTS.md](./AGENTS.md) and `.cursor/rules/`.

Approval words: `create`, `start`, `send`, `go`, `approve`.
