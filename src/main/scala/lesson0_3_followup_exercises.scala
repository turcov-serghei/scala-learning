import scala.:+

object lesson0_3_followup_exercises {
  // ============================================================================
  // Lesson 0.3 Follow-up Exercises — For-Comprehensions Decoded
  // ============================================================================
  // Level 1: Только описание задачи. Вы пишёте всё с нуля.
  //
  // Цель: Закрепить нюансы, обсуждённые во время теории урока 0.3.
  // Каждый микро-упражнение направлено на конкретный вопрос из обсуждения.
  // ============================================================================

  // ---------------------------------------------------------------------------
  // Общие case class'ы для всех упражнений
  // ---------------------------------------------------------------------------

  // ---------------------------------------------------------------------------
  // Микро-упражнение 1: `<-` vs `=`
  // ---------------------------------------------------------------------------
  //
  // Контекст: В for-comprehension есть два типа строк:
  //   - `a <- optA`  → flatMap (если None, весь результат None)
  //   - `b = a + 1`  → plain val (просто присваивание внутри)
  //
  // Задача: Напишите функцию, которая принимает Option[Account] и возвращает
  // Option[String] с форматированной информацией об аккаунте.
  //
  // Требования:
  //   1. Используйте for-comprehension
  //   2. Извлеките account через `<-` (flatMap)
  //   3. Вычислите walletCount через `=` (plain val) — количество кошельков
  //   4. Вычислите summary через `=` (plain val) — строка "Account X has N wallets"
  //   5. Верните summary через yield
  //
  // Подсказка: `<-` используется для Option, `=` для обычных вычислений.
  //
  // Подпись функции:
  //   def accountSummary(accountOpt: Option[Account]): Option[String]
  //
  // Ожидаемые результаты:
  //   Some(Account("ACC-1", "Alice", List(Wallet("USD", 100), Wallet("EUR", 200))))
  //     → Some("Account Alice has 2 wallets")
  //   None → None

  // Напишите вашу реализацию здесь

  def accountSummary(accountOpt: Option[Account]): Option[String] =
    for {
      account <- accountOpt
      walletCount = account.wallets.length
      summary = s"Account ${account.name} has $walletCount wallets"
    } yield summary

  // ---------------------------------------------------------------------------
  // Микро-упражнение 2: Guards с генераторами после
  // ---------------------------------------------------------------------------
  //
  // Контекст: Guard (`if`) в for-comprehension — это фильтрация.
  // После guard может идти ещё один generator (`<-`).
  // Если guard не прошёл → результат None.
  // Если прошёл → цепочка продолжается.
  //
  // Задача: Напишите функцию, которая принимает Option[Account], валюту и
  // пороговое значение баланса. Функция должна:
  //   1. Найти кошелёк с указанной валютой
  //   2. Проверить что баланс > порога (guard)
  //   3. После guard — вычислить bonus через `=` (plain val)
  //      Bonus(amount = balance * 0.1, reason = "Loyalty")
  //   4. Вернуть bonus через yield
  //
  // Требования:
  //   1. Используйте for-comprehension
  //   2. Используйте guard (`if`) для проверки баланса
  //   3. После guard вычислите bonus через `=`
  //   4. Верните Option[Bonus]
  //
  // Подсказка: account.wallets.find(_.currency == currency) возвращает Option[Wallet]
  //
  // Подпись функции:
  //   def getLoyaltyBonus(
  //       accountOpt: Option[Account],
  //       currency: String,
  //       minBalance: BigDecimal
  //     ): Option[Bonus]
  //
  // Ожидаемые результаты:
  //   Account с Wallet("USD", 1000), currency="USD", minBalance=500
  //     → Some(Bonus(100.0, "Loyalty"))
  //   Account с Wallet("USD", 100), currency="USD", minBalance=500
  //     → None (guard не прошёл)
  //   Account без Wallet("USD") → None (find вернул None)

  // Напишите вашу реализацию здесь

