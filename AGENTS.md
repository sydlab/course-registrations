# AGENTS.md

Lives at the **root of each target repo** after bootstrap. Master coordination contract for PM, Engineering, and Review agents.

---

## Agent Roster

| Agent | Rules File | Activates When | Owns |
|-------|-----------|---------------|------|
| **PM Agent** | `.cursor/rules/pm-agent.mdc` | New requirement, sprint/board queries | Issues, board, milestones |
| **Engineering Agent** | `.cursor/rules/eng-agent.mdc` | `status: ready-for-eng` | Branch, implementation, PR, CI |
| **Review Agent** | `.cursor/rules/review-agent.mdc` | `Review PR #N` in Cursor | Read-only review, AC coverage |

Slack triggers in agent rules are optional — use when Slack is configured in `project-context.mdc`.

---

## Semi-Autonomous Operation

**PROPOSE → APPROVE → EXECUTE** on every write action.

Approval words: `create`, `start`, `send`, `go`, `approve`.

---

## Label Contract

```
PM sets                         Engineering reacts
────────────────                ──────────────────
status: backlog
status: refinement
status: ready-for-eng    ──►    pickup

Engineering sets                PM reacts
────────────────                ──────────
status: in-progress    ──►    update board / notify team
status: in-review      ──►    update board / notify team
status: done           ──►    close issue, milestone
status: blocked        ──►    escalate
status: needs-clarification ──► re-scope
```

---

## Issue Body Ownership

| Section | Owner |
|---------|-------|
| `## 📋 Business Requirement` | PM |
| `## 🎯 Acceptance Criteria` | PM |
| `## 📐 Scope` | PM |
| `## 🔗 Business Context` | PM |
| `## 🤝 Engineering Handoff` | Engineering |
| `## 🏷️ Metadata` | PM (Eng updates `status` only) |

---

## Multi-Repo Rules

- Shared rules: copy from `agent-workflow/.cursor/rules/` (all except `project-context.mdc`)
- Per repo: fill `project-context.mdc` only
- Source module: `github.com/SydLabs9/agent-projects` → `agent-workflow/`
- Releases and consumer sync: parent repo `RELEASE.md` / `CONSUMERS.md` (not copied here)

---

## Setup Checklist (target repo)

- [ ] Copy 5 `.cursor/rules/*.mdc` files from `agent-workflow/`
- [ ] Fill `project-context.mdc` (see `examples/project-context.example.mdc`)
- [ ] Copy `AGENTS.md`, issue template, PR template
- [ ] Run label script in `00-coordination.mdc`
- [ ] GitHub Project board (optional)
- [ ] Test: requirement in Cursor → PM draft → `create`
- [ ] Confirm issue emoji headings render (UTF-8 `--body-file` on Windows)
- [ ] Confirm no tool attribution on test commit / issue comments

---

## No tool attribution

See `00-coordination.mdc`. Commits, issues, PRs, and comments must not include vendor co-author trailers or "Made with …" footers. Prefer `--body-file` (UTF-8) for emoji-rich issue bodies on Windows and macOS.

---

## Optional: Slack + GitHub Actions

Not included in the module. If you add Actions later:

```
GITHUB_TOKEN, SLACK_BOT_TOKEN, SLACK_CHANNEL_DEV, SLACK_CHANNEL_TRACKING
IN_PROGRESS_COLUMN_ID, IN_REVIEW_COLUMN_ID, DONE_COLUMN_ID
```

---

## Commit messages (your machine)

Configured **outside** this repo:

- Cursor **User** rule (Settings) — from `~/tech/AI/cursor/user-rules/git-commits.mdc`
- Git hooks — `~/tech/AI/cursor/git-hooks/install.sh`
