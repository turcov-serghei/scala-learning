object lesson1_1_followup3_exercise {

  def normalizeCountry(country: String): String = country.trim.toUpperCase()

  def limitFor(country: String): BigDecimal =
    if (List("ZZ","WW").contains(country)) BigDecimal("1000.00")
    else BigDecimal("10000.00")

  // Written answer: what type would the compiler infer if the explicit result type
  // were removed and the last branch `else None` were replaced by a string without `else`? Why?
  // it is Any type - don't know why, maybe compilator sees none covered cases as Unit. I'm not sure
  def riskCheck(amount: BigDecimal, country: String): Option[String] = {
    val countryCode = normalizeCountry(country)
    if (countryCode == "") Some("invalid country")
    else if (amount <= 0) Some("invalid amount")
    else if (countryCode == "XX" || countryCode == "YY") Some("blocked country")
    else if (amount > limitFor(countryCode)) Some("amount over limit")
    else None
  }

  def audit(amount: BigDecimal, country: String, sink: java.util.List[String]): Unit = {
    val countryCode = normalizeCountry(country)
    val limit = limitFor(countryCode)
    if (amount > limit) sink.add(s"OVER LIMIT: $amount ($countryCode)")
  }

  def main(args: Array[String]): Unit = {
    val country1 = " de "
    val r1 = normalizeCountry(country1)
    println(s"AC-1: $r1")
    assert(r1 == "DE")

    val country2 = "xx"
    val r2 = normalizeCountry(country2)
    println(s"AC-2: $r2")
    assert(r2 == "XX")

    val country3 = "DE"
    val r3 = limitFor(country3)
    println(s"AC-3: $r3")
    assert(r3 == BigDecimal("10000"))

    val country4 = "ZZ"
    val r4 = limitFor(country4)
    println(s"AC-4: $r4")
    assert(r4 == BigDecimal("1000"))

    val amount5 = BigDecimal("500")
    val country5 = "DE"
    val r5 = riskCheck(amount5, country5)
    println(s"AC-5: $r5")
    assert(r5.isEmpty)

    val amount6 = BigDecimal("10000")
    val country6 = "DE"
    val r6 = riskCheck(amount6, country6)
    println(s"AC-6: $r6")
    assert(r6.isEmpty)

    val amount7 = BigDecimal("10000.01")
    val country7 = "DE"
    val r7 = riskCheck(amount7, country7)
    println(s"AC-7: $r7")
    assert(r7.contains("amount over limit"))

    val amount8 = BigDecimal("1000")
    val country8 = "ZZ"
    val r8 = riskCheck(amount8, country8)
    println(s"AC-8: $r8")
    assert(r8.isEmpty)

    val amount9 = BigDecimal("1000.01")
    val country9 = "ZZ"
    val r9 = riskCheck(amount9, country9)
    println(s"AC-9: $r9")
    assert(r9.contains("amount over limit"))

    val amount10 = BigDecimal("50")
    val country10 = "xx"
    val r10 = riskCheck(amount10, country10)
    println(s"AC-10: $r10")
    assert(r10.contains("blocked country"))

    val amount11 = BigDecimal("50")
    val country11 = " YY "
    val r11 = riskCheck(amount11, country11)
    println(s"AC-11: $r11")
    assert(r11.contains("blocked country"))

    val amount12 = BigDecimal("50")
    val country12 = "  "
    val r12 = riskCheck(amount12, country12)
    println(s"AC-12: $r12")
    assert(r12.contains("invalid country"))

    val amount13 = BigDecimal("0")
    val country13 = "DE"
    val r13 = riskCheck(amount13, country13)
    println(s"AC-13: $r13")
    assert(r13.contains("invalid amount"))

    val amount14 = BigDecimal("-5")
    val country14 = "XX"
    val r14 = riskCheck(amount14, country14)
    println(s"AC-14: $r14")
    assert(r14.contains("invalid amount"))

    val amount15 = BigDecimal("0")
    val country15 = ""
    val r15 = riskCheck(amount15, country15)
    println(s"AC-15: $r15")
    assert(r15.contains("invalid country"))

    val amount16 = BigDecimal("1000.01")
    val country16 = "zz"
    val sink16 = new java.util.ArrayList[String]()
    val r16 = audit(amount16, country16, sink16)
    println(s"AC-16: $r16, sink=$sink16")
    assert(sink16.size() == 1)
    assert(sink16.get(0) == "OVER LIMIT: 1000.01 (ZZ)")

    val amount17 = BigDecimal("1000")
    val country17 = "ZZ"
    val sink17 = new java.util.ArrayList[String]()
    val r17 = audit(amount17, country17, sink17)
    println(s"AC-17: $r17, sink=$sink17")
    assert(sink17.isEmpty)

    val amount18 = BigDecimal("5000")
    val country18 = "DE"
    val sink18 = new java.util.ArrayList[String]()
    val r18 = audit(amount18, country18, sink18)
    println(s"AC-18: $r18, sink=$sink18")
    assert(sink18.isEmpty)

    val amount19 = BigDecimal("20000")
    val country19 = "DE"
    val sink19 = new java.util.ArrayList[String]()
    val r19a = audit(amount19, country19, sink19)
    val r19b = audit(amount19, country19, sink19)
    println(s"AC-19: $r19a, $r19b, sink=$sink19")
    assert(sink19.size() == 2)

  }
}
