import scala.Option
// ============================================================================
// Additional Exercise: Lesson 0.3 — For-Comprehensions + Protocol Integration
// ============================================================================
//
// Это упражнение учитывает все инсайты из протоколов lessons 0.1, 0.2, 0.3:
//
// Из Lesson 0.1 (Type Classes):
//   - Configuration как implicit параметр (DI pattern)
//
// Из Lesson 0.2 (Higher-Kinded Types, foldLeft):
//   - Агрегация List[Option[A]] → Option[List[A]]
//   - foldLeft с flatMap/map
//
// Из Lesson 0.3 (For-Comprehensions):
//   - For-comprehension с guard и subsequent computation
//   - <- на List (итерация по multiple wallets)
//   - <- vs = для промежуточных вычислений
//
// ============================================================================

object lesson0_3_exercise_additional {

  // ============================================================================
  // Business Context
  // ============================================================================
  //
  // Система расчёта бонусов лояльности с конфигурацией.
  //
  // Минимальные балансы и ставки бонусов — это КОНФИГУРАЦИЯ, а не параметры.
  // Разные валюты имеют разные минимальные балансы и ставки.
  //
  // Клиент может сделать несколько транзакций одновременно.
  // Нужно агрегировать результаты всех транзакций.
  //
  // Если хотя бы одна транзакция не прошла — весь пакет отклонён.
  //
  // ============================================================================

  case class Wallet(currency: String, balance: BigDecimal)
  case class Account(name: String, wallets: List[Wallet])
  case class TransactionResult(currency: String, amount: BigDecimal, bonus: BigDecimal)

  // Конфигурация — минимальные балансы и ставки по валютам
  case class TransactionConfig(
    minBalances: Map[String, BigDecimal],
    bonusRates:  Map[String, BigDecimal]
  )

  implicit val config: TransactionConfig = TransactionConfig(
    minBalances = Map("USD" -> 100, "EUR" -> 200, "GBP" -> 0),
    bonusRates = Map("USD" -> 10, "EUR" -> 5, "GBP" -> 2)
  )

  // AC-1: Одиночная транзакция с конфигурацией
  // Используйте for-comprehension.
  // minBalance и bonusRate берутся из config, а не передаются как параметры.
  def processTransaction(accountOpt: Option[Account],
                         currency: String)(implicit config: TransactionConfig): Option[TransactionResult] =
    for {
      account <- accountOpt
      wallet <- account.wallets.find(_.currency == currency)
      balance = wallet.balance
      currencyBalanceConfig <- config.minBalances.get(currency)
      if balance >= currencyBalanceConfig
      currencyBonusConfig <- config.bonusRates.get(currency)
      bonus = balance * currencyBonusConfig / 100
    } yield TransactionResult(currency, balance, bonus)

  // AC-2: Пакет транзакций (Lesson 0.2: foldLeft + flatMap/map)
  // Если хотя бы одна транзакция не прошла — весь пакет отклонён (None).
  // Используйте foldLeft с flatMap/map (как в lesson0_2_exercise).
  def processBatch(
      accountOpt: Option[Account],
      currencies: List[String])
      (implicit config: TransactionConfig)
  : Option[List[TransactionResult]] =
    currencies.
      map(currency => processTransaction(accountOpt, currency))
      .foldLeft(Some(List.empty[TransactionResult]) : Option[List[TransactionResult]]) {
        (acc, item) => acc.flatMap(list => item.map(transactionResult => list :+ transactionResult ))
      }


  def main(args: Array[String]): Unit = {

    val account = Account(
      name = "Bob",
      wallets = List(
        Wallet("USD", 1000),
        Wallet("EUR", 500),
        Wallet("GBP", 0)
      )
    )

    // AC-1 (Happy path): USD транзакция с config
    // Ожидаемый результат: Some(TransactionResult("USD", 1000, 100))
    val transactionResult1 = processTransaction(Some(account), "USD")
    println(transactionResult1)
    assert(transactionResult1.contains(TransactionResult("USD", 1000, 100)))

    // AC-2 (Currency not configured): Валюта нет в config
    // Ожидаемый результат: None
    val transactionResult2 = processTransaction(Some(account), "CAD")
    println(transactionResult2)
    assert(transactionResult2.isEmpty)

    // AC-3 (Balance below configured min): Баланс < minBalance из config
    // Ожидаемый результат: None
    val transactionResult3 = processTransaction(Some(Account(name= "another", wallets = List(Wallet("USD", 99)))), "USD")
    println(transactionResult3)
    assert(transactionResult3.isEmpty)

    // AC-4 (Zero balance, zero min): GBP с balance=0, min=0
    // Ожидаемый результат: Some(TransactionResult("GBP", 0, 0))
    val transactionResult4 = processTransaction(Some(account), "GBP")
    println(transactionResult4)
    assert(transactionResult4.contains(TransactionResult("GBP", 0, 0)))

    // AC-5 (Batch happy path): Пакет из 2 валют (USD + EUR)
    // Ожидаемый результат: Some(List(TransactionResult("USD",1000,100), TransactionResult("EUR",500,25)))
    val batchResult1 = processBatch(Some(account), List("USD", "EUR"))
    println(batchResult1)
    assert(batchResult1.contains(List(TransactionResult("USD",1000,100), TransactionResult("EUR",500,25))))

    // AC-6 (Batch failure): Пакет содержит валюту без кошелька
    // Ожидаемый результат: None
    val batchResult2 = processBatch(Some(account), List("USD", "CAD"))
    println(batchResult2)
    assert(batchResult2.isEmpty)

    // AC-7 (Batch single): Пакет из 1 валюты
    // Ожидаемый результат: Some(List(TransactionResult("USD",1000,100)))
    val batchResult3 = processBatch(Some(account), List("USD"))
    println(batchResult3)
    assert(batchResult3.contains(List(TransactionResult("USD",1000,100))))
  }
}
