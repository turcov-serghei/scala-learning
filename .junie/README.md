# Scala Learning Course — Project Guide for AI Agents

## Purpose

This project is a **Scala learning course** for a Java Senior Developer transitioning to Scala. The course teaches functional programming concepts through **Java-first analogies** and **realistic refactoring exercises** (payments, wallets, settlements), not "Hello World."

**Goal:** Build a Transaction, Wallet & Settlement engine in Scala using Kilo and FS2, while learning functional types, monads, and reactive programming.

---

## Project Structure

```
.junie/
├── README.md                  ← You are here — guide for AI agents
├── BACKGROUND.md              ← User profile (Java Senior, experience, skill levels) — READ FIRST
├── LEARNING_STYLE.md          ← Strict rules for teaching
├── SYLLABUS.md                ← Full course plan (Modules 0-6)
├── PROGRESS.md                ← Current progress tracker
├── lessons/                   ← Theory files (lessonX_Y_theory.md), grows dynamically
├── lessons/protocols/         ← Learning history (lessonX_Y_protocol.md), grows dynamically
└── plans/                     ← Historical artifacts from initial setup
```

**Note:** The `lessons/` and `protocols/` directories grow dynamically as new lessons are delivered. Do not list individual files here — just describe what each directory contains.

Exercise files (`src/main/scala/lesson*.scala`) are created per-lesson and are not tracked in this README.

---

## File Descriptions

### `.junie/BACKGROUND.md` — **READ FIRST**

Contains the user's profile: role (Java Senior Developer), experience, domain knowledge, and calibrated skill levels table. This file is the single source of truth for who you are teaching. Read it before LEARNING_STYLE.md to understand the context.

### `.junie/LEARNING_STYLE.md` — **MUST READ SECOND**

Contains strict rules for teaching. Read this file directly for full rule details — do not summarize them here to avoid duplication.

### `.junie/SYLLABUS.md` — Course Plan

Full course structure (Modules 0-6). Read directly for lesson topics and order.

### `.junie/PROGRESS.md` — Progress Tracker

Current status of each lesson/exercise. **Start with the "Current Position" section** at the top — it contains the exact checkpoint (last theory part delivered, next step, key decisions) so you can resume from where the previous session stopped.

### `.junie/lessons/` — Lesson Theory Files

Each lesson has a Markdown file containing the complete theory delivered during the session. Files are updated if the lesson content evolves.

### `.junie/lessons/protocols/` — Learning History

Protocol files record the learning journey for each lesson:
- **Theory Questions**: Questions asked during theory (reveal conceptual gaps or deep understanding)
- **Exercise Weak Spots**: Where the user struggled, what required multiple attempts
- **Refactoring Requests**: What was refactored and why (OOP → FP)
- **Key Insights**: "Aha moments" and particularly good questions
- **Calibration Notes**: What to emphasize/simplify/accelerate in future lessons

**These protocols are the basis for calibrating future lessons.** Before starting a new lesson, read the previous lesson's protocol.

### `.junie/plans/` — Session Plans

Historical artifacts from Junie's internal task tracking. Not needed for continuing the course.

**Note:** The file `scala-learning-course-setup.md` was the foundation that initiated this entire project — it defined the initial course structure, created LEARNING_STYLE, SYLLABUS, and PROGRESS files. Keeping it may be useful for future reference if the course needs to be rebuilt or restructured from scratch.

---

## How to Continue a Session

When starting a new session, follow these steps:

1. **Read `.junie/BACKGROUND.md`** — Understand who you are teaching (Java Senior, experience, skill levels).
2. **Read `.junie/LEARNING_STYLE.md`** — Understand the strict rules before doing anything.
3. **Read `.junie/PROGRESS.md` "Current Position" section** — This is your resume checkpoint. It tells you the exact last position (theory part delivered, next step, key decisions) so you can continue from where the previous session stopped.
4. **Read `.junie/lessons/protocols/`** — Review recent protocols to understand user's learning patterns, weak spots, and strengths.
5. **Check `.junie/SYLLABUS.md`** — Know the full course structure and where we are.
6. **Resume from checkpoint** — Follow the "Next step" from Current Position. If a lesson is `🔄 In progress`, continue from the checkpoint. If the last lesson is `✅ Completed`, proceed to the next lesson in the syllabus.

