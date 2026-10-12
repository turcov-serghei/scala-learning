object lesson0_2_exercise {

  trait FlatMap[F[_]] {
    def pure[A](a: A) : F[A]
    def map[A, B](fa: F[A])(fb: A => B): F[B]
    def flatMap[A, B](fa: F[A])(fb: A => F[B]): F[B]
  }

  implicit val optionFlatMap: FlatMap[Option] = new FlatMap[Option] {
    override def pure[A](a: A): Option[A] = Some(a)

    override def map[A, B](fa: Option[A])(fb: A => B): Option[B] =
      //if (fa.isEmpty) None else Some(fb.apply(fa.get)) //imperative style
      fa.map(fb) //- functional style

    override def flatMap[A, B](fa: Option[A])(fb: A => Option[B]): Option[B] =
      //if (fa.isEmpty) None else fb.apply(fa.get) //imperative style
      fa.flatMap(fb) //- functional style
  }

  def aggregateResultsImperativeStyle[A](results: List[Option[A]]): Option[List[A]] = {
    if (results.isEmpty) return Some(List.empty)
    if (results.contains(None)) Option.empty
    else Some(results.flatMap(el => el.toList))
  }

  def aggregateResultsWithOption[A](results: List[Option[A]]): Option[List[A]] =
    results.foldLeft(Some(List.empty[A]) : Option[List[A]]) {
      (acc, item) => acc.flatMap(list => item.map(itemValue => list :+ itemValue))
    }

  def aggregateResults[F[_], A](results: List[F[A]])(implicit f: FlatMap[F]): F[List[A]] =
      results.foldLeft(f.pure(List.empty[A]) : F[List[A]]) {
        (acc, item) => f.flatMap(acc)(list => f.map(item)(itemValue => list :+ itemValue))
  }

  def main(args: Array[String]): Unit = {
    val tc1 : List[Option[BigDecimal]] = List(Some(BigDecimal(100.0)), Some(BigDecimal(250.0)), Some(BigDecimal(50.0)))
    val r1 = aggregateResults(tc1)
    println(s"AC-1: $r1")
    assert(r1 == Some(List(BigDecimal(100.0), BigDecimal(250.0), BigDecimal(50.0))))

    val tc2: List[Option[BigDecimal]] = List.empty
    val r2 = aggregateResults(tc2)
    println(s"AC-2: $r2")
    assert(r2 == Some(List()))

    val tc3 : List[Option[BigDecimal]]= List(Some(BigDecimal(500.0)))
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

    println("All AC passed!")
  }
}