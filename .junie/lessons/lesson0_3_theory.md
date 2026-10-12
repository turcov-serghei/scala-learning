# Lesson 0.3 — For-Comprehensions Decoded

## 1. What problem are we solving?

В Java вы часто сталкиваетесь с вложенными `Optional`:

```java
public BigDecimal getBalance(String accountId, String currency) {
    Optional<Account> accountOpt = accountRepository.findById(accountId);
    if (accountOpt.isEmpty()) {
        return null;
    }
    Account account = accountOpt.get();
    
    Optional<Wallet> walletOpt = account.getWallets().stream()
        .filter(w -> w.getCurrency().equals(currency))
        .findFirst();
    if (walletOpt.isEmpty()) {
        return null;
    }
    Wallet wallet = walletOpt.get();
    
    return wallet.getBalance();
}
```

**Проблема:** Каждый уровень вложенности требует явной проверки и `.get()`. Код становится "лесенкой" из `if` + `.get()`. Это **imperative** стиль — вы управляете потоком явно.

**Цель:** Написать то же самое декларативно, без явных проверок и `.get()`.

## 2. Core concept

**For-comprehension — это НЕ цикл.** Это синтаксический сахар для цепочки `flatMap` + `map`.

| Scala for-comprehension | Реальная операция |
|------------------------|-------------------|
| `for { a <- optA; b <- optB } yield result` | `optA.flatMap(a => optB.map(b => result))` |
| `for { a <- optA; b <- optB; c <- optC } yield result` | `optA.flatMap(a => optB.flatMap(b => optC.map(c => result)))` |

Scala компилятор **автоматически** преобразует `for` в `flatMap`/`map`. Вы пишете читаемо, компилятор генерирует цепочку.

## 3. Why does it exist?

В уроке 0.2 вы видели `foldLeft` с `flatMap` + `map`:

```scala
acc.flatMap(list => item.map(value => list :+ value))
```

Когда таких вложений становится 3-4, код превращается в "callback hell":

```scala
optA.flatMap(a =>
  optB.flatMap(b =>
    optC.flatMap(c =>
      optD.map(d => a + b + c + d)
    )
  )
)
```

For-comprehension делает то же самое, но читаемо:

```scala
for {
  a <- optA
  b <- optB
  c <- optC
  d <- optD
} yield a + b + c + d
```

## 4. Java → Scala

### Java — вложенные Optional

```java
import java.util.*;

public class ForComprehensionExample {
    
    static class Account {
        String id;
        List<Wallet> wallets;
        Account(String id, List<Wallet> wallets) { this.id = id; this.wallets = wallets; }
    }
    
    static class Wallet {
        String currency;
        BigDecimal balance;
        Wallet(String currency, BigDecimal balance) { this.currency = currency; this.balance = balance; }
    }
    
    public static Optional<BigDecimal> getBalance(
            Optional<Account> accountOpt, String currency) {
        
        // Imperative: явные проверки + .get()
        if (accountOpt.isEmpty()) return Optional.empty();
        Account account = accountOpt.get();
        
        Optional<Wallet> walletOpt = account.wallets.stream()
            .filter(w -> w.currency.equals(currency))
            .findFirst();
        if (walletOpt.isEmpty()) return Optional.empty();
        
        return Optional.of(walletOpt.get().balance);
    }
    
    public static Optional<BigDecimal> getBalanceFunctional(
            Optional<Account> accountOpt, String currency) {
        
        // Declarative: flatMap + map цепочка
        return accountOpt
            .flatMap(account ->
                account.wallets.stream()
                    .filter(w -> w.currency.equals(currency))
                    .findFirst()
                    .map(wallet -> wallet.balance)
            );
    }
    
    public static void main(String[] args) {
        Wallet usdWallet = new Wallet("USD", new BigDecimal("1000.00"));
        Account account = new Account("ACC-1", List.of(usdWallet));
        
        System.out.println(getBalance(Optional.of(account), "USD"));
        // Optional[1000.00]
        
        System.out.println(getBalance(Optional.of(account), "EUR"));
        // Optional.empty
        
        System.out.println(getBalance(Optional.empty(), "USD"));
        // Optional.empty
    }
}
```

### Scala — For-comprehension

```scala
import java.math.BigDecimal

case class Wallet(currency: String, balance: BigDecimal)
case class Account(id: String, wallets: List[Wallet])

object ForComprehensionExample {
  
  def getBalance(accountOpt: Option[Account], currency: String): Option[BigDecimal] =
    for {
      account <- accountOpt                    // если None — результат None
      wallet  <- account.wallets.find(_.currency == currency)  // если не найдено — None
    } yield wallet.balance                      // оба Some — возвращаем balance
  
  def main(args: Array[String]): Unit = {
    val account = Account("ACC-1", List(
      Wallet("USD", BigDecimal("1000.00"))
    ))
    
    println(getBalance(Some(account), "USD"))
    // Some(1000.00)
    
    println(getBalance(Some(account), "EUR"))
    // None
    
    println(getBalance(None, "USD"))
    // None
  }
}
```

**Ключевое:** `for { ... } yield ...` — это НЕ цикл. Это `flatMap` + `map` в читаемом виде.

## 5. Step-by-step

Разберём как компилятор преобразует for-comprehension:

```scala
for {
  account <- accountOpt
  wallet  <- account.wallets.find(_.currency == currency)
} yield wallet.balance
```

**Компилятор преобразует в:**