  def getLoyaltyBonus(
                       accountOpt: Option[Account],
                       currency: String,
                       minBalance: BigDecimal
                     ): Option[Bonus] =
    for {
      account <- accountOpt
      walletOpt = account.wallets.find(w => w.currency == currency)
      wallet <- walletOpt
      if wallet.balance >= minBalance
      bonus = Bonus(wallet.balance * 0.1, "Loyalty")
    } yield bonus


// ---------------------------------------------------------------------------
// Микро-упражнение 3: `yield` vs без `yield`
// ---------------------------------------------------------------------------
//
// Контекст:
//   - for { ... } yield result → возвращает Option[T] (декларативный)
//   - for { ... } println(result) → возвращает Unit (побочный эффект)
//
// Задача: Напишите ДВЕ функции:
//
// Функция A (декларативная):
//   Принимает Option[Account] и возвращает Option[List[String]]
//   где каждый элемент — название валюты из кошельков аккаунта.
//   Используйте for-comprehension с yield.
//
//   Подпись:
//     def getCurrencies(accountOpt: Option[Account]): Option[List[String]]
//
//   Ожидаемые результаты:
//     Some(Account(..., List(Wallet("USD", 100), Wallet("EUR", 200))))
//       → Some(List("USD", "EUR"))
//     None → None
//
// Функция B (побочный эффект):
//   Принимает Option[Account] и печатает валюты кошельков.
//   Возвращает Unit.
//   Используйте for-comprehension БЕЗ yield.
//
//   Подпись:
//     def printCurrencies(accountOpt: Option[Account]): Unit
//
//   Ожидаемое поведение:
//     Some(Account(..., List(Wallet("USD", 100), Wallet("EUR", 200))))
//       → печатает "USD" и "EUR" на отдельных строках
//     None → ничего не печатает

// Напишите ваши реализации здесь

  def getCurrencies(accountOpt: Option[Account]): Option[List[String]] =
    for {
      account <- accountOpt
      wallets = account.wallets
      currencies = wallets.map(_.currency)
    } yield currencies


  def printCurrencies(accountOpt: Option[Account]): Unit =
    for {
      account <- accountOpt
      wallets = account.wallets
      currencies = wallets.map(_.currency)
      output = currencies.foldLeft(Some(""): Option[String]) { (acc, curr)=>
        acc.flatMap(str => Some(str + "\n" + curr))
      }
    } println(output.get)


  def printCurrenciesSimpler(accountOpt: Option[Account]): Unit =
    for {
      account <- accountOpt
      wallets = account.wallets
      currencies <- wallets.map(_.currency)
    } println(currencies)

// ---------------------------------------------------------------------------
// Микро-упражнение 4: `flatMap.flatMap` vs `flatMap.map`
// ---------------------------------------------------------------------------
//
// Контекст: В ручном коде вы можете использовать flatMap на последнем шаге,
// если лямбда возвращает Option. В for-comprehension yield возвращает
// "голое" значение, поэтому компилятор использует map.
//
// Задача: Напишите ОДНУ и ту же функцию ДВУЯ способами:
//
// Способ A — Ручная цепочка flatMap.map:
//   Принимает Option[Account] и String (валюту).
//   Возвращает Option[BigDecimal] (баланс кошелька).
//   Используйте flatMap + map (без for-comprehension).
//
//   Подпись:
//     def getBalanceManual(
//         accountOpt: Option[Account],
//         currency: String
//       ): Option[BigDecimal]
//
// Способ B — For-comprehension:
//   Та же логика, но с использованием for-comprehension.
//
//   Подпись:
//     def getBalanceForComp(
//         accountOpt: Option[Account],
//         currency: String
//       ): Option[BigDecimal]
//
// Ожидаемые результаты (оба способа):
//   Account с Wallet("USD", 1000), currency="USD"
//     → Some(1000.00)
//   Account без Wallet("USD")
//     → None
//   None, currency="USD"
//     → None
//
// Цель: Убедиться, что оба способа дают одинаковый результат,
// и увидеть разницу в читаемости.

// Напишите ваши реализации здесь

  def getBalanceManual(accountOpt: Option[Account],
                       currency: String): Option[BigDecimal] =
    accountOpt.flatMap(a => a.wallets.find(_.currency == currency))
      .map(w => w.balance)

  def getBalanceForComp(
                         accountOpt: Option[Account],
                         currency: String): Option[BigDecimal] =
    for {
      account <- accountOpt
      walletOpt = account.wallets.find(_.currency == currency)
      wallet <- walletOpt
    } yield wallet.balance

// ---------------------------------------------------------------------------
// Main — Тесты
// ---------------------------------------------------------------------------
// После реализации всех функций раскомментируйте и запустите тесты.

  // ---------------------------------------------------------------------------
  // Общие case class'ы для всех упражнений (изолированы внутри object)
  // ---------------------------------------------------------------------------

  case class Wallet(currency: String, balance: BigDecimal)
  case class Account(id: String, name: String, wallets: List[Wallet])
  case class Bonus(amount: BigDecimal, reason: String)

