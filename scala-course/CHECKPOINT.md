# Checkpoint — Scala Learning Course

> **Purpose:** Restore AI agent context after a new session. Read this file first when resuming.

## Current Position (as of 2026-10-16)

- **Active exercise:** Exercise 0 — Optional → Option refactoring (Main Exercise)
- **Last position:** Exercise 0.4 completed (Level 1) — all assertions passed, foldLeft diagram added, Monad laws verified.
- **Next step:** User implements Exercise 0 (Optional → Option) skeleton and test cases.
- **Git branch:** `lesson0_4`
- **Last commit:** `63dae89` — "Remove junie's workarounds" (`scala-course/LEARNING_STYLE.md`)

## Completed Lessons (Module 0)

| Lesson | Status | Date Completed |
|--------|--------|----------------|
| 0.1 Type Classes | ✅ | 2026-10-07 |
| 0.2 Higher-Kinded Types | ✅ | 2026-10-07 |
| 0.3 For-Comprehensions Decoded | ✅ | 2026-10-09 |
| 0.4 Monads Explained | ✅ | 2026-10-15 |

## What Changed Recently

- `scala-course/LEARNING_STYLE.md` updated: removed UI-bug workaround and mandatory progress-confirmation rules; renumbered rules 7–18 sequentially; fixed self-correction checklist numbering (1–8).
- Removed rules are confirmed absent from the file.
- All internal rule references verified correct.

## Files to Read to Restore Context (in order)

1. `scala-course/BACKGROUND.md` — user profile (who you are teaching)
2. `scala-course/LEARNING_STYLE.md` — strict rules (18 rules, Sections I–V)
3. `scala-course/PROGRESS.md` "Current Position" — exact resume checkpoint
4. `scala-course/lessons/protocols/lesson0_4_protocol.md` — weak spots and calibration notes for Lesson 0.4
5. `scala-course/CHECKPOINT.md` — this file (session state summary)

## Key Rules to Remember

- Rule 1: Answer ALL unanswered user questions before new content.
- Rule 5: Theory first, then exercise — wait for user confirmation.
- Rule 11: Exercise Acceptance Criteria — functional style required (Rule 14).
- Rule 15: Update PROGRESS.md "Current Position" after each step.
- Rule 18: Self-correction checklist before every response.

## Notes

- Cats library (cats-core 2.13.0) remains in build.sbt.
- Scala 2.13, SBT, IntelliJ IDEA EAP.
- Exercise files: `src/main/scala/lessonX_Y_exercise.scala` with matching object name.

## Session End Reason

- Session ended to allow user to connect GitHub MCP server (`mcpServers.github` in `kilo.jsonc`).
- After MCP is configured and Kilo session restarted, GitHub tools (PR list, create PR, review, merge) should become available.
- Resume from this checkpoint file after restart.

## Language

- **User**: predominantly Russian (~80%).
- **Assistant responses**: predominantly English (~80%), with Russian terms preserved from rules (e.g., "Что здесь непонятно?", "Готовы продолжить?").
- Expect mixed Russian/English in conversation; respond in the user's language unless they request otherwise.
