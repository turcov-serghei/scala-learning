# Lesson 0.4 — Monads Explained

## 1. What problem are we solving?

Вы уже знаете `Option`, `List`, `Either` — и используете их через `flatMap` + `map` или for-comprehensions. Но что объединяет эти типы? Почему они все поддерживают `flatMap` и `map`? И почему вы не можете написать одну функцию, которая работает с `Option[A]`, `List[A]` и `Either[E, A]` одновременно?

**Ответ:** Они все — **Monads**. И вы уже умеете с ними работать. Этот урок покажет, что "Monad" — это не магия, а просто паттерн, который вы уже используете.

## 2. Core concept: What is a Monad?

**Monad — это тип-контейнер, который поддерживает две операции:**

1. `pure` (или `unit`) — поместить значение в контейнер
2. `flatMap` — применить функцию, возвращающую контейнер, и "сплющить" результат

В Scala это выражено через trait `Monad` (в Cats) или просто через наличие методов `flatMap` и `pure` у типа.

### Java аналог: вы уже знаете Monads

| Java Type | Scala Equivalent | `pure` (создать) | `flatMap` |
|-----------|-----------------|------------------|-----------|
| `Optional<T>` | `Option[T]` | `Optional.of(x)` | `optional.flatMap(f)` |
| `CompletableFuture<T>` | `Future[T]` | `CompletableFuture.completedFuture(x)` | `future.thenCompose(f)` |
| `List<T>` (как контейнер) | `List[T]` | `Collections.singletonList(x)` | `list.flatMap(f)` (Java 9+) |
| Reactor `Mono<T>` | `fs2.FP[F, T]` | `Mono.just(x)` | `mono.flatMap(f)` |

**Вы уже использовали Monads.** `Optional.flatMap()` — это Monad. `CompletableFuture.thenCompose()` — это Monad. Scala просто делает этот паттерн явным и унифицированным.

## 3. Why does it exist?

Без Monad: каждый контейнер требует отдельного кода для композиции.
```java
// Java: разные API для каждого типа
Optional<String> opt = ...;
opt.flatMap(this::parse)    // Optional
    .map(Integer::toString);

CompletableFuture<String> future = ...;
future.thenCompose(this::parse)  // CompletableFuture
     .thenApply(Integer::toString);
```

С Monad (Scala): один паттерн для всех контейнеров.
```scala
def process[F[_]: Monad, A](fa: F[A]): F[String] =
  fa.flatMap(a => pure(a.toString))
// Работает с Option, Future, List, Either — одинаково
```

## 4. Java → Scala: Monad laws

Monad — это не просто "есть flatMap". Есть три закона, которые гарантируют предсказуемое поведение:

### Law 1: Left Identity
```
pure(x).flatMap(f)  ==  f(x)
```
Поместить значение в контейнер и сразу применить функцию — то же самое, что применить функцию напрямую.

**Java аналог:**
```java
Optional.of(5).flatMap(x -> Optional.of(x * 2))
// равно
Optional.of(5 * 2)
```

### Law 2: Right Identity
```
fa.flatMap(pure)  ==  fa
```
Если вы берёте значение из контейнера и сразу кладёте обратно — получаете то же самое.

**Java аналог:**
```java
Optional<Integer> opt = Optional.of(5);
opt.flatMap(Optional::of)
// равно
opt  // Optional.of(5)
```

### Law 3: Associativity
```
fa.flatMap(f).flatMap(g)  ==  fa.flatMap(x => f(x).flatMap(g))
```
Порядок вложенности `flatMap` не важен — результат одинаковый.

**Java аналог:**
```java
Optional<Integer> opt = Optional.of(5);

// Вариант 1: последовательный
opt.flatMap(x -> Optional.of(x * 2))
  .flatMap(y -> Optional.of(y + 1))

// Вариант 2: вложенный
opt.flatMap(x -> Optional.of(x * 2).flatMap(y -> Optional.of(y + 1)))

// Оба дают: Optional.of(11)
```

**Почему это важно?** For-comprehension полагается на эти законы. Когда компилятор превращает for-comprehension в `flatMap`/`map` цепочку, он предполагает что законы выполняются.

## 5. The `F[_]` connection (Higher-Kinded Types)

В уроке 0.2 вы узнали что `F[_]` — это "тип с дыркой". Monad — это `F[_]`, который поддерживает `flatMap` и `pure`.

```scala
// F[_] — любой контейнер
// Monad[F] — доказательство что F поддерживает flatMap и pure

def sequence[F[_]: Monad, A](items: List[F[A]]): F[List[A]] =
  items.foldLeft(Monad[F].pure(List.empty[A])) { (acc, item) =>
    Monad[F].flatMap(acc { list =>
      Monad[F].map(item)(a => list :+ a)
    })
  }
// Работает с:
// - List[Option[A]] → Option[List[A]]
// - List[Future[A]] → Future[List[A]]
// - List[Either[E, A]] → Either[E, List[A]]
```

