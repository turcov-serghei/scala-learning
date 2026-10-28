# Scala Learning Course — Project Guide for AI Agents

## Purpose

This project is a **Scala learning course** for a Java Senior Developer transitioning to Scala. The course teaches functional programming concepts through **Java-first analogies** and **realistic refactoring exercises** (payments, wallets, settlements), not "Hello World."

**Goals and tech stack:** defined only in `scala-course/SYLLABUS.md` (Course Goals, Tech Stack).

---

## Interaction Principles

- **Answer, don't act:** When the user asks to check/verify/analyze something — report findings first. Do not modify files until explicit approval ("сделай", "исправь", "добавь"). Exception: theory, protocol, and progress files are auto-updated — see Rule 2 (File Change Permissions) in `scala-course/LEARNING_STYLE.md`.
- **Git:** all git operations follow `GIT_RULES.md` (commit/push/merge only on explicit instruction, diff shown before commit).

Teaching rules (questions first, pacing, progress updates) are defined only in `scala-course/LEARNING_STYLE.md`.

---

## Project Structure

```
.
├── AGENTS.md                  ← You are here — guide for AI agents
├── GIT_RULES.md               ← Git workflow rules
├── README.md                  ← Short project overview
├── build.sbt                  ← SBT build (Scala 2.13)
├── src/main/scala/            ← Exercise and scratch files (lessonX_Y*.scala, *.sc)
└── scala-course/
    ├── CHECKPOINT.md          ← Session recovery data (repository state, interrupted step)
    ├── BACKGROUND.md          ← User profile (Java Senior, experience, skill levels) — READ FIRST
    ├── LEARNING_STYLE.md      ← Strict rules for teaching
    ├── SYLLABUS.md            ← Full course plan (Modules 0-6)
    ├── PROGRESS.md            ← Current progress tracker
    ├── templates/             ← Exercise code templates and review checklists
    ├── lessons/               ← Theory files (lessonX_Y_theory.md), grows dynamically
    ├── lessons/protocols/     ← Learning history (lessonX_Y_protocol.md), grows dynamically
    ├── self-check-lessons-0-1-to-0-4.md ← Archive of old-format self-check questions
    └── plans/                 ← Historical artifacts from initial setup
```

**Note:** The `lessons/` and `protocols/` directories grow dynamically as new lessons are delivered. Do not list individual files here — just describe what each directory contains.

Exercise files (`src/main/scala/lesson*.scala`) are created per-lesson and are not listed in this file.

---

## File Descriptions

### `scala-course/BACKGROUND.md` — **READ FIRST**

Contains the user's profile: role (Java Senior Developer), experience, domain knowledge, and calibrated skill levels table. This file is the single source of truth for who you are teaching. Read it before LEARNING_STYLE.md to understand the context.

### `scala-course/LEARNING_STYLE.md` — **MUST READ SECOND**

Contains strict rules for teaching. Read this file directly for full rule details — do not summarize them here to avoid duplication.

### `scala-course/SYLLABUS.md` — Course Plan

Full course structure (Modules 0-6). Read directly for lesson topics and order.

### `scala-course/PROGRESS.md` — Progress Tracker

Current status of each lesson/exercise. **Start with the "Current Position" section** at the top — it contains the exact checkpoint (last theory part delivered, next step, key decisions) so you can resume from where the previous session stopped.

### `scala-course/CHECKPOINT.md` — Session Checkpoint

**Recovery aid only.** Contains the repository state (branch, last commit, uncommitted work), and a note about an interrupted step. It does NOT hold learning progress — that lives only in `PROGRESS.md`. **Read this file first when starting a new or recovered session.**

### `scala-course/templates/` — Exercise Code Templates

Reference material used by the rules in `LEARNING_STYLE.md`: `exercise_skeletons.md` (Level 1/2/3 skeletons), `exercise_pitfalls.md` (compile checks), `fp_review_checklist.md` (functional style review).

### `scala-course/lessons/` — Lesson Theory Files

Each lesson has a Markdown file containing the complete theory delivered during the session. Files are updated if the lesson content evolves.

### `scala-course/lessons/protocols/` — Learning History

Protocol files record the learning journey for each lesson (theory questions, self-check, exercises, weak spots, insights, calibration notes). The exact structure is defined in Rule 17 of `scala-course/LEARNING_STYLE.md`.

**These protocols are the basis for calibrating future lessons.** Before starting a new lesson, read the previous lesson's protocol.

### `GIT_RULES.md` — Git Workflow Rules (MUST READ)

Project-level git rules for every AI agent (Claude Code, Kilo, others). **Read this file before any git operation.**

### `scala-course/plans/` — Historical Artifacts

Historical artifacts from Junie's internal task tracking. Not needed for continuing the course.

**Note:** The file `scala-learning-course-setup.md` was the foundation that initiated this entire project — it defined the initial course structure, created LEARNING_STYLE, SYLLABUS, and PROGRESS files. Keeping it may be useful for future reference if the course needs to be rebuilt or restructured from scratch.

---

## How to Continue a Session

When starting a new session, follow these steps:

0. **Read `scala-course/CHECKPOINT.md`** — Repository state and the interrupted-step note (recovery data only, not learning progress).
1. **Read `scala-course/BACKGROUND.md`** — Understand who you are teaching (Java Senior, experience, skill levels).
2. **Read `scala-course/LEARNING_STYLE.md`** — Understand the strict rules before doing anything.
3. **Read `scala-course/PROGRESS.md` "Current Position" section** — This is your resume checkpoint. It tells you the exact last position (theory part delivered, next step, key decisions) so you can continue from where the previous session stopped.
4. **Read the latest protocol in `scala-course/lessons/protocols/`** — Weak spots, calibration notes, and the user's learning patterns.
5. **Read the current lesson's theory file in `scala-course/lessons/`** — Needed to continue a lesson that is in progress.
6. **Check `scala-course/SYLLABUS.md`** when starting a new lesson or module, and before writing any code (Tech Stack: Scala version) — Know what comes next.
7. **Resume from checkpoint** — Follow the "Next step" from Current Position. If a lesson is `🔄 In progress`, continue from the checkpoint. If the last lesson is `✅ Completed`, proceed to the next lesson in the syllabus.

This list is the single source for the reading order; other files point here instead of repeating it.
