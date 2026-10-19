object lesson0_4 {
  import cats.Monad
  import cats.instances.option._
  import cats.instances.list._

  def process2[F[_], A](fa: F[A])(implicit ev: Monad[F]): F[String] = {
    ev.flatMap(fa)(a => ev.pure(a.toString + "x"))
  }

  def process[F[_]: Monad, A](fa: F[A]): F[String] = {
    val F = implicitly[Monad[F]]
    F.flatMap(fa)(a => F.pure(a.toString + "a"))
  }

  // Разделяем на Some и None:
  def gatherOptions(items: List[Option[Int]]): (List[Int], List[Unit]) =
    items.partitionMap(_.toLeft(()))

  def main(args: Array[String]): Unit = {
    val a = Monad[Option].pure(5)   // Some(5)
    val b = Monad[List].pure(5)     // List(5)

    println(s"Option pure: $a")
    println(s"List pure: $b")

    val optResult = process(Option(42))
    val listResult = process(List(1, 2, 3))

    println(s"process(Option(42)): $optResult")
    println(s"process(List(1,2,3)): $listResult")

    println(process(Option(42)))
    // F = Option
    // fa = Some(42)
    // F = Monad[Option] instance
    //
    // Шаг 1: F.flatMap(Some(42))(a => F.pure(a.toString))
    // Шаг 2: a = 42 (извлечено из Some)
    // Шаг 3: a.toString = "42"
    // Шаг 4: F.pure("42") = Some("42")
    //
    // Результат: Some("42")

    println(process(None))
    // F = Option
    // fa = None
    //
    // Шаг 1: F.flatMap(None)(...) → None
    // (flatMap на None всегда возвращает None — функция не выполняется)
    //
    // Результат: None

    println(process2(List(1, 2, 3)))
    // F = List
    // fa = List(1, 2, 3)
    //
    // Шаг 1: F.flatMap(List(1, 2, 3))(a => F.pure(a.toString))
    // Шаг 2: для каждого элемента:
    //   a = 1  → F.pure("1")  = List("1")
    //   a = 2  → F.pure("2")  = List("2")
    //   a = 3  → F.pure("3")  = List("3")
    // Шаг 3: flatMap объединяет все List в один
    //
    // Результат: List("1", "2", "3")

    // Пример:
    val items = List(Some(1), None, Some(3))
    val (values, noneCount) = gatherOptions(items)
    // values = List(1, 3)
    // noneCount = List((), ()) — два Unit, потому что два None
  }
}
