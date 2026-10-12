// ============================================================================
// Follow-up Micro-Exercises: Lesson 0.4 — Monads Explained
// ============================================================================
//
// Эти мини-упражнения основаны на протоколе lesson0_3 и покрывают:
//
// Из lesson0_3_protocol.md (Exercise Weak Spots):
//   1. foldLeft — "кошмар", "грузная структура" (CRITICAL затык)
//   2. For-comprehension на List — непонятно как применить для агрегации
//   3. Self-doubt — правильный выбор инструмента, но сомнения
//
// Из lesson0_3_protocol.md (Calibration Notes):
//   - foldLeft требует: простых примеров перед сложными
//   - Визуальных диаграмм accumulator flow step-by-step
//   - Java эквивалента (Stream.reduce()) для якоря понимания
//
// Интеграция prior knowledge (Rule 29):
//   - Из Lesson 0.1: implicit параметры
//   - Из Lesson 0.2: flatMap/map на Option, F[_]
//   - Из Lesson 0.3: for-comprehension desugaring
//
// ============================================================================

object lesson0_4_followup_exercises {

  // ============================================================================
  // Micro-Exercise 1: foldLeft — Java Stream.reduce() → Scala
  // ============================================================================
  //
  // Цель: Показать что foldLeft — это то же самое что Java Stream.reduce()
  // Затык: foldLeft кажется "грузной структурой"
  // Решение: Начать с самого простого foldLeft — сумма чисел
  //
  // Java аналог:
  //   List<Integer> numbers = List.of(1, 2, 3, 4);
  //   int sum = numbers.stream().reduce(0, (acc, item) -> acc + item);
  //   // Результат: 10
  //
  // Визуальная диаграмма:
  //   Step 0:  acc = 0 (initial)
  //   Step 1:  acc = 0 + 1 = 1
  //   Step 2:  acc = 1 + 2 = 3
  //   Step 3:  acc = 3 + 3 = 6
  //   Step 4:  acc = 6 + 4 = 10
  //
  // Реализуйте sumNumbers используя foldLeft.
  // Начальное значение аккумулятора: 0
  // Операция: (acc, item) => acc + item
  //
  def sumNumbers(numbers: List[Int]): Int = ???

  // AC-1: sumNumbers(List(1, 2, 3, 4)) == 10
  // AC-2: sumNumbers(List.empty[Int]) == 0
  // AC-3: sumNumbers(List(5)) == 5

  // ============================================================================
  // Micro-Exercise 2: foldLeft — произведение чисел
  // ============================================================================
  //
  // Цель: Укрепить понимание что начальное значение определяет операцию
  // Затык: Понимание роли initial value в foldLeft
  //
  // Java аналог:
  //   List<Integer> numbers = List.of(1, 2, 3, 4);
  //   int product = numbers.stream().reduce(1, (acc, item) -> acc * item);
  //   // Результат: 24
  //
  // Визуальная диаграмма:
  //   Step 0:  acc = 1 (initial — единица для умножения!)
  //   Step 1:  acc = 1 * 1 = 1
  //   Step 2:  acc = 1 * 2 = 2
  //   Step 3:  acc = 2 * 3 = 6
  //   Step 4:  acc = 6 * 4 = 24
  //
  // Реализуйте productNumbers используя foldLeft.
  // Начальное значение аккумулятора: 1 (не 0!)
  // Операция: (acc, item) => acc * item
  //
  def productNumbers(numbers: List[Int]): Int = ???

  // AC-4: productNumbers(List(1, 2, 3, 4)) == 24
  // AC-5: productNumbers(List.empty[Int]) == 1
  // AC-6: productNumbers(List(7)) == 7

