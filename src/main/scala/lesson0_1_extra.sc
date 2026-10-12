trait Format[T] {
  def format(value: T): String
}

case class Transaction(id: String, amount: BigDecimal, status: String)

implicit val shortFormat = new Format[Transaction] {
  override def format(transaction: Transaction): String = s"${transaction.id}:${transaction.status}"
}

implicit val fullFormat: Format[Transaction] = transaction => s"Transaction ${transaction.id}:$$${transaction.amount}:(${transaction.status})"

def display[T](value: T)(implicit format: Format[T]) : Unit = print(format.format(value))

val transaction1 = Transaction("TX-001", BigDecimal(500.00), "COMPLETED")
val transaction2 = Transaction("TX-002", BigDecimal(200.00), "PENDING")
val transaction3 = Transaction("TX-003", BigDecimal(300.00), "FAILED")

display(transaction1)(shortFormat)
display(transaction2)(shortFormat)
display(transaction3)(shortFormat)


