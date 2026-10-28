# Lesson 0.1 — Type Classes

## 1. What problem are we solving?

В Java вы добавляете поведение к типу двумя способами:

1. **Baked-in** (`Comparable`) — поведение зашито в класс. Если класс из библиотеки — вы застряли.
2. **External** (`Comparator`) — поведение передаётся отдельно. Это Strategy Pattern.

```java
// Baked-in — нельзя добавить к чужому классу
class Transaction implements Comparable<Transaction> {
    public int compareTo(Transaction other) { ... }
}

// External — работает с любым типом
Comparator<Transaction> byAmount = (a, b) -> a.getAmount().compareTo(b.getAmount());
transactions.sort(byAmount);
```

**Цель:** Сделать External подход первым классом в Scala — всегда внешний, никогда baked-in.

## 2. Core concept

**Type class** — это интерфейс + экземпляры поведения, которые компилятор подключает автоматически.

| Понятие | Scala | Java аналог |
|---------|-------|-------------|
| Type class | `trait Show[T]` | `interface Show<T>` |
| Экземпляр | `implicit val showTx: Show[Transaction]` | `Show<Transaction> showTx = ...` |
| Функция | `def print[T](value: T)(implicit show: Show[T])` | `void print(T value, Show<T> show)` |
| Вызов | `print(tx)` | `print(tx, showTx)` |

## 3. Why does it exist?

Type class — это **Strategy Pattern с автоматической инъекцией**. Вы определяете поведение отдельно от типа, и компилятор находит правильный экземпляр автоматически.

В экосистеме Cats/FS2/http4s **всё** построено на type classes:
- `Show[T]` — как конвертировать в строку
- `Order[T]` — как сравнивать значения
- `Monad[F]` — как цеплять вычисления
- `Async[F]` — как запускать асинхронные эффекты

## 4. Java → Scala

### Java — явная передача

```java
interface Show<T> {
    String show(T value);
}

Show<Transaction> transactionShow = new Show<>() {
    public String show(Transaction value) {
        return String.format("Transaction(%s, %s)", value.getId(), value.getAmount());
    }
};

public static <T> void print(T value, Show<T> show) {
    System.out.println(show.show(value));
}

// Использование — нужно передавать Show каждый раз
print(myTransaction, transactionShow);
```

### Scala — автоматическое разрешение

```scala
trait Show[T] {
  def show(value: T): String
}

implicit val transactionShow: Show[Transaction] = new Show[Transaction] {
  def show(value: Transaction): String =
    s"Transaction(${value.id}, ${value.amount})"
}

def print[T](value: T)(implicit show: Show[T]): Unit =
  println(show.show(value))

// Использование — компилятор находит transactionShow автоматически
print(myTransaction)
```

## 5. Step-by-step

**Шаг 1: `trait` — интерфейс**
```scala
trait Show[T] {
  def show(value: T): String
}
```
Аналог Java `interface Show<T>`. `trait` может иметь реализацию методов (как Java default methods).

**Шаг 2: `implicit val` — экземпляр**
```scala
implicit val transactionShow: Show[Transaction] = new Show[Transaction] {
  def show(value: Transaction): String = s"..."
}
```
`val` = неизменяемая переменная (как Java `final`).
`implicit` = компилятор автоматически инжектит этот экземпляр, когда нужен `Show[Transaction]`.

**Шаг 3: Функция с implicit параметром**
```scala
def print[T](value: T)(implicit show: Show[T]): Unit
```
| Часть | Значение | Java |
|-------|----------|------|
| `def print` | Метод | `void print` |
| `[T]` | Generic тип | `<T>` |
| `(value: T)` | Первый параметр | `(T value)` |
| `(implicit show: Show[T])` | Второй параметр, implicit | `(Show<T> show)` — явно |
| `: Unit` | Возврат | `: void` |

**Шаг 4: String interpolation**
```scala
s"Transaction(${value.id}, ${value.amount})"
```
Аналог Java: `String.format("Transaction(%s, %s)", value.getId(), value.getAmount())`

## 6. Important details

**`implicit` — это compile-time dependency injection.** Никакого runtime overhead, никакой рефлексии — компилятор разрешает на этапе компиляции.

**Без `implicit`:**
```scala
def print[T](value: T)(show: Show[T]): Unit = println(show.show(value))
print(myTransaction, transactionShow)  // Явная передача, как в Java
```

