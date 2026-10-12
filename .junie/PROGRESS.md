# Scala Learning Course — Progress Tracker

## Current Position

**Active lesson:** Lesson 0.4 — Monads Explained (Theory in progress)
**Last position:** Theory segment 3 confirmed (Why does it exist?)
**Next step:** Deliver segment 4 (Monad laws)
**Notes:**
- Resuming from Lesson 0.3 completion
- foldLeft is CRITICAL weak spot from protocol — theory starts with foldLeft warm-up
- Cats library (cats-core 2.13.0) added to build.sbt for practicing Monad[F].pure(x)
- User question: "how to call pure?" — explained Option(x), List(x) are already pure in practice
- User question: "explain process[F[_]: Monad] code" — explained F[_], context bound, implicitly, F.flatMap, F.pure with 3 concrete examples

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
| 🔄 | Lesson 0.4: Monads Explained | 2026-10-12 | — | |
| ⬜ | Exercise 0: Optional → Option refactoring | — | — | |

---

## Module 1: Scala Syntax Through Refactoring

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 1.1: Variables, Types, Control Structures | — | — | |
| ⬜ | Lesson 1.2: Case Classes, Pattern Matching | — | — | |
| ⬜ | Lesson 1.3: Collections, Immutability | — | — | |
| ⬜ | Lesson 1.4: Functions, Higher-Order Functions | — | — | |
| ⬜ | Exercise 1: TransactionValidator refactoring | — | — | |

---

## Module 2: Functional Programming Foundations

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 2.1: Immutability Deep Dive | — | — | |
| ⬜ | Lesson 2.2: Monads in Scala | — | — | |
| ⬜ | Lesson 2.3: For-Comprehensions in Practice | — | — | |
| ⬜ | Lesson 2.4: Type System Basics | — | — | |
| ⬜ | Exercise 2: Nested Optionals refactoring | — | — | |

---

## Module 3: Reactive Scala

| Status | Item | Date Started | Date Completed | Notes |
|--------|------|--------------|----------------|-------|
| ⬜ | Lesson 3.1: Bridging Reactor to Scala | — | — | |
| ⬜ | Lesson 3.2: FS2 Introduction | — | — | |
| ⬜ | Lesson 3.3: Backpressure in Practice | — | — | |
| ⬜ | Lesson 3.4: Kafka Integration | — | — | |
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
| ⬜ | Lesson 5.1: Kilo Framework Basics | — | — | |
| ⬜ | Lesson 5.2: OpenSpec Contract-First Design | — | — | |
| ⬜ | Lesson 5.3: gRPC + WebSockets | — | — | |
| ⬜ | Lesson 5.4: Kafka for Event-Driven Architecture | — | — | |
| ⬜ | Lesson 5.5: Cassandra for Immutable Ledger | — | — | |
| ⬜ | Lesson 5.6: Valkey (Sentinel) for Caching | — | — | |
| ⬜ | Exercise 5: Deposit/withdrawal flow | — | — | |

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
