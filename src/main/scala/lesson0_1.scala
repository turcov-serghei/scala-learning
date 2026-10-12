object lesson0_1 {
  trait Sort[T] {
    def sort(value1: T, value2: T) : Int
  }

  case class Person(name: String, age: Int)

  implicit val ageSorter: Sort[Person] = new Sort[Person] {
    override def sort(value1: Person, value2: Person) : Int =
      value1.age.compareTo(value2.age)
  }

  def sort[T](people: List[T])(implicit sort: Sort[T]): List[T] =
    people.sorted(new Ordering[T] {
      override def compare(x: T, y: T): Int = sort.sort(x, y)
    })

  def main(args: Array[String]): Unit = {
    val person1 = new Person("Bob", 45)
    val person2 = new Person("David", 8)
    val person3 = new Person("Olga", 41)
    val person4 = new Person("Mark", 5)
    val person5 = new Person("Anna", 13)

    val list : List[Person] = List(person1, person2, person3, person4, person5)

    var sortedList = sort(list)

    print(sortedList)
  }
}
