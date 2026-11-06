import lesson1_1_followup1_exercise.totalDebit

object lesson1_1_followup2_exercise {

  def balanceOf(accounts: java.util.Map[String, java.math.BigDecimal], id: String): Option[BigDecimal] = {
    val optJavaBigDecimal = Option(accounts.get(id))
    optJavaBigDecimal.map(javaBigDecimal => BigDecimal(javaBigDecimal))
  }

  def withdrawal(accounts: java.util.Map[String, java.math.BigDecimal], id: String, desiredAmount: BigDecimal): Option[BigDecimal] = {
    balanceOf(accounts, id).flatMap(bal => totalDebit(bal, desiredAmount))
  }

  def main(args: Array[String]): Unit = {
    val accounts = new java.util.HashMap[String, java.math.BigDecimal]()
    accounts.put("acc1", new java.math.BigDecimal("1010"))
    accounts.put("acc2", new java.math.BigDecimal("50"))

    val id1 = "acc1"
    val r1 = balanceOf(accounts, id1)
    println(s"AC-1: $r1")
    assert(r1.contains(BigDecimal("1010")))

    val id2 = "missing"
    val r2 = balanceOf(accounts, id2)
    println(s"AC-2: $r2")
    assert(r2.isEmpty)

    val id3 = "acc1"
    val amount3 = BigDecimal("1000")
    val r3 = withdrawal(accounts, id3, amount3)
    println(s"AC-3: $r3")
    assert(r3.contains(BigDecimal("1010.00")))

    val id4 = "acc1"
    val amount4 = BigDecimal("1001")
    val r4 = withdrawal(accounts, id4, amount4)
    println(s"AC-4: $r4")
    assert(r4.isEmpty)

    val id5 = "missing"
    val amount5 = BigDecimal("10")
    val r5 = withdrawal(accounts, id5, amount5)
    println(s"AC-5: $r5")
    assert(r5.isEmpty)

    val id6 = "acc2"
    val amount6 = BigDecimal("0")
    val r6 = withdrawal(accounts, id6, amount6)
    println(s"AC-6: $r6")
    assert(r6.isEmpty)

    val size7 = accounts.size()
    val balance7 = balanceOf(accounts, "acc1")
    println(s"AC-7: size=$size7, acc1=$balance7")
    assert(size7 == 2)
    assert(balance7.contains(BigDecimal("1010")))

    accounts.put("acc1", new java.math.BigDecimal("2000"))
    val r8 = balanceOf(accounts, "acc1")
    println(s"AC-8: $r8")
    assert(r8.contains(BigDecimal("2000")))
  }
}