  // ============================================================================
  // Micro-Exercise 3: foldLeft — агрегация в List
  // ============================================================================
  //
  // Цель: Показать foldLeft с List как аккумулятором
  // Затык: Понимание что аккумулятор может быть любым типом, не только Int
  //
  // Java аналог:
  //   List<String> words = List.of("a", "b", "c");
  //   List<String> result = words.stream()
  //     .reduce(new ArrayList<>(), (acc, item) -> {
  //       List<String> newAcc = new ArrayList<>(acc);
  //       newAcc.add(item.toUpperCase());
  //       return newAcc;
  //     });
  //   // Результат: ["A", "B", "C"]
  //
  // Визуальная диаграмма:
  //   Step 0:  acc = List() (initial)
  //   Step 1:  acc = List() :+ "A" = List("A")
  //   Step 2:  acc = List("A") :+ "B" = List("A", "B")
  //   Step 3:  acc = List("A", "B") :+ "C" = List("A", "B", "C")
  //
  // Реализуйте toUpperCaseList используя foldLeft.
  // Начальное значение: List.empty[String]
  // Операция: добавить item.toUpperCase() в конец списка
  //
  def toUpperCaseList(words: List[String]): List[String] = ???

  // AC-7: toUpperCaseList(List("a", "b", "c")) == List("A", "B", "C")
  // AC-8: toUpperCaseList(List.empty[String]) == List()

  // ============================================================================
  // Micro-Exercise 4: foldLeft — Option[List[A]] агрегация
  // ============================================================================
  //
  // Цель: Тот самый "кошмар" из lesson0_3, но с визуальной диаграммой
  // Затык: foldLeft с Option[List[A]] — "грузная структура"
  // Решение: Разложить по шагам с комментариями
  //
  // Java аналог:
  //   List<Optional<Integer>> items = Arrays.asList(
  //     Optional.of(1), Optional.of(2), Optional.empty()
  //   );
  //   // Если хотя бы один Optional пустой — результат пустой
  //   // Иначе — собрать все значения в List
  //
  // Визуальная диаграмма:
  //   Input: List(Some(1), Some(2), None)
  //
  //   Step 0:  acc = Some(List())
  //
  //   Step 1:  item = Some(1)
  //            acc.flatMap(list => item.map(a => list :+ a))
  //            = Some(List(1))
  //            acc = Some(List(1))
  //
  //   Step 2:  item = Some(2)
  //            acc.flatMap(list => item.map(a => list :+ a))
  //            = Some(List(1, 2))
  //            acc = Some(List(1, 2))
  //
  //   Step 3:  item = None
  //            acc.flatMap(list => item.map(a => list :+ a))
  //            = None  (flatMap на None всегда даёт None)
  //            acc = None
  //
  //   Результат: None
  //
  // Реализуйте aggregateOptions используя foldLeft.
  // Начальное значение: Some(List.empty[Int])
  // Операция: acc.flatMap(list => item.map(a => list :+ a))
  //
  // Ключевое понимание: Как только acc стал None, он остаётся None.
  // flatMap на None всегда даёт None — цепочка "разорвана".
  //
  def aggregateOptions(items: List[Option[Int]]): Option[List[Int]] = ???

  // AC-9: aggregateOptions(List(Some(1), Some(2), Some(3))) == Some(List(1, 2, 3))
  // AC-10: aggregateOptions(List.empty[Option[Int]]) == Some(List())
  // AC-11: aggregateOptions(List(Some(1), None, Some(3))) == None
  // AC-12: aggregateOptions(List(None)) == None

  // ============================================================================
  // Micro-Exercise 5: for-comprehension внутри foldLeft
  // ============================================================================
  //
  // Цель: Показать что for-comprehension делает foldLeft читаемым
  // Затык: For-comprehension на List в processBatch — непонятно как применить
  // Решение: for-comprehension внутри foldLeft body — это то же самое что flatMap/map
  //
  // Вместо:
  //   acc.flatMap(list => item.map(a => list :+ a))
  //
  // Можно записать как:
  //   for {
  //     list <- acc      // извлекаем список из Option
  //     a <- item        // извлекаем элемент из Option
  //   } yield list :+ a  // добавляем элемент в список
  //
  // Реализуйте aggregateOptionsForComp используя foldLeft + for-comprehension.
  // Результат должен быть идентичен aggregateOptions.
  //
  def aggregateOptionsForComp(items: List[Option[Int]]): Option[List[Int]] = ???

