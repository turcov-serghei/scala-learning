# Lesson 1.1 — Variables, Types, Control Structures

## 1. What problem are we solving?

В Java проверка транзакции обычно меняет локальное состояние и выбирает ветку через `if`:

```java
final class CreditRequest {
    final int amount;
    CreditRequest(int amount) { this.amount = amount; }
}

static String classify(CreditRequest request) {
    String result;
    if (request.amount <= 0) {
        result = "invalid";
    } else if (request.amount > 1000) {
        result = "review";
    } else {
        result = "approved";
    }
    return result;
}
```

В Scala тот же выбор можно выразить без изменяемой переменной и явного `return`.

## 2. Core concept

- `val` объявляет ссылку, которую нельзя переназначить; используйте по умолчанию.
- `var` разрешает переназначение; оставляйте его для случаев, где изменение действительно нужно.
- Тип часто выводится из значения: `val amount = 100` имеет тип `Int`.
- `if/else` — выражение: каждая ветка возвращает значение.
- Метод возвращает значение последнего выражения своего тела.

## 3. Why does it exist?

Когда вычисления выражены как значения, легче видеть вход и результат функции, тестировать правила отдельно и не отслеживать изменения локального состояния. Это не означает, что `if` запрещён: в Scala он полезен именно как выражение, а для разбора вариантов данных позже изучим `match`.

## 4. Java → Scala (complete runnable examples)

### Java

```java
public class CreditCheck {
    static final class CreditRequest {
        final int amount;
        CreditRequest(int amount) { this.amount = amount; }
    }

    static String classify(CreditRequest request) {
        if (request.amount <= 0) return "invalid";
        else if (request.amount > 1000) return "review";
        else return "approved";
    }

    public static void main(String[] args) {
        System.out.println(classify(new CreditRequest(250)));
    }
}
```

### Scala 2.13

```scala
object CreditCheck {
  final case class CreditRequest(amount: Int)

  def classify(request: CreditRequest): String =
    if (request.amount <= 0) "invalid"
    else if (request.amount > 1000) "review"
    else "approved"

  def main(args: Array[String]): Unit = {
    val request = CreditRequest(250)
    println(classify(request))
  }
}
```

## 5. Step-by-step

1. `final case class CreditRequest(amount: Int)` объявляет тип данных с полем `amount`; экземпляр создаётся как `CreditRequest(250)`, без `new`.
2. `val request = ...` объявляет неизменяемую ссылку. Тип `CreditRequest` выведен компилятором.
3. `def classify(request: CreditRequest): String` явно указывает тип входа и результата.
4. Цепочка `if/else` вычисляет одну строку; результат всей цепочки становится результатом метода.
5. `Unit` — тип результата для метода, который выполняет действие вроде `println` и не возвращает полезного значения; это аналогично Java `void`.

## 6. Important details

- Неизменяемость `val` относится к самой ссылке: если `val` указывает на изменяемый объект, состояние объекта всё ещё может меняться. Здесь мы предпочитаем неизменяемые данные.
- У выражения `if` с результатом обе ветки должны давать совместимый тип.
- `if` без `else` — это `if (c) a else ()`: недостающая ветка имеет тип `Unit`.
- Если ожидаемого типа нет, тип `if/else` — ближайший общий супертип веток (как у Java-тернарника `flag ? "a" : 42`). Цепочки предков: `Some[String]` и `None` сходятся в `Option[String]`; `Some[String]` и `Unit` только в `Any`. Если явный тип результата задан (например `Option[String]`), несовместимая ветка даёт `type mismatch`.
- Типы чисел не следует угадывать по виду: `Int`, `Long`, `Double` — разные типы. Указывайте тип явно, когда он неочевиден или важен для API.
- В Scala 2.13 скобки в списке параметров метода обязательны: `def classify(request: CreditRequest): String`. Скобки могут отсутствовать у метода без параметров (`def size: Int`); это отдельная форма объявления, а не метод с одним параметром.

## 7. Common mistakes

- `var amount = 100` с последующими присваиваниями по привычке Java: предпочитайте `val`, если переназначение не требуется.
- `new CreditRequest(250)`: для `case class` в Scala 2.13 используйте `CreditRequest(250)`.
- Сравнивать значение с `null` как обычный способ обозначить отсутствие: позже для этого изучим `Option`.
- Пытаться использовать `if` как оператор, присваивая результат в несколько шагов: здесь обе ветки `if/else` сами возвращают результат.
- Использовать mutable collections для обычных преобразований: в курсе начнём с неизменяемых коллекций.
- Ждать от компилятора Java-проверки «не во всех ветках есть `return`»: в Scala это проверка типов веток, а не анализ потока выполнения.
- Использовать `if/else` для разбора структурных вариантов данных, когда уместнее будет `match`. `if/else` остаётся правильным выбором для условий и диапазонов; `match` для case classes и sealed hierarchies подробно разберём в Lesson 1.2.
