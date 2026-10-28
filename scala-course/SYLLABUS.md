# Scala Learning Course — Syllabus

## Pet Project: B2B Trade Finance & Digital Wallet Platform

A dual-language learning project combining PAM (Player Account Management) and Trade Finance:
- **Java Side** — Core PAM microservice (registration, auth, sessions) — AI-supervised coding
- **Scala Side** — Transaction, Wallet & Settlement engine — deep language learning through refactoring

## Tech Stack

- **Scala**: 2.13 (`implicit`, `sealed trait` + `object` — more learning resources available). Lesson 2.6 (end of Module 2) moves the project to Scala 3 syntax (`given`/`using`, `enum`)
- **Scala Framework**: http4s (Typelevel ecosystem — Cats Effect / FS2)
- **Reactive Library**: FS2 (Functional Streams for Scala) — skip ZIO to keep focus sharp
- **Communication**: REST, gRPC, WebSockets
- **Messaging**: Apache Kafka
- **Storage**: Cassandra (immutable ledger), OpenSearch (analytics), Valkey/Sentinel (caching)
- **Build Tool**: SBT (configured in IntelliJ IDEA)
- **IDE**: IntelliJ IDEA EAP — may expire, requiring migration to another tool (Cursor, VS Code, ChatGPT/Claude)

## Course Goals

1. Learn Scala from scratch through refactoring, not Hello World
2. Understand functional types (type classes, higher-kinded types, monads) through Java analogies
3. Build a Transaction, Wallet & Settlement engine in Scala using http4s and FS2
4. Build a PAM microservice in Java with AI-supervised coding
5. Learn Kafka architecture and production configurations
6. Become confident with SBT and IntelliJ IDEA Scala features

---

## Module 0: Functional Types Primer (Java-first)

**Goal**: Demystify functional types before writing any Scala code.

### Lessons

- **Lesson 0.1: Type Classes** — What is a "type class"? Explained through Java `Comparable`, `Serializable`, and Strategy pattern.
- **Lesson 0.2: Higher-Kinded Types** — `F[_]` demystified as "a container type with a hole" — like `List<?>`, `Option<?>`, `Future<?>` in Java.
- **Lesson 0.3: For-Comprehensions Decoded** — It's NOT a loop. It's syntactic sugar for `flatMap` + `map` chains. Compare to Java's `Optional.flatMap()` or `Flux.flatMap()`.
- **Lesson 0.4: Monads Explained** — You already know them: `Optional`, `CompletableFuture`, `Mono`, `Flux` are all monads. Scala just makes this explicit and uniform.
- **Exercise 0**: Refactor a Java `Optional` chain into Scala `Option` with for-comprehension.

**Note**: No Scala code until you understand functional types conceptually through Java equivalents.

---

## Module 1: Scala Syntax Through Refactoring

**Goal**: Learn Scala syntax by refactoring realistic Java code, not Hello World.

### Lessons

- **Lesson 1.1: Variables, Types, Control Structures** — Refactor a Java DTO/validator. Learn `val`/`var`, type inference, and control expressions.
- **Lesson 1.2: Case Classes, Pattern Matching** — Refactor a Java enum/switch hierarchy. Learn case classes, extractors, and deconstruction.
- **Lesson 1.3: Collections, Immutability** — Refactor a Java collection-based utility. Learn immutable collections and functional operations.
- **Lesson 1.4: Functions, Higher-Order Functions** — Refactor a Java Stream pipeline. Learn function literals, currying, and higher-order functions.
- **Exercise 1**: Refactor a Java TransactionValidator into idiomatic Scala.

---

## Module 2: Functional Programming Foundations

**Goal**: Deep dive into FP concepts using Scala, building on Module 0 knowledge.

### Lessons

