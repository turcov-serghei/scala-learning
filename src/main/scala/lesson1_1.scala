object lesson1_1 {

  case class Wallet(currency: String, amount: BigDecimal)
  case class Account(id: String, wallet: Wallet) {
    def validate(amount: Double) : Boolean = ???

    def balance: BigDecimal = wallet.amount

    def bonus(amount: BigDecimal): BigDecimal = wallet.amount + amount
  }

  def validate(amount: Int): Unit = {
    val res = if (amount <= 0) println("invalid")
    else "valid"
    res
  }

  def main(args: Array[String]): Unit = {
    val amount = 10
    val res = if (amount <= 0) println("invalid")
    else Some("valid")

    val result: Any  =
      if (true) "approved"
      else 42
  }
}
