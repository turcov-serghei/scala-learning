# Lesson 0.4 Protocol — Monads Explained

**Date:** 2026-10-12

---

## Theory Questions

- **Segment 1 (What problem):** User confirmed understanding — Monad unifies Option, List, Either
- **Segment 2 (Core concept):** User asked "how to call pure?" — explained Option(x), List(x) are already pure. Monad[F].pure(x) provides unified naming.
- **Segment 2 follow-up:** User discovered `process(Some(42))` doesn't compile — explained `Some[Int]` vs `Option[Int]` type difference. Also explained context bound (`F[_]: Monad`) vs explicit implicit parameter (`implicit ev: Monad[F]`).
- **Segment 2 follow-up:** User questioned "flatMap объединяет все List в один" — clarified that flatMap first creates nested lists via map, then flattens them.
- **Segment 3 (Why does it exist):** User understood the problem of writing separate code for each container type.

---

## Segment 4 (Monad laws) — Delivered with practice

- User ran all 3 Monad law examples in code (Left Identity, Right Identity, Associativity)
- User asked: "что мне дают эти законы?" — explained practical value: for-comprehension predictability, refactoring safety, writing generic functions, Java already relies on these laws
- User asked: "вы в упражнении коснетесь этого закона на практике?" — confirmed yes (micro-exercise 6-7)

---

## Segment 5 (F[_] connection) — Delivered with questions

- **Main question:** "можно тоже примерчик чтобы погонять?" — provided `sequence` function examples with Option, List, Either
- **Follow-up question:** "как можно собрать включая None или Left?" — explained `partitionMap` vs `sequence`
- **Major confusion: `partitionMap` compilation error.** Initial code `items.partitionMap(identity)` did not compile for `List[Option[Int]]` because `partitionMap` expects `Either`, not `Option`. Fixed with `_.toLeft(())`.
- **Major confusion: `toLeft`/`toRight` semantics.** AI initially gave wrong explanation. User correctly identified:
  - `toLeft(default)`: `Some(x)` → `Left(x)`, `None` → `Right(default)`
  - `toRight(default)`: `Some(x)` → `Right(x)`, `None` → `Left(default)`
  - AI had it reversed. User corrected: `toLeft` puts value in Left, `toRight` puts value in Right.
- **Confusion: Tuple vs Either.** User confused tuples `(A, B)` with Either. Explained:
  - Tuple `(A, B)` = container for returning multiple values (like Java `Pair`, `Tuple2`)
  - Either[L, R] = union type (either Left OR Right)
  - `partitionMap` takes function returning Either, returns tuple of two lists
- **Confusion: Pattern matching on tuples.** `val (a, b) = tuple` unpacks tuple by position. Java has no equivalent.
- **Question:** "можно ли вернуть 3+ компонентный кортеж?" — `partitionMap` is binary (2-way split). For 3+ ways, use `groupBy` or `foldLeft` with tuple accumulator.
- **Blocker:** AI tried to use `sealed trait` and `match` examples — user has NOT learned these yet. Stopped to avoid teaching untaught concepts.

---

## Progress Tracking Bug (Fixed)

- **Bug:** Previous session recorded "Segments 4-8: Delivered without questions" — this was FALSE. Segments were NOT delivered in that session.
- **Root cause:** AI auto-updated progress without user confirmation, creating contradiction between PROGRESS.md Current Position and protocol.
- **Fix:** Added [CRITICAL] Progress Confirmation Rule to LEARNING_STYLE.md. All progress changes now require explicit "согласен" from user.
- **Lesson learned:** Protocol must reflect REAL progress, not assumed progress.

---

## Exercise Weak Spots

- **Micro-Exercise 1-3 (foldLeft basics):** No issues — user implemented sumNumbers, productNumbers, toUpperCaseList correctly on first try
- **Micro-Exercise 4 (Option[List[A]] aggregation):** Implemented correctly — `acc.flatMap(list => item.map(i => list :+ i))` — the "кошмар" from Lesson 0.3 is now understood
- **Micro-Exercise 5 (for-comprehension in foldLeft):** Implemented correctly — user replaced `items` parameter name with `item` in for-comprehension to avoid confusion
- **Micro-Exercise 6-7 (Monad laws):** Implemented correctly — Left Identity and Right Identity both verified
- **All 7 micro-exercises passed** on first run, no compilation errors or assertion failures

---

## Refactoring Requests

*None during micro-exercises*

---

## Key Insights

- User values precise terminology — "объединяет" was imprecise, "сплющивает" (flatten) is correct
- User experiments with code actively — created `process2` with explicit implicit parameter to understand the difference
- User catches type subtleties quickly — `Some[Int]` vs `Option[Int]` was discovered through experimentation

---

## Calibration Notes

- **foldLeft is CRITICAL weak spot** from Lesson 0.3 — user described it as "кошмар" and "очень грузная структура"
- **UI Bug:** User explicitly requested to stop using `ask_user` menus — they cause loop behavior. Deliver theory as continuous text.
- **No waiting for confirmation:** User wants continuous delivery, not stop-and-wait after each segment
- User values understanding the *generated code* and *mechanism*, not just syntax
- User prefers deep dives with extended discussion on each segment

