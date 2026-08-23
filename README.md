# course-registrations

API for managing student course registrations (education-first).

## v1

v1 closes a small slice: health check, list courses, and add student. Design docs:

- [v1 design index](docs/v1/README.md)
- [Architecture decision records](docs/adr/README.md)

Full enrollment, auth, Docker, and multi-institution are later phases — see [roadmap](docs/v1/roadmap.md).

## Run (local)

1. Create MySQL database and schema using scripts/notes under [`data/`](data/).
2. Set datasource properties (see `src/main/resources/application.properties` for local-dev shape; prefer env/overrides for real secrets).
3. Start the app with Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Smoke-test:

```powershell
curl http://localhost:8080/health
curl -H "user-id: demo" http://localhost:8080/courses/all
```

`POST /students/add` is documented in [docs/v1/api.md](docs/v1/api.md); fix request-body binding before relying on it.
