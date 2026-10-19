# Scala Learning Course — B2B Trade Finance & Digital Wallet Platform

A dual-language learning project for a Java Senior Developer transitioning to Scala.

## Scala Side — Transaction, Wallet & Settlement Engine
- **Framework**: Kilo (Typelevel ecosystem — Cats Effect / FS2)
- **Reactive Library**: FS2 (Functional Streams for Scala)
- **Build Tool**: SBT
- **IDE**: IntelliJ IDEA (EAP)

## Java Side — Core PAM Microservice (Player Account Management)
- Registration, auth, sessions
- AI-supervised coding practice

## Course Structure

All course materials are in the `scala-course/` directory:

| File | Purpose |
|------|---------|
| `scala-course/LEARNING_STYLE.md` | Teaching rules and learning preferences |
| `scala-course/SYLLABUS.md` | Full course plan (Modules 0–6) |
| `scala-course/PROGRESS.md` | Progress tracker |
| `scala-course/CHECKPOINT.md` | Session checkpoint for context restoration |
| `scala-course/BACKGROUND.md` | User profile (read first) |
| `scala-course/lessons/` | Lesson theory files |
| `scala-course/lessons/protocols/` | Learning history per lesson |
| `scala-course/templates/` | Exercise code templates (Level 1/2/3) |

## Modules

- **Module 0**: Functional Types Primer (Java-first) — type classes, HKT, for-comprehensions, monads
- **Module 1**: Scala Syntax Through Refactoring
- **Module 2**: Functional Programming Foundations
- **Module 3**: Reactive Scala (FS2, Kafka, backpressure)
- **Module 4**: Build Tool & IDE Mastery (SBT, IntelliJ)
- **Module 5**: Pet Project — Transaction Engine (Scala side)
- **Module 6**: Pet Project — PAM Service (Java side, AI supervision)

## Tech Stack

- **Scala**: 2.13 (implicit, sealed trait + object)
- **Build**: SBT
- **Reactive**: FS2 (skip ZIO)
- **Messaging**: Apache Kafka
- **Storage**: Cassandra (immutable ledger), OpenSearch (analytics), Valkey/Sentinel (caching)
- **Communication**: REST, gRPC, WebSockets

## Agent Instructions

AI agents working in this project should read `AGENTS.md` and `scala-course/LEARNING_STYLE.md` before any work.
