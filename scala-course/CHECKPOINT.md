# Checkpoint — Session Recovery

> **Purpose:** restore agent context after a new, hung, or lost session.
> **Not here:** learning position, lesson status, and next step live ONLY in `scala-course/PROGRESS.md` ("Current Position"). Teaching rules live ONLY in `scala-course/LEARNING_STYLE.md`. Do not copy them into this file.

## 1. Recovery order

Single source: `AGENTS.md`, section "How to Continue a Session". It is not repeated here.

## 2. Repository state

_Last updated: 2026-11-06_

- **Branch:** `master` (HEAD `b73be60` "Add Lesson 1.1 materials and update progress") holds all Lesson 1.1 materials. The repository is public; PR numbers mentioned in older notes and protocols refer to its previous private repository and do not exist here.
- **Repository move (2026-11-06):** the project was moved to this public repository with rewritten history. Work only in a clone of this repository; an older local folder with the previous origin and unsanitized history must not be used for new work.
- **Uncommitted work:** none. Commit on a new branch from `master`, never directly on `master`.
- **Public repository:** no personal data in files or commits. Set the GitHub noreply address as local `user.email` in every fresh clone before the first commit.
- **GitHub access:** `git` over SSH works. `gh` is installed and authenticated through the `GITHUB_TOKEN` environment variable; listing PRs, creating and editing a PR worked; changing repository settings is not permitted. Git rules: `GIT_RULES.md`.

## 3. Interrupted-session note

_Fill in when a session may stop mid-step; clear when the step is done. Only what is NOT already in PROGRESS.md or the protocol._

- 2026-10-28 (end of session): Lesson 1.1 self-check is open: Q2 was answered and reviewed, and the user postponed the discussion of that review to the next session (see PROGRESS.md). No other question is waiting. Do not commit or push without an explicit instruction.
- **Postponed by the user:** the explanation of value discarding (text saved in the Lesson 1.1 protocol) and, after Lesson 1.1 closes, `flatMap` vs `map` on `Option` (see PROGRESS.md Notes).
- **To resume:** the user starts in `<project-root>` and says "read AGENTS.md and continue" (there is no `CLAUDE.md` yet). Then follow "How to Continue a Session" in `AGENTS.md` and resume Lesson 1.1 as PROGRESS.md says unless the user chooses to settle the open decisions below first.
- **Standing preferences from this session:** the user wants the course AI-agnostic (rules and state live only in the repo); answers in Russian; the user reviews diffs themselves before commits, so show the change list and wait for approval (`GIT_RULES.md` rule 7).
- **Decisions still open with the user** (not recorded elsewhere):
  - Entry point for other AI tools: whether to add a thin `CLAUDE.md` pointing to `AGENTS.md` (the user wants the course AI-agnostic); nothing created yet.
  - `scala-course/self-check-lessons-0-1-to-0-4.md` is marked as an archive of the old format: keep it or delete it.
  - Proposal not answered yet: put each exercise's full specification as a header comment in its file (follow-ups 1–3) and fix this in Rule 10; today the specification lives only in chat and a short protocol entry.
  - `lessons/lesson0_3_theory.md` Scala example imports `java.math.BigDecimal`, which contradicts its own legacy Review points.
  - Lesson 1.2 main exercise Technical Requirements must include `match` on sealed ADTs.

## 4. Language

- User writes mostly Russian; answer in the user's language unless asked otherwise.
- Keep established Russian phrases from the rules (e.g., "Что здесь непонятно?", "Готовы продолжить?").