**Это та самая структура foldLeft, которая казалась вам "кошмаром".** Теперь вы видите что это не "сложная структура", а универсальный паттерн для агрегации любого Monad.

## 6. foldLeft refresh: Visualizing the accumulator

Прежде чем продолжить, давайте разберём foldLeft визуально — это ваш главный затык.

### foldLeft — это просто цикл с аккумулятором

**Java аналог:**
```java
List<Integer> numbers = List.of(1, 2, 3, 4);

// Java Stream.reduce() — это foldLeft
int sum = numbers.stream()
    .reduce(0, (accumulator, item) -> accumulator + item);
// Результат: 10
```

**Визуальная диаграмма:**
```
foldLeft(0) { (acc, item) => acc + item }

Step 0:  acc = 0 (initial)
Step 1:  acc = 0 + 1 = 1
Step 2:  acc = 1 + 2 = 3
Step 3:  acc = 3 + 3 = 6
Step 4:  acc = 6 + 4 = 10
```

### foldLeft с Option[List[A]] — шаг за шагом

Ваш "кошмар" из урока 0.3:
```scala
List(Some(1), Some(2), None).foldLeft(Some(List.empty[Int])) {
  (acc, item) => acc.flatMap(list => item.map(a => list :+ a))
}
```

**Визуальная диаграмма:**
```
Step 0:  acc = Some(List())

Step 1:  item = Some(1)
         acc.flatMap(list => item.map(a => list :+ a))
         = Some(List()).flatMap(list => Some(1).map(a => List() :+ 1))
         = Some(List()).flatMap(list => Some(List(1)))
         = Some(List(1))
         acc = Some(List(1))

Step 2:  item = Some(2)
         acc.flatMap(list => item.map(a => list :+ a))
         = Some(List(1)).flatMap(list => Some(2).map(a => List(1) :+ 2))
         = Some(List(1)).flatMap(list => Some(List(1, 2)))
         = Some(List(1, 2))
         acc = Some(List(1, 2))

Step 3:  item = None
         acc.flatMap(list => item.map(a => list :+ a))
         = Some(List(1, 2)).flatMap(list => None.map(a => List(1, 2) :+ a))
         = Some(List(1, 2)).flatMap(list => None)
         = None
         acc = None

Step 4:  item = ... (не важно, acc уже None)
         None.flatMap(...) = None
         acc = None

Результат: None
```

**Ключевое понимание:** `flatMap` на `None` всегда даёт `None`. Как только аккумулятор стал `None`, он остаётся `None` — цепочка "разорвана".

### Упрощение: `item.map(a => list :+ a)`

Вместо:
```scala
acc.flatMap(list => item.map(a => list :+ a))
```

Можно записать как:
```scala
for {
  list <- acc      // извлекаем список из Option
  a <- item        // извлекаем элемент из Option
} yield list :+ a  // добавляем элемент в список
```

**For-comprehension делает foldLeft читаемым.** Это не "кошмар", а просто цикл с аккумулятором, где аккумулятор — `Option[List[A]]`.

## 7. Common mistakes

### Mistake 1: "Monad — это что-то сложное"
**Нет.** `Optional` в Java — это Monad. Вы уже использовали Monads годами. Scala просто называет этот паттерн именем и делает его универсальным.

### Mistake 2: "Нужно запомнить Monad законы"
Не нужно запоминать. Нужно понимать интуитивно:
- Left Identity: пустой контейнер + функция = функция
- Right Identity: контейнер → извлечь → обратно в контейнер = тот же контейнер
- Associativity: порядок flatMap не важен

### Mistake 3: "foldLeft — это сложно"
foldLeft — это цикл с аккумулятором. Java's `Stream.reduce()` — то же самое. Сложность возникает только когда аккумулятор — `Option[List[A]]`. Но если разложить по шагам, это просто последовательное добавление элементов.

## 8. Connection to the exercise

В упражнении вы:
1. **Повторите foldLeft** с простыми примерами (сумма, произведение) — чтобы структура стала привычной
2. **Сравните Java Stream.reduce() с Scala foldLeft** — чтобы увидеть что это один и тот же паттерн
3. **Примените foldLeft с Option[List[A]]** — но с визуальной диаграммой каждого шага
4. **Увидите Monad pattern** в Option, List, Either — и поймёте что это один паттерн, а не три разных

## 9. Summary

- **Monad** — это контейнер с `flatMap` и `pure`
- **Вы уже знаете Monads:** `Optional`, `CompletableFuture`, `Mono`, `Flux`
- **Scala унифицирует** этот паттерн через `F[_]` и type classes
- **foldLeft** — это цикл с аккумулятором, не "кошмар"
- **For-comprehension** делает foldLeft с Monad читаемым
