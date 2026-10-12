# Exercise Code Templates

This file contains the code templates for exercise levels. Referenced by Rule 11 in `.junie/LEARNING_STYLE.md`.

---

## Level 1 (Skeleton)

Function signature with `= ???` (completely empty body). `main` contains ONLY human-readable comments for each AC — no active code, no asserts, no variables. User writes everything: input variables, function calls, assert, println.

Golden standard:

```scala
object lesson0_2_exercise {
  def aggregateResults[A](results: List[Option[A]]): Option[List[A]] = ???
  def main(args: Array[String]): Unit = {
    // AC-1 (All Valid): Some(List(100.0, 250.0, 50.0))
    // AC-2 (Empty): Some(List())
    // AC-3 (Single Success): Some(List(500.0))
    // AC-4 (Single Failure): None
    // AC-5 (Fail Start): None
    // AC-6 (Fail Middle): None
    // AC-7 (Fail End): None
    // AC-8 (Multiple Failures): None
  }
}
```

---

## Level 2 (Test skeleton) — Golden Standard

Function signature with `= ???` (completely empty body). `main` declares each test case as a separate `val tcN = ...` variable, then calls the function and asserts on a separate line. Never inline list creation inside assert. No `println`.

Golden standard:

```scala
object lesson0_2_exercise {
  def aggregateResults[A](results: List[Option[A]]): Option[List[A]] = ???
  def main(args: Array[String]): Unit = {
    val tc1 = List(Some(BigDecimal(100.0)), Some(BigDecimal(250.0)), Some(BigDecimal(50.0)))
    assert(aggregateResults(tc1) == Some(List(BigDecimal(100.0), BigDecimal(250.0), BigDecimal(50.0))))
    val tc2: List[Option[BigDecimal]] = List.empty
    assert(aggregateResults(tc2) == Some(List()))
    val tc3 = List(Some(BigDecimal(500.0)))
    assert(aggregateResults(tc3) == Some(List(BigDecimal(500.0))))
    val tc4 = List(None)
    assert(aggregateResults(tc4) == None)
    val tc5 = List(None, Some(BigDecimal(200.0)), Some(BigDecimal(300.0)))
    assert(aggregateResults(tc5) == None)
    val tc6 = List(Some(BigDecimal(100.0)), None, Some(BigDecimal(300.0)))
    assert(aggregateResults(tc6) == None)
    val tc7 = List(Some(BigDecimal(100.0)), Some(BigDecimal(200.0)), None)
    assert(aggregateResults(tc7) == None)
    val tc8 = List(None, None)
    assert(aggregateResults(tc8) == None)
  }
}
```

---

## Level 3 (Full)

Level 2 + `val rN = function(tcN)` + `println(s"AC-N: $rN")` before each `assert` for visual debugging. User only implements the core logic.

Example (continuation of Level 2):

```scala
object lesson0_2_exercise {
  def aggregateResults[A](results: List[Option[A]]): Option[List[A]] = ???
  def main(args: Array[String]): Unit = {
    val tc1 = List(Some(BigDecimal(100.0)), Some(BigDecimal(250.0)), Some(BigDecimal(50.0)))
    val r1 = aggregateResults(tc1)
    println(s"AC-1: $r1")
    assert(r1 == Some(List(BigDecimal(100.0), BigDecimal(250.0), BigDecimal(50.0))))

    val tc2: List[Option[BigDecimal]] = List.empty
    val r2 = aggregateResults(tc2)
    println(s"AC-2: $r2")
    assert(r2 == Some(List()))

    val tc3 = List(Some(BigDecimal(500.0)))
    val r3 = aggregateResults(tc3)
    println(s"AC-3: $r3")
    assert(r3 == Some(List(BigDecimal(500.0))))

    val tc4 = List(None)
    val r4 = aggregateResults(tc4)
    println(s"AC-4: $r4")
    assert(r4 == None)

    val tc5 = List(None, Some(BigDecimal(200.0)), Some(BigDecimal(300.0)))
    val r5 = aggregateResults(tc5)
    println(s"AC-5: $r5")
    assert(r5 == None)

    val tc6 = List(Some(BigDecimal(100.0)), None, Some(BigDecimal(300.0)))
    val r6 = aggregateResults(tc6)
    println(s"AC-6: $r6")
    assert(r6 == None)

    val tc7 = List(Some(BigDecimal(100.0)), Some(BigDecimal(200.0)), None)
    val r7 = aggregateResults(tc7)
    println(s"AC-7: $r7")
    assert(r7 == None)

    val tc8 = List(None, None)
    val r8 = aggregateResults(tc8)
    println(s"AC-8: $r8")
    assert(r8 == None)
  }
}
```
