case class Wallet(currency: String, balance: BigDecimal)
case class Account(id: String, wallets: List[Wallet])

object lesson0_3 {

  def getBalanceWithNoForComprehensive(accountOpt: Option[Account], currency: String): Option[BigDecimal] =
    accountOpt
      .flatMap(account => account.wallets.find(w => w.currency == currency)
      .map(w => w.balance))

  def getBalance(accountOpt: Option[Account], currency: String): Option[BigDecimal] =
    for {
      account <- accountOpt                    // если None — результат None
      wallet  <- account.wallets.find(_.currency == currency)  // если не найдено — None
    } yield wallet.balance                      // оба Some — возвращаем balance

  def main(args: Array[String]): Unit = {
    val account = Account("ACC-1", List(
      Wallet("USD", BigDecimal("1000.00"))
    ))

    println(getBalanceWithNoForComprehensive(Some(account), "USD"))
    // Some(1000.00)

    println(getBalanceWithNoForComprehensive(Some(account), "EUR"))
    // None

    println(getBalanceWithNoForComprehensive(None, "USD"))
    // None
  }
}