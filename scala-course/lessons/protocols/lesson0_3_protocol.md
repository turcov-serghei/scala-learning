# Lesson 0.3 Protocol — For-Comprehensions Decoded

**Date:** 2026-10-08

---

## Theory Questions

### Question 1: Always flatMap N times, then map once?
**Q:** In for-comprehension, do we always have flatMap N times followed by 1 map at the end?
**A:** Yes. Every generator except the last becomes `flatMap`. The last generator becomes `map`. The `yield` clause is the body of that final `map`. With 1 generator, it's just `map`. With N generators, it's N-1 `flatMap`s + 1 `map`.

**Insight:** User showed strong understanding by noticing the pattern and asking about the structural rule.

---

### Question 2: `flatMap.flatMap` vs `flatMap.map`
**Q:** My manual implementation uses `flatMap.flatMap`, but for-comprehension uses `flatMap.map`. Is my version worse?
**A:** Not worse, just different. In manual code, if the lambda returns `Option[T]`, you must use `flatMap`. In for-comprehension, `yield` returns a bare value, so the compiler uses `map`. Both produce the same result. For-comprehension is more idiomatic because `yield` naturally returns bare values.

**Insight:** User implemented the same logic manually before seeing for-comprehension syntax, demonstrating independent problem-solving.

---

### Question 3: Guards — can a generator follow an `if` guard?
**Q:** After an `if` guard in for-comprehension, can there be another `<-` generator?
**A:** Yes. The guard wraps everything after it in an `if/else` block. If the condition is false, the result is `None` (for Option). If true, the chain continues with subsequent generators.

**Insight:** User questioned whether guards terminate the chain — important conceptual clarification.

---

### Question 4: Guard syntax — where is the `else` block?
**Q:** In the guard example, I only see `if`, no `else`. Is this valid? Where is the code inside `if`? Is it syntactic sugar?
**A:** It's syntactic sugar. The compiler generates the `else` block automatically: for `Option`, it's `None`; for `List`, it's `List()`. You cannot customize what's in `else` — it's hardcoded by the container type. All code after the guard falls inside the `if` block.

**Insight:** User wanted to understand the generated code structure, not just the surface syntax.

---

### Question 5: Can ordinary `if-else` exist inside for-comprehension?
**Q:** Can there be a plain `if-else` inside for-comprehension?
**A:** Yes, but with `=` (assignment), not as a guard. Example: `status = if (balance > 0) "active" else "inactive"`. This is a plain Scala expression, not a filter. Guards use `if` without `=` and act as filters.

**Insight:** User distinguished between guard syntax and ordinary conditional expressions — important nuance.

---

### Question 6: What can be in the `else` block of a guard?
**Q:** Can something other than `None` be in the `else` block of a for-comprehension guard?
**A:** No. The `else` value is determined by the container type: `Option` → `None`, `List` → `List()`, `Either` → `Left(error)`. You cannot write "if guard fails, return `Some(0)`" — guards are pure filters.

**Insight:** User probed the limits of guard customization, showing deep curiosity about the mechanism.

---

## Exercise Weak Spots

1. **`printCurrencies` — итерация по List**: Изначально пользователь использовал `foldLeft` для итерации по List внутри for-comprehension с `.get` на Option — потенциальный краш. Затем создал `printCurrenciesSimpler` с `currency <- wallets.map(_.currency)` (flatMap на List = итерация) и `println(currency)` — безопасный и идиоматичный вариант. Оба варианта работают, но упрощённая версия предпочтительнее.

2. **Пробел в теории**: Пользователь думал что `<-` в for-comprehension только извлекает значение из `Option`. Он не знал что `<-` на `List` работает как итерация (цикл). Теория Lesson 0.3 не покрыла это различие — добавлен раздел 8 в `lesson0_3_theory.md`.

3. **Понимание `println` вызовов**: Пользователь спросил почему вывод `USD` и `EUR` оказался в отдельных строках, а не в одну линию. Уточнил что `println(currency)` выполнился 2 раза (по одному на каждую итерацию), а не 1 раз со всеми валютами. Это подтверждает понимание что `<-` на List — это цикл с N итерациями.

4. **`foldLeft` — очень грузная структура** (Additional exercise): Пользователь описал foldLeft как "кошмар" и "очень грузную структуру". Конкретно: после `currencies.map(currency => processTransaction(accountOpt, currency))` нужно положить все результаты `processTransaction` в один лист, но структура foldLeft с `flatMap/map` выглядит сложной. **Это основной затык** — требует дополнительного закрепления.

5. **For-comprehension на List в processBatch** (Additional exercise): Пользователь не понимает как "воткнуть" for-comprehension с `<-` на List для итерации по multiple wallets (строка 17 задания). Вопрос: можно ли использовать for-comprehension вместо foldLeft в processBatch? Ответ: да, но задача требует foldLeft (интеграция с уроком 0.2). For-comprehension не поддерживает агрегацию `List[Option[A]]` → `Option[List[A]]` напрямую — нужен foldLeft или sequence pattern.

6. **Implicit case class vs Type Classes** (Additional exercise): Пользователь использовал `implicit case class` вместо type classes. Сказал: "думаю было бы лучше с type class - как вы запрашивали". Оценка: для этого задания implicit case class — правильное решение. Type classes нужны когда нужно добавлять поведение к типу, который вы не контролируете. Здесь TransactionConfig — это просто конфигурация (DI), а не type class. Пользователь правильно интуитивно выбрал подходящий инструмент, но сомневается из-за того что "запрашивали type class".