  // AC-13: aggregateOptionsForComp(List(Some(1), Some(2))) == Some(List(1, 2))
  // AC-14: aggregateOptionsForComp(List(Some(1), None)) == None

  // ============================================================================
  // Micro-Exercise 6: Monad Law — Left Identity
  // ============================================================================
  //
  // Цель: Проверить Left Identity law на практике
  // Теория: pure(x).flatMap(f) == f(x)
  //
  // Java аналог:
  //   Optional.of(5).flatMap(x -> Optional.of(x * 2))
  //   // равно
  //   Optional.of(5 * 2)
  //
  // Реализуйте две функции и проверьте что они дают одинаковый результат.
  //
  def leftIdentitySideA(x: Int): Option[Int] =
    Some(x).flatMap(a => Some(a * 2))

  def leftIdentitySideB(x: Int): Option[Int] = ???
  // Подсказка: f(x) где f = (a: Int) => Some(a * 2)

  // AC-15: leftIdentitySideA(5) == leftIdentitySideB(5)
  // AC-16: leftIdentitySideA(0) == leftIdentitySideB(0)

  // ============================================================================
  // Micro-Exercise 7: Monad Law — Right Identity
  // ============================================================================
  //
  // Цель: Проверить Right Identity law на практике
  // Теория: fa.flatMap(pure) == fa
  //
  // Java аналог:
  //   Optional<Integer> opt = Optional.of(5);
  //   opt.flatMap(Optional::of)
  //   // равно
  //   opt  // Optional.of(5)
  //
  // Реализуйте rightIdentitySideB.
  //
  def rightIdentitySideA(opt: Option[Int]): Option[Int] =
    opt.flatMap(x => Some(x))

  def rightIdentitySideB(opt: Option[Int]): Option[Int] = ???
  // Подсказка: Right Identity говорит что результат == fa

  // AC-17: rightIdentitySideA(Some(5)) == rightIdentitySideB(Some(5))
  // AC-18: rightIdentitySideA(None) == rightIdentitySideB(None)

  // ============================================================================
  // Main: Test cases (commented out — uncomment after implementation)
  // ============================================================================

  def main(args: Array[String]): Unit = {

    // === Micro-Exercise 1: sumNumbers ===
    // assert(sumNumbers(List(1, 2, 3, 4)) == 10)
    // assert(sumNumbers(List.empty[Int]) == 0)
    // assert(sumNumbers(List(5)) == 5)

    // === Micro-Exercise 2: productNumbers ===
    // assert(productNumbers(List(1, 2, 3, 4)) == 24)
    // assert(productNumbers(List.empty[Int]) == 1)
    // assert(productNumbers(List(7)) == 7)

    // === Micro-Exercise 3: toUpperCaseList ===
    // assert(toUpperCaseList(List("a", "b", "c")) == List("A", "B", "C"))
    // assert(toUpperCaseList(List.empty[String]) == List())

    // === Micro-Exercise 4: aggregateOptions ===
    // assert(aggregateOptions(List(Some(1), Some(2), Some(3))) == Some(List(1, 2, 3)))
    // assert(aggregateOptions(List.empty[Option[Int]]) == Some(List()))
    // assert(aggregateOptions(List(Some(1), None, Some(3))) == None)
    // assert(aggregateOptions(List(None)) == None)

    // === Micro-Exercise 5: aggregateOptionsForComp ===
    // assert(aggregateOptionsForComp(List(Some(1), Some(2))) == Some(List(1, 2)))
    // assert(aggregateOptionsForComp(List(Some(1), None)) == None)

    // === Micro-Exercise 6: Left Identity ===
    // assert(leftIdentitySideA(5) == leftIdentitySideB(5))
    // assert(leftIdentitySideA(0) == leftIdentitySideB(0))

    // === Micro-Exercise 7: Right Identity ===
    // assert(rightIdentitySideA(Some(5)) == rightIdentitySideB(Some(5)))
    // assert(rightIdentitySideA(None) == rightIdentitySideB(None))
  }
}
