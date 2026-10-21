object exercise0 {

  /*accountOpt.flatMap(account ->
    account.wallets.stream()
      .filter(w -> w.currency.equals(currency))
      .findFirst()
      .map(wallet -> wallet.balance)
  )*/

  case class Wallet(currency: String, balance: BigDecimal)
  case class Account(id: String, wallets: List[Wallet])

  def getBalance(accountOpt : Option[Account], walletCurr : String) : Option[BigDecimal] =
    for {
      account <- accountOpt
      wallet <- account.wallets.find(_.currency == walletCurr)
    } yield wallet.balance

  def main(args: Array[String]): Unit = {
    val a = Account("123",
      List(
        Wallet("EUR", 100),
        Wallet("USD", 200)
      )
    )

    val bal1 = getBalance(Some(a), "EUR")
    println(s"account.id=[${a.id}], curr = EUR, balance =$bal1")
    assert(bal1.contains(100))
    val bal2 = getBalance(Some(a), "USD")
    println(s"account.id=[${a.id}], curr = USD, balance =$bal2")
    assert(bal2.contains(200))
    val bal3 = getBalance(Some(a), "GBP")
    println(s"account.id=[${a.id}], curr = GBP, balance =$bal3")
    assert(bal3.isEmpty)
  }
}