## Refactoring Requests

1. **`printCurrencies` — `.get` на Option**: Пользователь самостоятельно создал `printCurrenciesSimpler` без `.get`, используя `<-` на List для итерации. Рефакторинг выполнен самостоятельно.

## Follow-up Exercises Created

**Date:** 2026-10-08
**Level:** 1 (minimal code — only case classes and task descriptions)
**File:** `src/main/scala/lesson0_3_followup_exercises.scala`

**4 micro-exercises covering all discussed nuances:**

1. **`<-` vs `=`** — `accountSummary`: for-comprehension с `<-` для Option и `=` для промежуточных вычислений.
2. **Guards с генераторами после** — `getLoyaltyBonus`: guard (`if`) с последующим `=` вычислением bonus.
3. **`yield` vs без `yield`** — `getCurrencies` (декларативная, возвращает Option) и `printCurrencies` (побочный эффект, Unit).
4. **`flatMap.flatMap` vs `flatMap.map`** — `getBalanceManual` (ручная цепочка) и `getBalanceForComp` (for-comprehension), сравнение читаемости.

**Design decisions based on protocols:**
- Level 1 chosen per user request — maximum independence, user writes everything from scratch.
- Case classes (`Wallet`, `Account`, `Bonus`) provided as shared context.
- Test cases in `main` are commented out — user uncomment after implementation.
- Each exercise targets a specific question from the theory discussion (see Theory Questions above).

## Additional Exercise Results

**Date:** 2026-10-09
**File:** `src/main/scala/lesson0_3_exercise_additional.scala`
**Status:** ✅ All 7 AC passed. Code compiles and runs successfully.

**Implementation notes:**
- `processTransaction`: For-comprehension с implicit config — реализовано правильно. Все 6 строк for-comprehension работают корректно (account, wallet, balance, guard, bonus rate, yield).
- `processBatch`: `map` + `foldLeft` с `flatMap/map` — работает, но пользователь описал структуру как "кошмар". Реализация корректна, но вызывает трудности с пониманием.

**User's self-assessment:** "мне было сложно", "я все время путаюсь с foldLeft - очень грузная структура", "неужели такая сложная сборка"

**Key sticking points recorded:**
1. foldLeft — основной затык, требует дополнительного закрепления
2. For-comprehension на List в processBatch — непонятно как применить
3. Implicit case class vs Type Classes — интуитивно выбрал правильный инструмент, но сомневается

---

## Key Insights

1. **User implemented for-comprehension logic manually** before seeing the syntax. This shows strong analytical skills — the user didn't just accept the syntax but verified it by building the equivalent `flatMap`/`map` chain independently.

2. **User questioned every structural assumption** — from "always flatMap then map" to "can guards be followed by generators" to "what's in the else block." This pattern of questioning reveals a learner who wants to understand the *mechanism*, not just the *syntax*.

3. **User noticed the `flatMap.flatMap` vs `flatMap.map` difference** and asked whether their approach was worse. This shows attention to detail and concern about idiomatic code — a senior developer trait.

4. **User was concerned about Java's flatMap/map approach being "not so bad"** — this is a valid observation. For-comprehension shines at 3-4 levels of nesting. For 2 levels, Java's approach is comparable. Future lessons should acknowledge this nuance.

5. **User self-corrected `printCurrencies`** by creating `printCurrenciesSimpler` with `<-` on List for iteration. Instead of waiting for guidance, the user independently discovered the idiomatic solution — demonstrating strong learning autonomy.

6. **User chose implicit case class over type classes** for TransactionConfig — intuitively correct choice. Type classes are for adding behavior to types you don't control; TransactionConfig is simple DI configuration. User's intuition was right, but self-doubt shows they need confidence in choosing the right tool for the job.

## Calibration Notes

- **Pacing:** User prefers deep dives into each question before proceeding. The "one screen per segment" rule works well, but be prepared for extended discussions on each segment.
- **Depth:** User wants to understand the *generated code*, not just the surface syntax. When explaining for-comprehension, always show the desugared `flatMap`/`map` version.
- **Guards:** User found guards particularly interesting — the follow-up exercises should include multiple guard scenarios (guards followed by generators, multiple guards, guards with `if-else` assignments).
- **`<->` vs `=`:** This distinction emerged as a common mistake in the theory, but user didn't struggle with it conceptually. A quick micro-exercise should suffice.
- **Future lessons:** When introducing new syntactic sugar (e.g., pattern matching, implicit conversions), always show the desugared version first, as user values understanding the underlying mechanism.
- **foldLeft is a major sticking point** (CRITICAL): User described foldLeft as "кошмар" and "очень грузная структура". This is the #1 weak spot discovered in additional exercise. Lesson 0.4 (Monads) and future lessons MUST include:
  1. Multiple foldLeft warm-up exercises before combining with other concepts
  2. Visual diagrams showing the accumulator flow step-by-step
  3. Java equivalent (Stream.reduce()) comparison to anchor understanding
  4. Practice with simpler aggregations before complex ones like `List[Option[A]]` → `Option[List[A]]`
- **Self-doubt about tool choice**: User correctly chose implicit case class over type classes but doubted the choice. Future lessons should reinforce confidence by validating when a simpler tool is the right choice, not just teaching the "advanced" pattern.

---

*Protocol created during theory phase. Will be updated after exercises are completed.*