---

## Segment 6 (foldLeft refresh) — Delivered with questions

- **Question:** "как foldLeft заменяется for-comprehension?" — User correctly identified that theory was imprecise. foldLeft is NOT replaced by for-comprehension. Only the **body** of foldLeft (flatMap + map) is replaced. foldLeft itself remains.
- **Theory fix applied:** Updated `lesson0_4_theory.md` segment 6 to clarify: "foldLeft не заменяется for-comprehension. Заменяется только **тело** foldLeft." Added Java analog showing reduce remains the same, only the body changes.
- **Question:** Type annotation difference — User writes `foldLeft(Some(List.empty[A]): Option[List[A]])` (explicit type annotation) while theory uses `foldLeft(Some(List.empty[A]))` (inferred). Both are correct; explicit annotation is more verbose but helps with generic types.

---

## Segment 7 (Common mistakes) — Delivered

- Delivered 3 common mistakes about Monads: (1) "Monad is complicated" — No, Java's Optional is a Monad, (2) "Need to memorize Monad laws" — Understand intuitively, don't memorize, (3) "foldLeft is hard" — It's just a loop with an accumulator like Java Stream.reduce()
- No questions or confusion points from user on this segment

---

*Protocol updated during theory delivery, segment 7 confirmed.*

---

## Segment 8 (Connection to exercise) — Delivered

- Delivered overview of what the follow-up exercises will cover: foldLeft warm-up, Java Stream.reduce() comparison, Option[List[A]] with visual diagrams, Monad pattern across Option/List/Either
- No questions or confusion points from user on this segment

---

*Protocol updated during theory delivery, segment 8 confirmed.*

---

## Segment 9 (Summary) — Delivered

- Delivered final summary: Monad = container with flatMap and pure, Java already uses Monads (Optional, CompletableFuture), Scala unifies via F[_] and type classes, foldLeft is a loop with accumulator, for-comprehension makes foldLeft body readable
- Theory completed: all 9 segments delivered
- No questions or confusion points from user on this segment

---

*Protocol updated after theory completion, all 9 segments confirmed.*

---

## Main Exercise (Exercise 0.4) — Created

**Date:** 2026-10-13
**Status:** Skeleton created (Level 1) — awaiting user implementation
**File:** `src/main/scala/lesson0_4_exercise.scala`
**Level:** 1 (skeleton — function signatures with `???`, comments-only `main`)

### Exercise Design Decisions

- **Domain:** Wallet Settlement Aggregator (B2B Trade Finance)
  - `List[Either[SettlementError, Int]]` → `Either[SettlementError, List[Int]]`
  - fail-fast semantics via flatMap on Left
- **Integration of prior lessons:**
  - Lesson 0.1: implicit `SettlementConfig` for threshold validation
  - Lesson 0.2: F[_] Monad operations (flatMap, map, pure=Right)
  - Lesson 0.3: foldLeft body pattern
  - Lesson 0.4: Either as Monad, Monad laws
- **foldLeft visual diagram:** Step-by-step comment for 3-step trace (Right, Right, Left)
- **Optional for-comp version:** `aggregateCreditsForComp` for body rewrite practice
- **Monad laws:** Left Identity + Right Identity verification functions

### Calibration Applied

- Level 1 chosen by user (self-described as "try Level 1, switch if too hard")
- No `ask_user` tool used (per calibration note — UI bug with menus)
- `BigDecimal` avoided (using Int — cents) per Rule 17
- Domain classes isolated inside object to prevent compilation conflicts
- sbt compile verified: `[success]`

---

## Micro-Exercises Completion

- **All 7 micro-exercises completed and passed** on first run (2026-10-13)
- **foldLeft "кошмар" resolved:** User now implements `Option[List[A]]` aggregation confidently
- **For-comprehension in foldLeft body:** User correctly applied for-comprehension to replace flatMap + map in foldLeft body
---

## Self-Check Discussion (2026-10-21)

**Q1:** Что такое monad (две операции)? → Пользователь ответил: pure и flatMap — создание контейнера и его преобразование. Верно.
**Result:** Пройден.

**Q2:** Три закона monad → Пользователь не помнил. Уточнено: Left Identity, Right Identity, Associativity. Объяснено зачем нужны (безопасный рефакторинг).
**Result:** Пройден с уточнением.

**Q3:** Почему foldLeft — не "кошмар", а цикл с аккумулятором? → Пользователь ответил: сложно записываемая структура, for-comprehension удобнее. Уточнено: foldLeft = цикл с аккумулятором.
**Result:** Пройден с уточнением.

**Q4:** Что произойдёт с аккумулятором Option[List[A]], как только он станет None? → Пользователь ответил: foldLeft остановится и вернёт None. Верно (None.flatMap = None, результат остаётся None).
**Result:** Пройден.

*Protocol updated on 2026-10-21.*