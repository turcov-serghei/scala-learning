# Checkpoint — Session Recovery

> **Purpose:** restore agent context after a new, hung, or lost session.
> **Not here:** learning position, lesson status, and next step live ONLY in `scala-course/PROGRESS.md` ("Current Position"). Teaching rules live ONLY in `scala-course/LEARNING_STYLE.md`. Do not copy them into this file.

## 1. Recovery order

1. `scala-course/BACKGROUND.md` — who is being taught
2. `scala-course/LEARNING_STYLE.md` — rules
3. `scala-course/PROGRESS.md` "Current Position" — where we stopped, what is next
4. `scala-course/lessons/protocols/` — latest lesson protocol (weak spots, calibration)
5. The current lesson's theory file in `scala-course/lessons/`

## 2. Repository state

_Last updated: 2026-10-27_

- **Branch:** `docs/restructure-agent-rules` (from `origin/master`); `master` is the default branch. The old `self-check-lessons` branch is deleted.
- **Open PR:** #10 "Restructure learning rules and make git rules project-level" — awaiting the user's review and merge. Do not merge it.
- **Uncommitted work (not part of PR #10):** `PROGRESS.md`, `SYLLABUS.md` (Lesson 2.4 `Any`/`AnyVal`/`AnyRef`), and the Lesson 1.1 theory (with Review points), protocol, `lesson1_1.scala`, `lesson1_1.sc`, plus this file's own update. Run `git status` for the current list.
- **GitHub access:** `git` over SSH works. `gh` is installed and authenticated through the `GITHUB_TOKEN` environment variable; listing PRs, creating and editing a PR worked. Git rules: `GIT_RULES.md`.

## 3. Interrupted-session note

_Fill in when a session may stop mid-step; clear when the step is done. Only what is NOT already in PROGRESS.md or the protocol._

- 2026-10-27: no lesson step is open (Lesson 1.1 stays at section 8, see PROGRESS.md). Do not commit or push without an explicit instruction.
- **After PR #10 is merged:** switch to `master` and pull, delete the branch locally (`-D`; squash merge) and remotely (rule 5), refresh section 2 of this file.
- **Decisions still open with the user** (not recorded elsewhere):
  - "Kilo" is named as the Scala framework in `LEARNING_STYLE.md` Constraints/Goals, `AGENTS.md`, `README.md`, `SYLLABUS.md`; it is an AI agent, not a framework (candidate: http4s with Cats Effect/FS2).
  - Entry point for other AI tools: whether to add a thin `CLAUDE.md` pointing to `AGENTS.md` (the user wants the course AI-agnostic); nothing created yet.
  - `scala-course/self-check-lessons-0-1-to-0-4.md` is no longer referenced by any rule: keep or delete.
  - `lessons/lesson0_3_theory.md` Scala example imports `java.math.BigDecimal`, which contradicts its own Review points.
  - Lesson 1.2 theory must add Review points including `match` on sealed ADTs.

## 4. Language

- User writes mostly Russian; answer in the user's language unless asked otherwise.
- Keep established Russian phrases from the rules (e.g., "Что здесь непонятно?", "Готовы продолжить?").