  def main(args: Array[String]): Unit = {
    val alice = Account(
      "ACC-1",
      "Alice",
      List(
        Wallet("USD", BigDecimal("1000.00")),
        Wallet("EUR", BigDecimal("200.00"))
      )
    )

    val bob = Account("ACC-2", "Bob", List.empty)

    // --- Микро-упражнение 1: <- vs = ---
    println("=== Micro Exercise 1: <- vs = ===")
    val accountSummaryAlice = accountSummary(Some(alice))
    println(accountSummaryAlice)
    assert(accountSummaryAlice.contains("Account Alice has 2 wallets"))// Some("Account Alice has 2 wallets")

    println(accountSummary(None)) // None
    assert(accountSummary(None).isEmpty)

    val accountSummaryBob = accountSummary(Some(bob))
    println(accountSummaryBob)
    assert(accountSummaryBob.contains("Account Bob has 0 wallets"))

    println("=== Micro Exercise 1: <- vs = === PASSED")


    // --- Микро-упражнение 2: Guards с генераторами после ---
    println("\n=== Micro Exercise 2: Guards ===")
    val bonus1 = getLoyaltyBonus(Some(alice), "USD", BigDecimal("500"))
    println(bonus1)  // Some(Bonus(100.0, "Loyalty"))
    assert(bonus1.contains(Bonus(100.0, "Loyalty")))

    val bonus2 = getLoyaltyBonus(Some(alice), "USD", BigDecimal("1500"))
    println(bonus2) // None (guard не прошёл)
    assert(bonus2.isEmpty)

    val bonus3 = getLoyaltyBonus(Some(alice), "GBP", BigDecimal("0"))
    println(bonus3)    // None (валюта не найдена)
    assert(bonus3.isEmpty)

    println("\n=== Micro Exercise 2: Guards === PASSED")

    // --- Микро-упражнение 3: yield vs без yield ---
    println("\n=== Micro Exercise 3: yield vs no yield ===")

    val aliceCurrencies = getCurrencies(Some(alice))
    println(aliceCurrencies)  // Some(List(USD, EUR))
    assert(aliceCurrencies.contains(List("USD", "EUR")))

    val noCurrencies = getCurrencies(None)
    println(noCurrencies)          // None
    assert(noCurrencies.isEmpty)

    val bobCurrencies = getCurrencies(Some(bob))
    println(bobCurrencies)          // None
    assert(bobCurrencies.contains(List.empty))

    printCurrencies(Some(alice))          // печатает USD и EUR
    printCurrenciesSimpler(Some(alice))          // печатает USD и EUR
    println("\n=== Micro Exercise 3: yield vs no yield === PASSED")

    // --- Микро-упражнение 4: flatMap.flatMap vs flatMap.map ---
    println("\n=== Micro Exercise 4: Manual vs For-comprehension ===")

    val aliceUsdBalanceManual = getBalanceManual(Some(alice), "USD")
    println(aliceUsdBalanceManual)   // Some(1000.00)
    assert(aliceUsdBalanceManual.contains(1000.0))

    val aliceUsdBalanceComp = getBalanceForComp(Some(alice), "USD")
    println(aliceUsdBalanceComp)  // Some(1000.00)
    assert(aliceUsdBalanceComp.contains(1000.0))

    val aliceGBPBalanceManual = getBalanceManual(Some(alice), "GBP")
    println(aliceGBPBalanceManual)   // Some(1000.00)
    assert(aliceGBPBalanceManual.isEmpty)

    val aliceGBPBalanceComp = getBalanceForComp(Some(alice), "GBP")
    println(aliceGBPBalanceComp)  // Some(1000.00)
    assert(aliceGBPBalanceComp.isEmpty)

    val nonBalanceManual = getBalanceManual(None, "USD")
    println(nonBalanceManual)           // None
    assert(nonBalanceManual.isEmpty)

    val nonBalanceComp = getBalanceForComp(None, "USD")
    println(nonBalanceComp)          // None
    assert(nonBalanceComp.isEmpty)

    val bobBalanceManual = getBalanceManual(Some(bob), "USD")
    println(bobBalanceManual)          // None
    assert(bobBalanceManual.isEmpty)

    val bobBalanceComp = getBalanceForComp(Some(bob), "USD")
    println(bobBalanceComp)          // None
    assert(bobBalanceComp.isEmpty)

    println("\n=== Micro Exercise 4: Manual vs For-comprehension === PASSED")
  }
}