- **Lesson 2.1: Immutability Deep Dive** — Refactor mutable state to immutable designs. Learn why and how.
- **Lesson 2.2: Errors as Values** — `Either`, error ADTs, `Either` in for-comprehensions. Builds on Module 0 (`Future` moves to Module 3 together with Cats Effect).
- **Lesson 2.3: For-Comprehensions in Practice** — Refactor nested calls across `Option`, `Either` and `List`. Now you understand it's not a loop.
- **Lesson 2.4: Testing (MUnit)** — Tests for pure functions and domain rules.
- **Lesson 2.5: Type System Basics** — Generics vs type parameters, variance, bounds, and Scala's `Any` / `AnyVal` / `AnyRef` hierarchy.
- **Lesson 2.6: Scala 3** — `given`/`using`, `enum`, extension methods; migrate `build.sbt`.
- **Exercise 2**: Refactor a Java service with `Optional` and exceptions into Scala with `Either`, for-comprehensions and MUnit tests.

---

## Module 3: Reactive Scala

**Goal**: Bridge your Reactor/WebFlux experience to Scala's reactive ecosystem.

### Lessons

- **Lesson 3.1: Cats Effect** — `IO`, `Resource`, `Ref`, error handling. FS2 builds on it.
- **Lesson 3.2: Bridging Reactor to Scala** — Compare Flux/Mono to Scala's `Future`/`Promise`, `IO` and FS2 equivalents.
- **Lesson 3.3: FS2 Introduction** — Functional Streams for Scala. Core concepts, syntax, and operators.
- **Lesson 3.4: Backpressure in Practice** — Implement and understand backpressure strategies in FS2.
- **Lesson 3.5: Kafka Integration** — Event-driven architecture with reactive streams, production configs.
- **Exercise 3**: Build a reactive transaction event pipeline with FS2 and Kafka.

---

## Module 4: Build Tool & IDE Mastery

**Goal**: Become confident with SBT and IntelliJ IDEA Scala features.

### Lessons

- **Lesson 4.1: SBT Deep Dive** — Multi-module projects, plugins, custom tasks, build configuration.
- **Lesson 4.2: IntelliJ IDEA Scala Features** — Worksheets, structural refactoring, debugger, plugins.
- **Lesson 4.3: Portability** — Keeping everything tool-agnostic for future migration.
- **Exercise 4**: Set up a multi-module SBT project and use IDEA Scala features.

---

## Module 5: Pet Project — Transaction Engine (Scala Side)

**Goal**: Build the core Transaction, Wallet & Settlement engine in Scala.

### Lessons

- **Lesson 5.1: http4s Basics** — Routing, middleware, and structure.
- **Lesson 5.2: JSON (circe) and HTTP Error Responses** — Encoding and decoding domain types, mapping domain errors to HTTP responses.
- **Lesson 5.3: OpenSpec Contract-First Design** — Define APIs first, implement later.
- **Lesson 5.4: gRPC + WebSockets** — Real-time balance updates and transaction events.
- **Lesson 5.5: Kafka for Event-Driven Architecture** — Streaming financial events and wallet state updates.
- **Lesson 5.6: Cassandra for Immutable Ledger** — Schema design, writes, and reads for audit logs.
- **Lesson 5.7: Valkey (Sentinel) for Caching** — Fast balance checkouts and session caching.
- **Exercise 5A**: Implement the deposit/withdrawal flow over http4s with an in-memory ledger.
- **Exercise 5B**: Replace the in-memory parts with Kafka, Cassandra and Valkey.

---

## Module 6: Pet Project — PAM Service (Java Side, AI Supervision)

**Goal**: Build the Core PAM microservice in Java, practicing AI-supervised development.

### Lessons

- **Lesson 6.1: Core PAM Scaffolding** — Project structure, dependencies, and configuration.
- **Lesson 6.2: AI Supervision Practice** — Prompting, reviewing, and managing AI-generated code.
- **Lesson 6.3: Java vs Scala Comparison** — Compare approaches for the same problem in both languages.
- **Exercise 6**: Build player registration and auth flow, comparing to Scala equivalents.

---

## Course Completion Criteria

- All exercises completed and reviewed.
- Transaction engine running with all infrastructure components.
- PAM service running and integrated with transaction engine.
- Confidence in Scala syntax, FP concepts, and reactive programming.
