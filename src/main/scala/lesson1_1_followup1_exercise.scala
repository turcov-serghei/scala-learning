import scala.math.BigDecimal.RoundingMode

object lesson1_1_followup1_exercise {

  // --- Follow-up 1: withdrawal fee and debit ---

  def withdrawalFee(amount: BigDecimal): Option[BigDecimal] = {
    if (amount <= 0) None
    else if (amount <= 100) Some(BigDecimal("0.00"))
    else if (amount <= 1000) Some((amount * BigDecimal(0.01)).setScale(2, RoundingMode.HALF_UP))
    else Some({
      val minCommission = BigDecimal("10.00")
      val commissionedByPercentage = (amount * BigDecimal(0.50) / BigDecimal(100.00)).setScale(2, RoundingMode.HALF_UP)
      if (commissionedByPercentage < minCommission) minCommission
      else commissionedByPercentage
    })
  }

  def totalDebit(balance: BigDecimal, desiredAmount: BigDecimal): Option[BigDecimal] = {
    for {
      fee <- withdrawalFee(desiredAmount)
      if desiredAmount + fee <= balance
    } yield fee + desiredAmount
  }

  def main(args: Array[String]): Unit = {
    val tc1 = BigDecimal("0")
    val r1 = withdrawalFee(tc1)
    println(s"AC-1: $r1")
    assert(r1.isEmpty)

    val tc2 = BigDecimal("-5")
    val r2 = withdrawalFee(tc2)
    println(s"AC-2: $r2")
    assert(r2.isEmpty)

    val tc3 = BigDecimal("100")
    val r3 = withdrawalFee(tc3)
    println(s"AC-3: $r3")
    assert(r3.contains(BigDecimal("0")))

    val tc4 = BigDecimal("100.01")
    val r4 = withdrawalFee(tc4)
    println(s"AC-4: $r4")
    assert(r4.contains(BigDecimal("1.00")))

    val tc5 = BigDecimal("1000")
    val r5 = withdrawalFee(tc5)
    println(s"AC-5: $r5")
    assert(r5.contains(BigDecimal("10.00")))

    val tc6 = BigDecimal("1000.01")
    val r6 = withdrawalFee(tc6)
    println(s"AC-6: $r6")
    assert(r6.contains(BigDecimal("10.00")))

    val tc7 = BigDecimal("5000")
    val r7 = withdrawalFee(tc7)
    println(s"AC-7: $r7")
    assert(r7.contains(BigDecimal("25.00")))

    val balance8 = BigDecimal("1010")
    val amount8 = BigDecimal("1000")
    val r8 = totalDebit(balance8, amount8)
    println(s"AC-8: $r8")
    assert(r8.contains(BigDecimal("1010.00")))

    val balance9 = BigDecimal("1009.99")
    val amount9 = BigDecimal("1000")
    val r9 = totalDebit(balance9, amount9)
    println(s"AC-9: $r9")
    assert(r9.isEmpty)

    val balance10 = BigDecimal("50")
    val amount10 = BigDecimal("50")
    val r10 = totalDebit(balance10, amount10)
    println(s"AC-10: $r10")
    assert(r10.contains(BigDecimal("50")))

    val balance11 = BigDecimal("500")
    val amount11 = BigDecimal("0")
    val r11 = totalDebit(balance11, amount11)
    println(s"AC-11: $r11")
    assert(r11.isEmpty)

    val balance12 = BigDecimal("0")
    val amount12 = BigDecimal("50")
    val r12 = totalDebit(balance12, amount12)
    println(s"AC-12: $r12")
    assert(r12.isEmpty)
  }
}
