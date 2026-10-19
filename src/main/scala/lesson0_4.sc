import cats.Monad
import cats.instances.option._
import cats.instances.list._


// Единый вызов для любого Monad:
Monad[Option].pure(5)   // Some(5)
Monad[List].pure(5)     // List(5)

def process2[F[_], A](fa: F[A])(implicit ev: Monad[F]): F[String] = {
  val F = implicitly[Monad[F]]
  F.flatMap(fa)(a => F.pure(a.toString))
}

def process[F[_]: Monad, A](fa: F[A]): F[String] = {
  val F = implicitly[Monad[F]]
  F.flatMap(fa)(a => F.pure(a.toString))
}

process(Option(42))


// ========================================
// Law 1: Left Identity
// pure(x).flatMap(f)  ==  f(x)
// ========================================

val x = 5
val f: Int => Option[Int] = a => Option(a * 2)

// Side A: pure(x).flatMap(f)
val leftA = Monad[Option].pure(x).flatMap(f)
// Side B: f(x)
val leftB = f(x)
// Проверка:
assert(leftA == leftB)  // Some(10) == Some(10)


// ========================================
// Law 2: Right Identity
// fa.flatMap(pure)  ==  fa
// ========================================

val fa: Option[Int] = Option(5)

// Side A: fa.flatMap(pure)
val rightA = fa.flatMap(x => Monad[Option].pure(x))
// Side B: fa
val rightB = fa
// Проверка:
assert(rightA == rightB)  // Some(5) == Some(5)

// Проверка с None:
val noneFa: Option[Int] = None
val rightNoneA = noneFa.flatMap(x => Monad[Option].pure(x))
val rightNoneB = noneFa
assert(rightNoneA == rightNoneB)  // None == None

// ========================================
// Law 3: Associativity
// fa.flatMap(f).flatMap(g)  ==  fa.flatMap(x => f(x).flatMap(g))
// ========================================

val fa3: Option[Int] = Option(5)
val f3: Int => Option[Int] = a => Option(a * 2)
val g3: Int => Option[Int] = b => Option(b + 1)

// Side A: последовательный
val assocA = fa3.flatMap(f3).flatMap(g3)
// Side B: вложенный
val assocB = fa3.flatMap(a => f3(a).flatMap(g3))
// Проверка:
assert(assocA == assocB)  // Some(11) == Some(11)

// Проверка с None посередине:
val gBreak: Int => Option[Int] = _ => None
val assocBreakA = fa3.flatMap(f3).flatMap(gBreak)
val assocBreakB = fa3.flatMap(a => f3(a).flatMap(gBreak))
assert(assocBreakA == assocBreakB)  // None == None


// ========================================
// sequence — универсальная функция для любого Monad
// ========================================

def sequence[F[_]: Monad, A](items: List[F[A]]): F[List[A]] =
  items.foldLeft(Monad[F].pure(List.empty[A])) { (acc, item) =>
    Monad[F].flatMap(acc) { list =>
      Monad[F].map(item)(a => list :+ a)
    }
  }

// ========================================
// Пример 1: List[Option[Int]] → Option[List[Int]]
// ========================================

val options: List[Option[Int]] = List(Some(1), Some(2), Some(3))
val result1: Option[List[Int]] = sequence(options)
// Результат: Some(List(1, 2, 3))

val optionsWithNone: List[Option[Int]] = List(Some(1), None, Some(3))
val result2: Option[List[Int]] = sequence(optionsWithNone)
// Результат: None

// ========================================
// Пример 2: List[List[Int]] → List[List[Int]]
// ========================================

val lists: List[List[Int]] = List(List(1, 2), List(3), List(4, 5))
val result3: List[List[Int]] = sequence(lists)
// Результат: List(List(1, 3, 4), List(1, 3, 5), List(2, 3, 4), List(2, 3, 5))

// ========================================
// Пример 3: List[Either[String, Int]] → Either[String, List[Int]]
// ========================================

val eithers: List[Either[String, Int]] = List(
  Right(1), Right(2), Right(3)
)
val result4: Either[String, List[Int]] = sequence(eithers)
// Результат: Right(List(1, 2, 3))

val eithersWithError: List[Either[String, Int]] = List(
  Right(1), Left("error"), Right(3)
)
val result5: Either[String, List[Int]] = sequence(eithersWithError)


// Вместо sequence, который даёт None при первом None:
// List(Some(1), None, Some(3)) → None

// Разделяем на Some и None:
def gatherOptions(items: List[Option[Int]]): (List[Int], List[Unit]) =
  items.partitionMap(_.toLeft(()))

// Пример:
val items = List(Some(1), None, Some(3))
val (values, noneCount) = gatherOptions(items)
// values = List(1, 3)
// noneCount = List((), ()) — два Unit, потому что два None

def gatherEithers(items: List[Either[String, Int]]): (List[String], List[Int]) =
  items.partitionMap(identity)

// Пример:
val items = List(
  Right(1), Left("error1"), Right(3), Left("error2")
)
val (errors, values) = gatherEithers(items)
// values = List(1, 3)
// errors = List("error1", "error2")