```scala
accountOpt.flatMap { account =>
  account.wallets.find(_.currency == currency).map { wallet =>
    wallet.balance
  }
}
```

**Правило преобразования:**
- Каждая строка, кроме последней → `flatMap`
- Последняя строка → `map`
- `yield` → тело `map`

**Протрассировка:**

```
Вход: accountOpt = Some(Account("ACC-1", List(Wallet("USD", 1000.00)))), currency = "USD"

Шаг 1: accountOpt.flatMap { account => ... }
  accountOpt = Some(...) → извлекаем account

Шаг 2: account.wallets.find(_.currency == "USD").map { wallet => ... }
  find возвращает Some(Wallet("USD", 1000.00))
  map извлекает wallet.balance = 1000.00

Результат: Some(1000.00)
```

```
Вход: accountOpt = None, currency = "USD"

Шаг 1: None.flatMap { ... }
  flatMap на None → None (fail-fast)

Результат: None
```

## 6. Important details

**`yield` обязателен.** Без `yield` это обычный `for` цикл (side effects):

```scala
// For-comprehension (декларативный, возвращает Option)
for {
  a <- Some(1)
  b <- Some(2)
} yield a + b
// Result: Some(3)

// Обычный for (императивный, Unit)
for {
  a <- Some(1)
  b <- Some(2)
} println(a + b)
// Result: Unit (побочный эффект)
```

**For-comprehension работает с любым Monad:** `Option`, `Either`, `Future`, `List`:

```scala
// С Either (ошибки с сообщением)
for {
  account <- Right[Throwable, Account](account)
  wallet  <- account.wallets.find(_.currency == currency)
    .toRight(new NoSuchElementException("Wallet not found"))
} yield wallet.balance

// С Future (асинхронно)
for {
  account <- Future(accountRepository.findById(id))
  wallet  <- Future(walletRepository.findByAccount(account.id))
} yield wallet.balance
```

**Guards (фильтрация):**

```scala
for {
  account <- accountOpt
  wallet  <- account.wallets.find(_.currency == currency)
  if wallet.balance > BigDecimal(0)  // guard — фильтрация
} yield wallet.balance
// Если balance <= 0 → None
```

## 7. Common mistakes

**Ошибка 1: Думать что for — это цикл**
```scala
// Это НЕ перебирает элементы
for {
  a <- Some(1)
  b <- Some(2)
} yield a + b
// Это flatMap + map, результат Some(3)
```

**Ошибка 2: Забыть `yield`**
```scala
for {
  a <- Some(1)
  b <- Some(2)
} a + b
// Компилируется, но возвращает Unit!
```

**Ошибка 3: Путать `<-` с `=`**
```scala
for {
  a <- Some(1)   // flatMap — если None, то весь результат None
  b = a + 1       // plain val — просто присваивание внутри
} yield b
// Some(2)
```

**Ошибка 4: Использовать for-comprehension когда нужен простой map**
```scala
// Избыточно:
for { a <- Some(1) } yield a + 1

// Проще:
Some(1).map(_ + 1)
```

## 8. `<-` на разных контейнерах

**Критически важное различие:** `<-` работает по-разному в зависимости от типа контейнера.

| Контейнер | `<-` делает | Количество итераций |
|-----------|-------------|---------------------|
| `Option[A]` | Извлекает значение (если `Some`) | 0 или 1 |
| `List[A]` | Перебирает каждый элемент | N (длина списка) |
| `Future[A]` | Ждёт результат и извлекает | 1 (асинхронно) |

**`Option` — извлечение:**

```scala
for { x <- Some(1) } yield x + 1
// Some(1).flatMap(x => Some(x + 1))
// Результат: Some(2) — 1 итерация
```

**`List` — итерация (цикл):**

```scala
for { x <- List(1, 2, 3) } yield x + 1
// List(1, 2, 3).flatMap(x => List(x + 1))
// Результат: List(2, 3, 4) — 3 итерации
```

**Протрассировка `List`:**

```
for { x <- List(1, 2, 3) } println(x)

Итерация 1: x = 1 → println(1)
Итерация 2: x = 2 → println(2)
Итерация 3: x = 3 → println(3)

Вывод:
1
2
3
```

**Смешанные контейнеры:**

```scala
for {
  account <- accountOpt              // Option: извлекает (0 или 1 раз)
  wallets = account.wallets          // plain val: List[Wallet]
  currency <- wallets.map(_.currency) // List: перебирает (N раз)
} println(currency)

// Если wallets = List(Wallet("USD"), Wallet("EUR")):
// Итерация 1: currency = "USD" → println("USD")
// Итерация 2: currency = "EUR" → println("EUR")
```

**Почему это важно:**

| Что вы думали | Что происходит |
|---------------|----------------|
| `<-` всегда извлекает из контейнера | `<-` на `Option` — извлекает, на `List` — итерирует |
| For-comprehension — это не цикл | На `List` это **цикл**, на `Option` — **извлечение** |

**Ключевой инсайт:** `<-` — это `flatMap`. Поведение зависит от того, как `flatMap` реализован для конкретного контейнера:
- `Option.flatMap` — извлекает и применяет функцию
- `List.flatMap` — перебирает каждый элемент и объединяет результаты

---

## 9. Connection to the exercise

В упражнении вы будете использовать for-comprehension для работы с `List[Option[A]]`.

**Почему это упражнение:**
- Вы закрепите понимание что for-comprehension — это flatMap + map
- Научитесь выбирать между for-comprehension и foldLeft
- Поймёте когда for-comprehension уместен, а когда foldLeft лучше
