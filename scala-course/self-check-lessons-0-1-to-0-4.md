# Self-Check — Пройденные уроки (0.1 – 0.4)

> **Архив старого формата.** Правилами больше не используется: вопросы и найденные пробелы self-check теперь записываются в протокол урока (`lessons/protocols/`, раздел Self-Check).

## Вопросы по урокам

### Lesson 0.1 — Type Classes

1. Чем отличается `trait` от Java `interface` по части поведения?
2. Зачем нужен `implicit val` в Scala?
3. Что произойдёт, если в scope окажется два `implicit` одного и того же типа?
4. В Lesson 0.1 мы создали type class `Show[T]` (trait с методом `show`) и функцию `print[T](value: T)(implicit show: Show[T])`. Если в scope уже есть `implicit val transactionShow: Show[Transaction]`, как выглядит вызов `print(myTransaction)`? Нужно ли передавать `Show` явно?
5. В Java есть Comparable<T> (поведение зашито в класс) и Comparator<T> (поведение передаётся отдельно). В Lesson 0.1 мы изучили type class — это интерфейс + экземпляры поведения, которые компилятор подключает автоматически. Чем type class отличается от Comparator? Что делает его первым классом в Scala по сравнению с Java?

**Ссылка:** `scala-course/lessons/lesson0_1_theory.md`

### Lesson 0.2 — Higher-Kinded Types

1. Что такое `F[_]` простыми словами?
2. В Lesson 0.2 мы изучили `F[_]` — контейнер, принимающий один тип. Если `F = Option`, то `F[Int]` = `Option[Int]`. Если `F = List`, то `F[Int]` = `List[Int]`. Что будет, если `F = Future` и `F[Int]`? Приведи конкретный пример типа.
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
