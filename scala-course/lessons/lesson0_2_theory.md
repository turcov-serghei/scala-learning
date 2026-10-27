# Lesson 0.2 — Higher-Kinded Types

## 1. What problem are we solving?

В Java вы пишете одинаковую логику для разных контейнеров:

```java
// Для Optional
Optional<List<String>> sequenceOptional(List<Optional<String>> items) { ... }

// Для Mono — та же логика
Mono<List<String>> sequenceMono(List<Mono<String>> items) { ... }

// Для CompletableFuture — та же логика
CompletableFuture<List<String>> sequenceFuture(List<CompletableFuture<String>> items) { ... }
```

**Проблема:** 3 метода, одна и та же логика, дублирование кода.

**Цель:** Одна функция, которая работает с любым контейнером.

## 2. Core concept

**Higher-Kinded Type** (`F[_]`) — это абстракция над самим контейнером.

| Понятие | Пример |
|---------|--------|
| **Значение** | `42`, `"hello"`, `Some(1)` |
| **Тип** | `Int`, `String`, `Option[Int]` |
| **Тип-конструктор** | `Option`, `List`, `Future` |
| **Higher-Kinded Type** | `F[_]` — "любой тип-конструктор" |

`F[_]` читается как: "некий контейнер, куда можно подставить тип".

- `F = Option` → `Option[Int]`
- `F = List` → `List[String]`
- `F = Future` → `Future[BigDecimal]`

## 3. Why does it exist?

Java не поддерживает higher-kinded types. Вы не можете написать:

```java
// НЕВОЗМОЖНО в Java:
public <T, F<T>> F<List<T>> sequence(List<F<T>> items) { ... }
```

Scala поддерживает. Это позволяет писать **универсальные функции**, которые работают с любым контейнером:

```scala
// Одна функция для Option, Future, Stream, List
def sequence[F[_], A](items: List[F[A]]): F[List[A]]
```

В экосистеме Cats/FS2 **весь код** написан через `F[_]`. Вы пишете логику один раз, она работает с любым контейнером.

## 4. Java → Scala

### Java — дублирование для каждого контейнера

```java
import java.util.*;
import java.util.stream.*;

public class SequenceExample {
    
    public static <T> Optional<List<T>> sequenceOptional(List<Optional<T>> items) {
        return items.stream()
            .reduce(Optional.of(new ArrayList<T>()),
                (acc, item) -> acc.flatMap(list ->
                    item.map(value -> {
                        List<T> newList = new ArrayList<>(list);
                        newList.add(value);
                        return Optional.of(newList);
                    })),
                (a, b) -> a
            );
    }
    
    public static void main(String[] args) {
        List<Optional<Integer>> test1 = Arrays.asList(
            Optional.of(1), Optional.of(2), Optional.of(3)
        );
        System.out.println(sequenceOptional(test1));
        // Optional([1, 2, 3])
        
        List<Optional<Integer>> test2 = Arrays.asList(
            Optional.of(1), Optional.empty(), Optional.of(3)
        );
        System.out.println(sequenceOptional(test2));
        // Optional.empty
    }
}
```

### Scala — один метод для Option

```scala
object SequenceExample {
  
  def sequenceOption[A](items: List[Option[A]]): Option[List[A]] =
    items.foldLeft(Some(List.empty[A]): Option[List[A]]) { (acc, item) =>
      acc.flatMap(list => item.map(value => list :+ value))
    }
  
  def main(args: Array[String]): Unit = {
    val test1 = List(Some(1), Some(2), Some(3))
    println(sequenceOption(test1))
    // Some(List(1, 2, 3))
    
    val test2 = List(Some(1), None, Some(3))
    println(sequenceOption(test2))
    // None
  }
}
```

## 5. Step-by-step

Разберём Scala код по шагам:

**Шаг 1: `foldLeft` с начальным значением**
```scala
items.foldLeft(Some(List.empty[A]): Option[List[A]]) { ... }
```
Начинаем с пустого списка внутри `Some`. Аналог Java: `Optional.of(new ArrayList<>())`.

**Шаг 2: Лямбда `(acc, item)`**
```scala
{ (acc, item) => ... }
// acc: Option[List[A]] — накопленный результат
// item: Option[A] — текущий элемент
```

**Шаг 3: `flatMap` + `map` цепочка**
```scala
acc.flatMap(list => item.map(value => list :+ value))
```
- Если `acc = None` → результат `None` (fail-fast)
- Если `item = None` → результат `None` (fail-fast)
- Если оба `Some` → `list :+ value` (добавляем значение к списку)

**Протрассировка:**
```
Вход: List(Some(1), Some(2), None)

Начало: acc = Some(List())

Элемент 1: Some(1)
  acc.flatMap(list => Some(1).map(v => List() :+ 1))
  = Some(List(1))

Элемент 2: Some(2)
  acc.flatMap(list => Some(2).map(v => List(1) :+ 2))
  = Some(List(1, 2))

Элемент 3: None
  acc.flatMap(list => None.map(v => List(1,2) :+ v))
  = None (fail-fast!)

Результат: None
```

## 6. Important details

**`F` — это конвенция, не правило.** Можно использовать любую букву:

```scala
def sequence[F[_], A](items: List[F[A]]): F[List[A]]  // F — стандарт
def sequence[G[_], A](items: List[G[A]]): G[List[A]]  // G — тоже работает
def sequence[M[_], A](items: List[M[A]]): M[List[A]]  // M — тоже работает
```

**`: Option[List[A]]` — явное указание типа.** Компилятор Scala иногда не может сам вывести тип аккумулятора в `foldLeft`. Вы помогаете ему:

```scala
Some(List.empty[A]): Option[List[A]]
//  ^^^^^^^^^^^^^^^^^^^  ^^^^^^^^^^^^^^^
//  значение             тип (как Java каст)
```

**`list :+ value`** — добавление элемента в конец списка:
```scala
List(1, 2) :+ 3  // List(1, 2, 3)
```

## 7. Common mistakes

**Ошибка 1: Путать `F[_]` с generic**
```scala
// Generic — конкретный тип
def foo[T](item: T): T = item

// Higher-kinded — тип-конструктор
def bar[F[_], A](item: F[A]): F[A] = item
//        ^^^^ F — это не тип, это "контейнер"
```

**Ошибка 2: Думать что `F[_]` — это `List`**
`F[_]` — это **любой** контейнер: `Option`, `Future`, `Either`, `Stream`. `List` — только один из вариантов.

**Ошибка 3: Забыть что `flatMap` на `Option` — это fail-fast**
```scala
None.flatMap(x => Some(42))  // None, не Some(42)!
```

## 8. Connection to the exercise

В упражнении `lesson0_2_exercise` вы реализуете `aggregateResults` — функцию, которая агрегирует `List[Option[A]]` в `Option[List[A]]`.

**Почему это упражнение:**
- Вы учитесь работать с `Option` как контейнером (`F[_]` где `F = Option`)
- Понимаете паттерн агрегации: из списка контейнеров получить один контейнер со списком
- Ваш код с `contains` + `flatMap` — корректное решение, хотя и не через `foldLeft`


## Review points

- Функция работает с контейнером как с `F[_]` (или с `Option`) и использует `flatMap`/`map`, а не `.get`, `isEmpty` или `null`-проверки.
- Нет `var` и мутабельных коллекций для накопления результата.
- `contains` + `flatMap` (два прохода) на этом уроке допустим; требование одного прохода через `foldLeft` вводится в Lesson 0.4.
- Логика не дублируется под каждый тип контейнера: общее поведение выражено один раз.
