object lesson0_1_adhoc {

  trait Named {
    val name: String // поле состояния

    def greet(): String = s"Hello, $name"
  }

  case class User(name: String) extends Named

  case class GreetedUser(name: String) extends Named {
    override def greet(): String = s"Hi $name!"
  }
  
  trait Auditable {
    val createdAt: Long
    def auditInfo() : String = s"Created at: $createdAt"
  }

  case class Transaction(id: Int, amount: BigDecimal, createdAt: Long) extends Auditable

  trait Loggable {
    def log(msg: String): Unit = println(s"[LOG] $msg")
  }

  trait Timestamped {
    def timestamp(): String = java.time.Instant.now().toString
  }

  case class Audit() extends Loggable with Timestamped

  def main(args: Array[String]): Unit = {
    val u = User("Bob")
    println(u.greet()) // Hello, Bob

    val u2 = GreetedUser("Bob")
    println(u2.greet()) // Hi Bob!

    val t = Transaction(123, 200.0, System.currentTimeMillis())
    println(t.auditInfo())

    val a = Audit()
    a.log(a.timestamp())
  }
}