**Два implicit одного типа в scope = ошибка `ambiguous implicit`.** Решение: сделайте один implicit, второй — обычный `val`.

## 7. Common mistakes

**Ошибка 1: `new` для case class**
```scala
val p = new Person("Bob", 45)  // ❌ Java-привычка
val p = Person("Bob", 45)       // ✅ Case class не требует new
```

**Ошибка 2: `var` вместо `val`**
```scala
var list = sort(people)  // ❌ Java-привычка
val list = sort(people)  // ✅ Immutable по умолчанию
```

**Ошибка 3: `print` вместо `println`**
```scala
print(sortedList)   // ❌ Вывод в одну строку
println(sortedList)  // ✅ Новая строка
```

**Ошибка 4: `Int.compareTo`**
```scala
value1.age.compareTo(value2.age)  // ❌ Int — value type, нет compareTo
java.lang.Integer.compare(value1.age, value2.age)  // ✅
```

## 8. Connection to the exercise

В упражнении `lesson0_1_extra_exercise` вы создаёте type class `Format[T]` для форматирования транзакций.

**Почему это упражнение:**
- Вы учитесь создавать trait (type class)
- Создаёте case class без `new`
- Создаёте `List` через `List(...)`
- Используете `val` вместо `var`
- Implicit resolution — компилятор находит `shortFormat` автоматически
- String interpolation `s"${...}"`

---

## Ad-hoc: `trait` vs Java `interface` — поведенческие отличия

### State (поля в trait)

В Java `interface` не может хранить состояние объекта — только `static final` константы. В Scala `trait` может содержать `val`/`var`, и каждый класс, наследующий этот trait, получает это поле.

```scala
trait Named {
  val name: String        // абстрактное поле — каждый объект получает своё
  def greet(): String = s"Hello, $name"
}

case class User(name: String) extends Named
// User автоматически получает поле name и метод greet()

val u = User("Bob")
println(u.greet())  // Hello, Bob
```

**Что здесь происходит:**
- `trait Named` объявляет `val name: String` — абстрактное поле (без значения).
- `case class User(name: String)` — при `extends Named` компилятор видит: `User` уже имеет параметр `name: String`, значит поле trait реализовано. Ничего переопределять не нужно.
- Если бы case class не имел параметра `name`, компилятор потребовал бы его реализовать.

**Java-аналог:** Нет прямого аналога. В Java для этого нужен абстрактный класс с полем и геттером.

### Implicit resolution (type class pattern)

`trait` + `implicit` — компилятор находит реализацию автоматически (см. разделы 2-4 выше). Java `interface` не имеет compile-time DI.

### Множественное наследование

Один класс может наследовать от **нескольких** trait через `extends` + `with`:

```scala
trait A { def foo(): String = "A" }
trait B { def bar(): String = "B" }

class C extends A with B  // смешивает оба trait
```

При конфликте методов (одинаковые сигнатуры в A и B):

```scala
trait A { def greet(): String = "Hello from A" }
trait B { def greet(): String = "Hello from B" }

class C extends A with B
// ОШИБКА: "class C inherits conflicting members"
```

Компилятор **НЕ выберет** автоматически — потребует явного `override`:

```scala
class C extends A with B {
  override def greet(): String = super[A].greet()  // явно берём из A
}
```

- Java: ошибка компиляции, нужно переопределить вручную
- Scala: `super[A].method` / `super[B].method` — явный выбор

### Упражнение (lesson0_1_adhoc.scala)

Практическое упражнение: `trait Named` с полем state, `trait Auditable` с полем state, case classes с override и mixin. Файл: `src/main/scala/lesson0_1_adhoc.scala`. Результат: компиляция и запуск успешны, все кейсы пройдены.


---

## Review points

- Type class объявлен как `trait` с параметром типа (`Format[T]`), а не как набор отдельных методов под каждый тип.
- Экземпляры type class — `implicit val` (или `implicit object`), а не `var` и не создаются заново при каждом вызове.
- Экземпляр подставляется компилятором (implicit resolution); на месте вызова он не передаётся вручную.
- Case class создаётся без `new`, коллекции — через `List(...)`.
- Нет `var`; строки собираются через интерполяцию `s"..."`, а не конкатенацией.
