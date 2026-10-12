# Lesson 0.1 Protocol — Type Classes

**Status:** ✅ Completed (theory + two exercises completed)
**Date:** 2026-10-07 (previous session)

---

## Theory Questions

### Question 1: `val` и `Show[T]` — аналог в Java?
**Q:** `implicit val transactionShow: Show[Transaction]` — насколько я понимаю `val` это неизменяемая переменная которая имплементирует `Show[T]` интерфейс — что-то похожее есть в Java?

**A:** Да, `val` — это как `final` в Java. `Show[T]` — это trait (интерфейс), и `transactionShow` — это экземпляр, который его реализует. В Java аналог:

```java
final Show<Transaction> transactionShow = new Show<>() {
    public String show(Transaction value) { ... }
};
```

**Insight:** Пользователь сразу связал Scala `val` с Java `final` и trait с интерфейсом — показывает, что Java аналогии работают эффективно.

---

### Question 2: `implicit` — это compile-time DI или что-то большее?
**Q:** `implicit` — это compile-time dependency injection или что-то большее?

**A:** `implicit` — это **compile-time dependency injection**. Компилятор ищет в текущей области видимости подходящий экземпляр и подставляет его. Нет runtime overhead, нет reflection — всё решается на этапе компиляции.

В Java такого механизма нет — вы всегда передаёте `Comparator` или `Serializer` явно:

```java
transactions.sort(myComparator);  // всегда явно
```

В Scala компилятор находит нужный экземпляр автоматически, поэтому вызывающий код чище.

**Insight:** Пользователь сразу уловил суть `implicit` как DI — без этого понимания type classes кажутся "магией".

---

### Question 3: Что было бы без `implicit`?
**Q:** Если бы не было `implicit` — что бы было?

**A:** Без `implicit` вы передаёте экземпляр `Show` явно, как в Java:

```scala
// Без implicit — явно, как в Java:
def print[T](value: T)(show: Show[T]): Unit =
  println(show.show(value))

// Вызов — нужно передавать экземпляр каждый раз:
print(myTransaction, transactionShow)
```

`implicit` делает передачу экземпляра невидимой на месте вызова, но видимой в сигнатуре метода.

**Insight:** Пользователь хотел понять, что `implicit` — это синтаксический сахар, а не новая концепция. Подтверждение что это просто автоматизация передачи параметров помогло снять страх перед "магией".

---

### Question 4: Разбор сигнатуры `def print[T](value: T)(implicit show: Show[T]): Unit`
**Q:** `def print[T](value: T)(implicit show: Show[T]): Unit =` — много всего в этой строчке — что за что отвечает?

**A:** Поэлементный разбор:

| Часть | Значение | Java аналог |
|-------|----------|-------------|
| `def print` | Метод "print" | `void print` |
| `[T]` | Generic тип | `<T>` |
| `(value: T)` | Первый параметр | `(T value)` |
| `(implicit show: Show[T])` | Второй параметр, компилятор подставляет | `(Show<T> show)` явно |
| `: Unit` | Возвращает ничего | `: void` |

**Два параметр-группы** — `(value: T)` и `(implicit show: Show[T])` — это называется **currying**. Позволяет разделить обычные аргументы от implicit. В Java это один метод с двумя параметрами.

**Insight:** Пользователь хотел разобрать каждую часть сигнатуры — показывает внимание к деталям и желание понять механизм, а не просто синтаксис.

---

## Exercises Completed

### Exercise 1: `lesson0_1.scala` — Sort[T] Type Class

**File:** `src/main/scala/lesson0_1.scala`

**Task:** Реализовать type class `Sort[T]` для сортировки объектов `Person` по возрасту.

**Implementation:**
- Создан trait `Sort[T]` с методом `sort(value1: T, value2: T): Int` — аналог Java `Comparator.compare()`.
- Создан case class `Person(name: String, age: Int)`.
- Создан implicit instance `ageSorter: Sort[Person]`, который сравнивает по полю `age`.
- Реализована функция `sort[T](people: List[T])(implicit sort: Sort[T]): List[T]` — принимает implicit параметр и использует его для сортировки через `Ordering[T]`.

**Key pattern:** Пользователь реализовал type class с нуля, включая implicit instance и функцию, принимающую implicit параметр. Это подтверждает понимание механизма compile-time DI.

---

### Exercise 2: `lesson0_1_extra_exercise.scala` — Format[T] Type Class

**File:** `src/main/scala/lesson0_1_extra_exercise.scala`

**Task:** Реализовать type class `Format[T]` для форматирования объектов `Transaction` в строку.

