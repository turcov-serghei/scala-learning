# Scala Learning Course — Project Guide for AI Agents

## Purpose

This project is a **Scala learning course** for a Java Senior Developer transitioning to Scala. The course teaches functional programming concepts through **Java-first analogies** and **realistic refactoring exercises** (payments, wallets, settlements), not "Hello World."

**Goal:** Build a Transaction, Wallet & Settlement engine in Scala using Kilo and FS2, while learning functional types, monads, and reactive programming.

---

## Interaction Principles

- **Answer, don't act:** When the user asks to check/verify/analyze something — report findings first. Do not modify files until explicit approval ("сделай", "исправь", "добавь").
- **Questions first:** Answer ALL unanswered user questions before delivering new content or making changes.
- **Show before commit:** Present diff and wait for approval before any git commit.
- **One concept at a time:** Deliver one concept per response (max 1 screen). Never dump multiple topics at once.
- **No auto-merge:** Never merge PRs or push to master without explicit instruction.

---

## Project Structure

```
scala-course/
├── README.md                  ← You are here — guide for AI agents
├── BACKGROUND.md              ← User profile (Java Senior, experience, skill levels) — READ FIRST
├── LEARNING_STYLE.md          ← Strict rules for teaching
├── SYLLABUS.md                ← Full course plan (Modules 0-6)
├── PROGRESS.md                ← Current progress tracker
├── templates/                 ← Exercise code templates (Level 1/2/3 skeletons)
├── lessons/                   ← Theory files (lessonX_Y_theory.md), grows dynamically
├── lessons/protocols/         ← Learning history (lessonX_Y_protocol.md), grows dynamically
└── plans/                     ← Historical artifacts from initial setup
```

**Note:** The `lessons/` and `protocols/` directories grow dynamically as new lessons are delivered. Do not list individual files here — just describe what each directory contains.

Exercise files (`src/main/scala/lesson*.scala`) are created per-lesson and are not tracked in this README.

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

**Created for new-session context restoration.** Contains a concise summary of the current state: active exercise, last completed position, next step, git branch, last commit, and the ordered list of files to read to restore context. **Read this file first when starting a new session** to quickly reload the agent context without replaying the full chat history.

### `scala-course/templates/` — Exercise Code Templates

Contains reusable code skeleton templates for exercise levels (Level 1/2/3). Referenced by "Code Template — 3 Levels on Request" rule in `LEARNING_STYLE.md` when the user requests a code template.

### `scala-course/lessons/` — Lesson Theory Files

Each lesson has a Markdown file containing the complete theory delivered during the session. Files are updated if the lesson content evolves.

### `scala-course/lessons/protocols/` — Learning History

Protocol files record the learning journey for each lesson:
- **Theory Questions**: Questions asked during theory (reveal conceptual gaps or deep understanding)
- **Exercise Weak Spots**: Where the user struggled, what required multiple attempts
- **Refactoring Requests**: What was refactored and why (OOP → FP)
- **Key Insights**: "Aha moments" and particularly good questions
- **Calibration Notes**: What to emphasize/simplify/accelerate in future lessons

**These protocols are the basis for calibrating future lessons.** Before starting a new lesson, read the previous lesson's protocol.

### `.kilo/git-rules.md` — Git Workflow Rules (MUST READ)

Git workflow rules for the AI agent. **Read this file before any git operation.**

---

Historical artifacts from Junie's internal task tracking. Not needed for continuing the course.

**Note:** The file `scala-learning-course-setup.md` was the foundation that initiated this entire project — it defined the initial course structure, created LEARNING_STYLE, SYLLABUS, and PROGRESS files. Keeping it may be useful for future reference if the course needs to be rebuilt or restructured from scratch.

---

## How to Continue a Session

When starting a new session, follow these steps:

1. **Read `scala-course/BACKGROUND.md`** — Understand who you are teaching (Java Senior, experience, skill levels).
2. **Read `scala-course/LEARNING_STYLE.md`** — Understand the strict rules before doing anything.
3. **Read `scala-course/PROGRESS.md` "Current Position" section** — This is your resume checkpoint. It tells you the exact last position (theory part delivered, next step, key decisions) so you can continue from where the previous session stopped.
4. **Read `scala-course/lessons/protocols/`** — Review recent protocols to understand user's learning patterns, weak spots, and strengths.
5. **Check `scala-course/SYLLABUS.md`** — Know the full course structure and where we are.
6. **Resume from checkpoint** — Follow the "Next step" from Current Position. If a lesson is `🔄 In progress`, continue from the checkpoint. If the last lesson is `✅ Completed`, proceed to the next lesson in the syllabus.

