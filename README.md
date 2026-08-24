# course-registrations

API for managing student course registrations (education-first).

## v1 shared spec

v1 closes a small slice: health check, list courses, and add student. Design docs:

- [v1 design index](docs/v1/README.md)
- [Architecture decision records](docs/adr/README.md)

Full enrollment, auth, Docker, and multi-institution are later phases — see [roadmap](docs/v1/roadmap.md).

## Delivery approach

`main` is rebuilt in **small PRs** via the Cursor HITL agent loop (PM → Eng → Review), using [SydLabs9/agent-projects](https://github.com/SydLabs9/agent-projects) **`agent-workflow` @ `v0.1.2`**.

Application code lands via later Phase 0 slices. Local MySQL setup notes are in [`data/`](data/).

## Agents

After bootstrap: see [AGENTS.md](./AGENTS.md) and `.cursor/rules/`.

Approval words: `create`, `start`, `send`, `go`, `approve`.

## Local database

Create MySQL database `course_registrations`, apply the current DDL, and load seed data using [data/database-setup.md](data/database-setup.md).

That guide shows the locked database name and the current DDL/DML. Set the DB password locally; do not commit it.

## Run (local)

Target runbook once the service slice lands:

1. Follow [data/database-setup.md](data/database-setup.md) to create `course_registrations`.
2. Set datasource properties from that guide (prefer env/overrides for anything other than local-dev).
3. Start the app with Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Smoke-test:

```powershell
curl http://localhost:8080/health
curl -H "user-id: demo" http://localhost:8080/courses/all
```

`POST /students/add` is documented in [docs/v1/api.md](docs/v1/api.md); treat as unstable until request-body binding and related fixes land.