**Implementation:**
- Создан trait `Format[T]` с методом `format(value: T): String`.
- Создан case class `Transaction(id: String, amount: BigDecimal, status: String)`.
- Создан implicit instance `shortFormat`, который форматирует как `"id:status"`.
- Создан обычный (не implicit) instance `fullFormat` с lambda syntax: `transaction => s"Transaction ${transaction.id}:${transaction.amount}:(${transaction.status})"`.
- Реализована функция `display[T](value: T)(implicit format: Format[T]): Unit` — использует implicit формат для вывода.

**Key pattern:** Пользователь использовал два стиля создания instance:
1. `new Format[Transaction] { ... }` — классический anonymous class стиль.
2. `transaction => s"..."` — lambda стиль (short syntax для single-method traits).

Это показывает понимание, что single-method trait можно имплементировать как lambda (как Java `Runnable` или `Comparator`).

---

## Exercise Weak Spots

*(Слабых мест не выявлено. Оба упражнения выполнены корректно с первого раза.)*

## Refactoring Requests

*(Не применимо — упражнения выполнены без запросов на рефакторинг.)*

## Key Insights

1. **Java аналогии работают.** Пользователь сразу связал type class с `Comparator` и Strategy pattern. Когда type class объясняется через Java, он перестаёт быть "магией" и становится знакомым паттерном.

2. **`implicit` как DI — ключевое понимание.** Пользователь сразу уловил что `implicit` — это compile-time dependency injection. Без этого понимания type classes кажутся непонятными.

3. **Пользователь разбирал сигнатуру по элементам.** Вместо того чтобы принять `def print[T](value: T)(implicit show: Show[T])` как чёрный ящик, пользователь попросил разобрать каждую часть. Это показывает аналитический подход — learner who wants to understand the *mechanism*, not just the *syntax*.

4. **Пользователь сравнивал с Java.** Каждый элемент Scala кода сравнивался с Java аналогом (`val` → `final`, `trait` → `interface`, `implicit` → explicit DI). Этот подход эффективен для Java developer.

5. **Пользователь использовал два стиля имплементации.** В extra exercise пользователь показал, что single-method trait можно имплементировать как lambda (`transaction => s"..."`) и как anonymous class (`new Format[Transaction] { ... }`). Это подтверждает понимание SAM (Single Abstract Method) паттерна, знакомого из Java `Runnable`/`Comparator`.

6. **Пользователь создал два type class с нуля.** Sort[T] и Format[T] — оба реализованы самостоятельно, включая implicit instances и функции с implicit параметрами. Это подтверждает, что концепция type class усвоена на практике, а не только теоретически.

---

## Calibration Notes

### Pacing
- **Теория усвоена быстро.** Пользователь понял type classes за один слайд благодаря Java аналогиям. Future lessons могут использовать тот же подход: Java first, Scala second.
- **Упражнения выполнены без проблем.** Оба упражнения выполнены корректно с первого раза. Тема type classes полностью усвоена.

### Depth
- **Пользователь хочет разбор по элементам.** Сигнатура метода была разобрана по частям. Future lessons должны делать то же самое для сложных сигнатур.
- **Java аналогии обязательны.** Каждый Scala концепт должен иметь Java аналог. Без аналогов пользователь теряет ориентир.

### Что подчеркнуть в будущих уроках
- **`implicit` — это DI.** Это ключевое понимание, которое нужно закреплять в каждом уроке где используется `implicit`.
- **Currying — два параметр-группы.** Пользователь впервые увидел currying в Lesson 0.1. Future lessons должны использовать currying и объяснять его.
- **Lambda syntax для SAM traits.** Пользователь использовал lambda syntax в extra exercise. Future lessons могут использовать этот паттерн без дополнительных объяснений.

### Что ускорить
- **Type classes — понятная тема.** Пользователь быстро усвоил концепцию и успешно выполнил два упражнения. Future lessons могут двигаться быстрее по type classes и фокусироваться на более сложных темах (monads, higher-kinded types).

### Что упростить
- **Сигнатуры методов.** Пользователь попросил разобрать `def print[T](value: T)(implicit show: Show[T])` по элементам. Future lessons должны заранее включать такие разборы для сложных сигнатур.

---

*Protocol updated on 2026-10-08. Two exercises were completed: `lesson0_1.scala` (Sort[T]) and `lesson0_1_extra_exercise.scala` (Format[T]). Both exercises demonstrate solid understanding of type classes, implicit parameters, and SAM trait lambda syntax.*
