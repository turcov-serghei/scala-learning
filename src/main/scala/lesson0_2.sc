// Higher-Kinded Types пример

// 1. F[_] — это просто плейсхолдер для любого контейнера
//    Option[_], List[_], Future[_] — все подходят

// 2. Пример: sequence для Option (без Applicative, вручную)
def sequenceOption[A](items: List[Option[A]]): Option[List[A]] =
  items.foldLeft(Some(List.empty[A]): Option[List[A]]) { (acc, item) =>
    for {
      list <- acc    // если acc = None → результат None
      value <- item  // если item = None → результат None
    } yield list :+ value
  }

// Тест 1: все Some
val test1 = List(Some(1), Some(2), Some(3))
sequenceOption(test1)  // Some(List(1, 2, 3))

// Тест 2: есть None
val test2 = List(Some(1), Option(2), Some(3))
sequenceOption(test2)  // None