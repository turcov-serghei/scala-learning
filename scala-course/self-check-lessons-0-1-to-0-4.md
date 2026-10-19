# Self-Check — Пройденные уроки (0.1 – 0.4)

## Вопросы по урокам

### Lesson 0.1 — Type Classes

1. Чем отличается `trait` от Java `interface` по части поведения?
2. Зачем нужен `implicit val` в Scala?
3. Что произойдёт, если в scope окажется два `implicit` одного и того же типа?
4. Как выглядит вызов `print(myTransaction)` в Scala без явного указания `Show`?
5. Что такое type class простыми словами (через Java Comparable/Comparator)?

**Ссылка:** `scala-course/lessons/lesson0_1_theory.md`

### Lesson 0.2 — Higher-Kinded Types

1. Что такое `F[_]` простыми словами?
2. Приведи примеры: `F = Option`, `F = List`, `F = Future`.
3. Почему в Java нельзя написать одну функцию, работающую с `Optional`, `Mono` и `CompletableFuture` одновременно?
4. Что делает `foldLeft` с `List[Option[A]]`?

**Ссылка:** `scala-course/lessons/lesson0_2_theory.md`

### Lesson 0.3 — For-Comprehensions Decoded

1. For-comprehension — это цикл или `flatMap` + `map`?
2. Что делает `yield`?
3. Чем отличается `<-` на `Option` от `<-` на `List`?
4. Что вернёт `for { a <- None } yield a + 1`?

**Ссылка:** `scala-course/lessons/lesson0_3_theory.md`

### Lesson 0.4 — Monads Explained

1. Что такое monad (две операции)?
2. Какие три закона monad (назови и опиши своими словами)?
3. Почему `foldLeft` — это не "кошмар", а цикл с аккумулятором?
4. Что произойдёт с аккумулятором `Option[List[A]]`, как только он станет `None`?

**Ссылка:** `scala-course/lessons/lesson0_4_theory.md`
