# course-registrations

API for managing student course registrations (education-first).

## v1 shared spec

v1 closes a small slice: health check, list courses, and add student. Design docs:

- [v1 design index](docs/v1/README.md)
- [Architecture decision records](docs/adr/README.md)

Full enrollment, auth, Docker, and multi-institution are later phases — see [roadmap](docs/v1/roadmap.md).

## Delivery approach

`main` is rebuilt in **small PRs** via the Cursor HITL agent loop (PM → Eng → Review), using [SydLabs9/agent-projects](https://github.com/SydLabs9/agent-projects) **`agent-workflow` @ `v0.1.2`**.

Application code lands via Phase 0 slices. Local MySQL setup notes are in [`database/`](database/).

## Agents

After bootstrap: see [AGENTS.md](./AGENTS.md) and `.cursor/rules/`.

Approval words: `create`, `start`, `send`, `go`, `approve`.

## Local database

Create MySQL database `course_registrations`, apply the current DDL, and load seed data using [database/database-setup.md](database/database-setup.md).

That guide shows the locked database name and the current DDL/DML. Set the DB password locally; do not commit it.

## Run (local)

1. Follow [database/database-setup.md](database/database-setup.md) to create `course_registrations` and the `app_user`.
2. Set the database password in your shell (do not commit it):

```bash
export COURSE_REG_DB_PASSWORD='your-local-password'
```

3. Start the app:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
$env:COURSE_REG_DB_PASSWORD = 'your-local-password'
.\mvnw.cmd spring-boot:run
```

4. Check health (`UP` when MySQL answers, `DOWN` when it does not):

```bash
curl http://localhost:8080/health
```

5. List the course catalog (`user-id` is required for logging only, not authorization). Seeded data returns `200` and a JSON array. An empty catalog returns `404`.

```bash
curl -H "user-id: demo" http://localhost:8080/courses/all
```

6. Add a student (`requestId` is required for tracing only). Success returns `200` and `Student added successfully`. Duplicate email returns `500`.

```bash
curl -X POST http://localhost:8080/students/add \
  -H "Content-Type: application/json" \
  -H "requestId: demo-1" \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@student.edu","enrollmentYear":2026}'
```

See [docs/v1/api.md](docs/v1/api.md).
