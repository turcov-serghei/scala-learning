object SequenceExample {

  def sequenceOption[A](items: List[Option[A]]): Option[List[A]] =
    items.foldLeft(Some(List.empty[A]): Option[List[A]]) { (acc, item) =>
      acc.flatMap(list => item.map(value => list :+ value))
    }

  def main(args: Array[String]): Unit = {
    val test1 = List(Some(1), Some(2), Some(3))
    println(sequenceOption(test1))
    // Some(List(1, 2, 3))

    val test2 = List(Some(1), None, Some(3))
    println(sequenceOption(test2))
    // None
  }
}