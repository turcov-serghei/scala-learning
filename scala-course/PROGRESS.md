# Scala Learning Course — Progress Tracker

## Current Position

_Last updated: 2026-10-28_

**Active exercise:** Lesson 1.1 follow-ups 1–3 done (follow-up 3: all AC pass, written answer `Any` accepted after discussion); self-check in progress
**Last position:** Lesson 1.1: theory 1–7 covered, follow-ups 1–3 passed. Self-check: Q1 passed; Q2 (`Option` vs Java `null` at the boundary) answered and the review was delivered (`Some(null)` does not throw and is the leak; `None.get` throws `NoSuchElementException`). The user will discuss the Q2 review in the next session.
**Next step:** Next session: start with the user's reaction to the Q2 review (answer questions first, do not move on until the user is ready); then 1–2 more self-check questions one at a time, only on taught material; then the main exercise (spec with Technical Requirements, Quality bar in Rule 10). `Any`/`AnyRef`/`AnyVal` hierarchy is scheduled in Lesson 2.5.
**Notes:**
- Monad laws remain difficult for the user; revisit them gradually with concrete examples.
- User explicitly requested an interactive, block-by-block course following `lesson1_1_theory.md`; ask only non-trivial questions about concepts already explained and give feedback before proceeding.
- Java comparisons must acknowledge equivalent Java constructs (e.g. `if` + `return`, ternary); explain semantic differences without presenting Scala as uniquely capable.
- Lesson 1.1 is the first lesson in Module 1; the follow-up exercises start only after the user confirms readiness, the main exercise only after follow-ups and self-check are passed.
- Pending after Lesson 1.1 closes: explain `flatMap` vs `map` on `Option` with `totalDebit` (user request, see the Lesson 1.1 protocol). Do not explain earlier unless the user asks.
- Pending (user postponed): explain value discarding (`Boolean` vs expected `Unit`), see the Lesson 1.1 protocol.
- Previous checkpoint notes: ad-hoc Lesson 0.1 exercises completed; self-checks 0.1-0.4 passed (Lesson 0.4 laws needed clarification).

---

## How to Use This File

- Each lesson and exercise has a status: `⬜` (not started), `🔄` (in progress), `✅` (completed), `❌` (failed/skipped).
- After completing a lesson or exercise, update the status and add notes.
- Use the notes section to record key takeaways, questions, or blockers.
- **Update "Current Position" section** after each significant step (theory delivered, exercise started, exercise completed) so that a new session can resume from the exact checkpoint.

---

## Module 0: Functional Types Primer (Java-first)

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ✅ | Lesson 0.1: Type Classes | 2026-10-07 | 2026-10-07 | Усвоено: trait, implicit resolution, case class без new, List(...), for-цикл |
| ✅ | Lesson 0.2: Higher-Kinded Types | 2026-10-07 | 2026-10-07 | Усвоено: F[_], foldLeft, flatMap/map на Option, функциональный стиль vs Java-style |
| ✅ | Lesson 0.3: For-Comprehensions Decoded | 2026-10-07 | 2026-10-09 | Усвоено: for-comprehension desugaring, guards, `<-` vs `=`, `<-` на List (итерация). Затык: foldLeft агрегация — требует закрепления в уроке 0.4 |
| ✅ | Lesson 0.4: Monads Explained | 2026-10-12 | 2026-10-15 | Теория и упражнения завершены. Main Exercise 0.4 (Level 1) успешно пройден. |
| ✅ | Exercise 0: Optional → Option refactoring | 2026-10-21 | 2026-10-21 | for-comprehension with Option, find, yield. Compiles, tests pass. |

---

## Self-Check Progress

| Status | Lesson | Position | Notes |
|--------|--------|----------|-------|
| ✅ | Lesson 0.1: Type Classes | Q1-Q5 passed | Ad-hoc: trait state + multiple inheritance |
| ✅ | Lesson 0.2: Higher-Kinded Types | Q1-Q4 passed | |
| ✅ | Lesson 0.3: For-Comprehensions | Q1-Q4 passed | |
| ✅ | Lesson 0.4: Monads | Q1-Q4 passed | |

---

## Module 1: Scala Syntax Through Refactoring

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| 🔄 | Lesson 1.1: Variables, Types, Control Structures | 2026-10-22 | — | Theory 1–7 covered; follow-ups, self-check and main exercise pending. Monad laws noted as difficult; revisit gradually. |
| ⬜ | Lesson 1.2: Case Classes, Pattern Matching | — | — | |
| ⬜ | Lesson 1.3: Collections, Immutability | — | — | |
| ⬜ | Lesson 1.4: Functions, Higher-Order Functions | — | — | |
| ⬜ | Exercise 1: TransactionValidator refactoring | — | — | |

---

## Module 2: Functional Programming Foundations

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 2.1: Immutability Deep Dive | — | — | |
| ⬜ | Lesson 2.2: Errors as Values | — | — | |
| ⬜ | Lesson 2.3: For-Comprehensions in Practice | — | — | |
| ⬜ | Lesson 2.4: Testing (MUnit) | — | — | |
| ⬜ | Lesson 2.5: Type System Basics | — | — | |
| ⬜ | Lesson 2.6: Scala 3 | — | — | |
| ⬜ | Exercise 2: Optional/exceptions → Either + MUnit refactoring | — | — | |

---

## Module 3: Reactive Scala

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 3.1: Cats Effect | — | — | |
| ⬜ | Lesson 3.2: Bridging Reactor to Scala | — | — | |
| ⬜ | Lesson 3.3: FS2 Introduction | — | — | |
| ⬜ | Lesson 3.4: Backpressure in Practice | — | — | |
| ⬜ | Lesson 3.5: Kafka Integration | — | — | |
| ⬜ | Exercise 3: Reactive transaction pipeline | — | — | |

---

## Module 4: Build Tool & IDE Mastery

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 4.1: SBT Deep Dive | — | — | |
| ⬜ | Lesson 4.2: IntelliJ IDEA Scala Features | — | — | |
| ⬜ | Lesson 4.3: Portability | — | — | |
| ⬜ | Exercise 4: Multi-module SBT project | — | — | |

---

## Module 5: Pet Project — Transaction Engine (Scala Side)

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 5.1: http4s Basics | — | — | |
| ⬜ | Lesson 5.2: JSON (circe) and HTTP Error Responses | — | — | |
| ⬜ | Lesson 5.3: OpenSpec Contract-First Design | — | — | |
| ⬜ | Lesson 5.4: gRPC + WebSockets | — | — | |
| ⬜ | Lesson 5.5: Kafka for Event-Driven Architecture | — | — | |
| ⬜ | Lesson 5.6: Cassandra for Immutable Ledger | — | — | |
| ⬜ | Lesson 5.7: Valkey (Sentinel) for Caching | — | — | |
| ⬜ | Exercise 5A: Deposit/withdrawal over http4s (in-memory ledger) | — | — | |
| ⬜ | Exercise 5B: Kafka, Cassandra and Valkey | — | — | |

---

## Module 6: Pet Project — PAM Service (Java Side, AI Supervision)

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 6.1: Core PAM Scaffolding | — | — | |
| ⬜ | Lesson 6.2: AI Supervision Practice | — | — | |
| ⬜ | Lesson 6.3: Java vs Scala Comparison | — | — | |
| ⬜ | Exercise 6: Player registration and auth | — | — | |

---

## Key Takeaways

Record important insights, "aha!" moments, and recurring patterns here as you progress through the course.

-

## Blockers & Questions

Record any blockers, unanswered questions, or topics you want to revisit.

-
