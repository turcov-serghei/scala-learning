import java.util

def aggregateResults[A](results: List[Option[A]]): Option[List[A]] = {
  if (results.isEmpty) return Some(List.empty)
  val containsNone = results.contains(None)
  var aggregatedList = new util.ArrayList[A]()
  var result = List.empty
  if (containsNone) return Option.empty
  else {
    return Some(results.flatMap(el => el.toList))
  }
}

def aggregateResults[A](results: List[Option[A]]): Option[List[A]] =
  results.foldLeft(Some(List.empty[A]) : Option[List[A]]) { (acc, item) =>
    acc.flatMap(list => item.map(itemValue => list :+ itemValue))
  }

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