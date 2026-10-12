
  trait Sort[T] {
    def sort(value1: T, value2: T) : Int
  }

  case class Person(name: String, age: Int)

  implicit val ageSorter: Sort[Person] =
    (value1, value2) => value2.age.compareTo(value1.age)


  def sort[T](people: List[T])(implicit sort: Sort[T]): List[T] =
    people.sorted((x, y) => sort.sort(x, y))

    val person1 = Person("Bob", 45)
    val person2 = Person("David", 8)
    val person3 = Person("Olga", 41)
    val person4 = Person("Mark", 5)
    val person5 = Person("Anna", 13)

    val list : List[Person] = List(person1, person2, person3, person4, person5)

    var sortedList = sort(list)

    print(sortedList)


