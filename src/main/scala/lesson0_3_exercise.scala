object lesson0_3_exercise {

  // ============================================================================
  // Business Context
  // ============================================================================
  //
  // Система расчёта бонусов лояльности.
  // Клиент делает транзакцию → нужно:
  //   1. Проверить существование аккаунта
  //   2. Найти кошелёк по валюте
  //   3. Валидировать что баланс >= минимального
  //   4. Вычислить бонус
  //   5. Вернуть результат транзакции
  //
  // Если любой шаг не прошёл — транзакция отклонена (None).
  //
  // ============================================================================

  case class Wallet(currency: String, balance: BigDecimal)
  case class Account(name: String, wallets: List[Wallet])
  case class TransactionResult(currency: String, amount: BigDecimal, bonus: BigDecimal)

  // Реализуйте функцию с использованием for-comprehension.
  def processTransaction(
      accountOpt: Option[Account],
      currency: String,
      minBalance: BigDecimal,
      bonusRate: BigDecimal
  ): Option[TransactionResult] = for {
    account <- accountOpt
    wallet <- account.wallets.find(_.currency == currency)
    balance = wallet.balance
    if balance >= minBalance
    bonus = balance * bonusRate / 100
  } yield TransactionResult(currency, balance, bonus)

  def main(args: Array[String]): Unit = {
    val account = Account(name = "Bob",
                           wallets = List(
                             Wallet("USD", 1000),
                             Wallet("EUR", 500),
                             Wallet("GBP", 0)
                           )
    )

    println("AC-1 (Happy path): Аккаунт найден, кошелёк найден, баланс >= minBalance")
    // Ожидаемый результат: Some(TransactionResult("USD", 1000.00, 100.00))
    val transactionResult1 = processTransaction(Some(account), "USD", 100, 10)
    println(transactionResult1)
    assert(transactionResult1.contains(TransactionResult("USD", 1000.00, 100.00)))

    println("AC-2 (Account missing): accountOpt = None")
    // Ожидаемый результат: None
    val transactionResult2 = processTransaction(None, "USD", 100, 10)
    println(transactionResult2)
    assert(transactionResult2.isEmpty)

    println("AC-3 (Wallet not found): Аккаунт существует, но нужной валюты нет")
    // Ожидаемый результат: None
    val transactionResult3 = processTransaction(Some(account), "CAD", 100, 10)
    println(transactionResult3)
    assert(transactionResult3.isEmpty)

    println("AC-4 (Balance too low): Баланс < minBalance")
    // Ожидаемый результат: None
    val transactionResult4 = processTransaction(Some(account), "USD", 1001, 10)
    println(transactionResult4)
    assert(transactionResult4.isEmpty)

    println("AC-5 (Multiple wallets): Аккаунт с несколькими кошельками, найти нужный по валюте")
    // Ожидаемый результат: Some(TransactionResult("EUR", 500.00, 50.00))
    val transactionResult5 = processTransaction(Some(account), "EUR", 100, 10)
    println(transactionResult5)
    assert(transactionResult5.contains(TransactionResult("EUR", 500.00, 50.00)))

    println("AC-6 (Zero balance, zero min): Баланс = 0, minBalance = 0 → транзакция проходит")
    // Ожидаемый результат: Some(TransactionResult("GBP", 0, 0))
    val transactionResult6 = processTransaction(Some(account), "GBP", 0, 10)
    println(transactionResult6)
    assert(transactionResult6.contains(TransactionResult("GBP", 0, 0)))


    val account2 = Account(name = "Bob",
      wallets = List(
        Wallet("USD", 2000)
      )
    )

    println("AC-7 (Guard + subsequent computation): После guard вычислить бонус через =")
    // Ожидаемый результат: Some(TransactionResult("USD", 2000.00, 200.00))
    val transactionResult7 = processTransaction(Some(account2), "USD", 100, 10)
    println(transactionResult7)
    assert(transactionResult7.contains(TransactionResult("USD", 2000.00, 200.00)))
  }
}
