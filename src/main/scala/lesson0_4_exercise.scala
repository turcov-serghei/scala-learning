// ============================================================================
// Exercise 0.4 — Wallet Settlement Aggregator (Monads: foldLeft + Either)
// ============================================================================
//
// Business Context:
//   A B2B Trade Finance / Digital Wallet platform processes settlement
//   batches of wallet credit operations. Each credit amount either succeeds
//   (Right(amount)) or fails with a SettlementError (Left). The system
//   must aggregate ALL successful credits OR fail-fast on the first error.
//
//   Java analog: reduce a List<Optional<Integer>> and stop on first empty.
//
// Prior Knowledge Integration (Rule 18):
//   - Lesson 0.1: implicit parameters (SettlementConfig DI)
//   - Lesson 0.2: F[_] and Monad operations (flatMap, map)
//   - Lesson 0.3: foldLeft + Option[List[A]] aggregation
//   - Lesson 0.4: Monad pattern, Monad laws, Either as Monad, for-comp body
//
// ============================================================================

object lesson0_4_exercise {

  // ============================================================================
  // Domain Types — DO NOT MODIFY
  // ============================================================================

  sealed trait SettlementError
  case class InsufficientFunds(walletId: String, balance: Int, requested: Int) extends SettlementError
  case class FrozenAccount(walletId: String) extends SettlementError
  case class ThresholdExceeded(credit: Int, threshold: Int) extends SettlementError

  case class SettlementConfig(maxCreditThreshold: Int)

  // ============================================================================
  // Function Signatures — Implement the bodies (replace ???)
  // ============================================================================

  // ----------------------------------------------------------------------------
  // AC 1-8: aggregateCredits — foldLeft + Either as Monad
  // ----------------------------------------------------------------------------
  //
  // Aggregates a list of validated credit operations, fail-fast on first error.
  //
  // Core concept: Either is a Monad — it supports flatMap and pure (Right).
  //   pure(x)  = Right(x)
  //   flatMap  = Either.flatMap (built-in)
  //   yield    = Either's case: Right(y) propagates, Left(e) short-circuits
  //
  // foldLeft accumulator: Either[SettlementError, List[Int]]
  //   Initial value: Right(List.empty[Int])
  //
  // Visual foldLeft diagram for input: List(Right(100), Right(50), Left(err))
  //
  //   Step 0:  acc = Right(List())
  //
  //   Step 1:  item = Right(100)
  //            acc.flatMap(list => item.map(a => list :+ a))
  //            = Right(List()).flatMap(list => Right(100).map(a => List() :+ 100))
  //            = Right(List()).flatMap(list => Right(List(100)))
  //            = Right(List(100))
  //            acc = Right(List(100))
  //
  //   Step 2:  item = Right(50)
  //            acc.flatMap(list => item.map(a => list :+ a))
  //            = Right(List(100)).flatMap(list => Right(50).map(a => List(100) :+ 50))
  //            = Right(List(100)).flatMap(list => Right(List(100, 50)))
  //            = Right(List(100, 50))
  //            acc = Right(List(100, 50))
  //
  //   Step 3:  item = Left(err)
  //            acc.flatMap(list => item.map(a => list :+ a))
  //            = Right(List(100, 50)).flatMap(list => Left(err))
  //            = Left(err)   ← flatMap on Left always returns Left — chain breaks
  //            acc = Left(err)
  //
  //   Result: Left(err)
  //
  // Key insight: flatMap on Left ALWAYS returns Left. Once the accumulator
  // becomes Left, it stays Left for all remaining items.
  // This is why foldLeft + Either gives fail-fast semantics for free.
  //
  // After implementing, try rewriting the foldLeft body using for-comprehension
  // (replacing flatMap + map, NOT replacing foldLeft itself).
  // See aggregateCreditsForComp below.
  //


  def aggregateCredits(items: List[Either[SettlementError, Int]]): Either[SettlementError, List[Int]] =
    items.foldLeft(Right(List.empty[Int]) : Either[SettlementError, List[Int]]) {
      (acc , item) => acc.flatMap(list =>
        item.map(amount =>  list :+ amount)
      )
    }

  // ----------------------------------------------------------------------------
  // AC 9: validateCredit — implicit config threshold check
  // ----------------------------------------------------------------------------
  //
  // Uses implicit parameter (Lesson 0.1 — implicit / dependency injection).
  // If credit > config.maxCreditThreshold → Left(ThresholdExceeded)
  // Otherwise → Right(credit)
  //
  // Usage in main:
  //   implicit val config = SettlementConfig(250)
  //   validateCredit(300)  // → Left(ThresholdExceeded(300, 250))
  //   validateCredit(100)  // → Right(100)
  //
  def validateCredit(credit: Int)(implicit config: SettlementConfig): Either[SettlementError, Int] =
    if (credit > config.maxCreditThreshold) Left(ThresholdExceeded(credit, config.maxCreditThreshold))
    else Right(credit)

  // ----------------------------------------------------------------------------
  // AC 10: Monad Law — Left Identity
  // ----------------------------------------------------------------------------
  //
  //   Law: pure(x).flatMap(f) == f(x)
  //   For Either: Right(x).flatMap(f) == f(x)
  //
  //   Verify by computing both sides and returning true if equal.
  //
  //   Example:
  //     f = (x: Int) => Right(x * 2)
  //     leftIdentityCheck(5, f)
  //       sideA: Right(5).flatMap(f) = Right(10)
  //       sideB: f(5) = Right(10)
  //       → true
  //
  def leftIdentityCheck(x: Int, f: Int => Either[SettlementError, Int]): Boolean =
    f(x) == Right(x).flatMap(f)

  // ----------------------------------------------------------------------------
  // AC 11: Monad Law — Right Identity
  // ----------------------------------------------------------------------------
  //
  //   Law: fa.flatMap(pure) == fa
  //   For Either: fa.flatMap(x => Right(x)) == fa
  //
  //   Verify by computing both sides and returning true if equal.
  //
  //   Example:
  //     rightIdentityCheck(Right(42))
  //       sideA: Right(42).flatMap(x => Right(x)) = Right(42)
  //       sideB: Right(42)
  //       → true
  //
  def rightIdentityCheck(fa: Either[SettlementError, Int]): Boolean =
    fa.flatMap(x => Right(x)) == fa

  // ----------------------------------------------------------------------------
  // Optional: aggregateCreditsForComp — for-comprehension body in foldLeft
  // ----------------------------------------------------------------------------
  //
  // Same logic as aggregateCredits but the foldLeft body uses for-comprehension
  // to replace flatMap + map (Theory Segment 6 — foldLeft body refresh).
  //
  //   Instead of: acc.flatMap(list => item.map(a => list :+ a))
  //   Use:        for { list <- acc; a <- item } yield list :+ a
  //
  //   foldLeft itself is NOT replaced — only its body.
  //
  def aggregateCreditsForComp(items: List[Either[SettlementError, Int]]): Either[SettlementError, List[Int]] =
    items.foldLeft(Right(List.empty[Int]) : Either[SettlementError, List[Int]]) { (acc, item) =>
      for {
        list <- acc
        it <- item
      } yield list :+ it
    }

  // ============================================================================
  // Main — Implementation & Tests
  // ============================================================================
  //
  // Level 1: This main is intentionally empty (comments only).
  // YOU write all:
  //   - implicit config for validateCredit
  //   - test case variables (tc1, tc2, etc.)
  //   - function calls
  //   - assert statements
  //   - println statements for debugging
  //
  // ============================================================================

  def main(args: Array[String]): Unit = {
      println("AC-1 (All Valid)")
      val aggregate1 = aggregateCredits(List(Right(100), Right(200), Right(50)))
      println(aggregate1)
      assert(aggregate1.contains(List(100, 200, 50)))

      println("AC-2 (Empty): aggregateCredits(List()) == Right(List()) ---")
      val aggregate2 = aggregateCredits(List())
      println(aggregate2)
      assert(aggregate2.contains(List()))

      println("--- AC-3 (Single Valid): aggregateCredits(List(Right(300))) == Right(List(300)) ---")
      val aggregate3 = aggregateCredits(List(Right(300)))
      println(aggregate3)
      assert(aggregate3.contains(List(300)))

      println("--- AC-4 (Single Error): aggregateCredits(List(Left(InsufficientFunds(\"w1\", 0, 100)))) == Left(InsufficientFunds(\"w1\", 0, 100)) ---")
      val aggregate4 = aggregateCredits(List(Left(InsufficientFunds("w1", 0, 100))))
      println(aggregate4)
      assert(aggregate4 == Left(InsufficientFunds("w1", 0, 100)))

      println("--- AC-5 (Error at Start): aggregateCredits(List(Left(FrozenAccount(\"w1\")), Right(100))) == Left(FrozenAccount(\"w1\")) ---")
      val aggregate5 = aggregateCredits(List(Left(FrozenAccount("w1"))))
      println(aggregate5)
      assert(aggregate5 == Left(FrozenAccount("w1")))

      println("--- AC-6 (Error in Middle): aggregateCredits(List(Right(100), Left(InsufficientFunds(\"w2\", 50, 200)), Right(50))) == Left(InsufficientFunds(\"w2\", 50, 200)) ---")
      val aggregate6 = aggregateCredits(List(Right(100), Left(InsufficientFunds("w2", 50, 200)), Right(50)))
      println(aggregate6)
      assert(aggregate6 == Left(InsufficientFunds("w2", 50, 200)))

      println("--- AC-7 (Error at End): aggregateCredits(List(Right(100), Right(200), Left(ThresholdExceeded(300, 250)))) == Left(ThresholdExceeded(300, 250)) ---")
      val aggregate7 = aggregateCredits(List(Right(100), Right(200), Left(ThresholdExceeded(300, 250))))
      println(aggregate7)
      assert(aggregate7 == Left(ThresholdExceeded(300, 250)))

      println("--- AC-8 (Multiple Errors): aggregateCredits(List(Left(InsufficientFunds(\"w1\", 0, 100)), Left(FrozenAccount(\"w2\")))) == Left(InsufficientFunds(\"w1\", 0, 100)) ---")
      val aggregate8 = aggregateCredits(List(Left(InsufficientFunds("w1", 0, 100)), Left(FrozenAccount("w2"))))
      println(aggregate8)
      assert(aggregate8 == Left(InsufficientFunds("w1", 0, 100)))

      implicit val config = SettlementConfig(250);
      println("--- AC-9 (Threshold) positive scenario: ---")
      val validate1 = validateCredit(200)
      println(validate1)
      assert(validate1 == Right(200))

      println("--- AC-9a (Threshold): negative scenario---")
      val validate2 = validateCredit(300)
      println(validate2)
      assert(validate2 == Left(ThresholdExceeded(300, 250)))

      println("--- AC-10 (Monad Law - Left Identity)---")
      val leftCheck = leftIdentityCheck(5, x => Right(x * 2))
      println(s"leftIdentityCheck(5, x => Right(x * 2)) is $leftCheck")
      assert(leftCheck)

      println("--- AC-11 (Monad Law - Right Identity)---")
      val rightCheck = rightIdentityCheck(Right(42))
      println(s"rightIdentityCheck(Right(42) are $rightCheck")
      assert(rightCheck)

    // --- AC-12 (FoldLeft diagram): In your implementation, add step-by-step comments tracing the accumulator flow ---
      println("--- --- AC-12 (FoldLeft diagram)---")
      println("Happy path: List(Right(100), Right(200), Right(50))")
      println("step 0 : Right(List())")
      println("step 1 : Right(List(100))")
      println("step 2 : Right(List(100, 200))")
      println("step 3 : Right(List(100, 200, 50))")
      println("Error at the end: List(Right(100), Right(200), Left(ThresholdExceeded(300, 250))")
      println("step 0 : Right(List())")
      println("step 1 : Right(List(100))")
      println("step 2 : Right(List(100, 200))")
      println("step 3 : Left(ThresholdExceeded(300, 250))")

      //Optional
      println("AC-1a (All Valid)")
      val aggregate1a = aggregateCreditsForComp(List(Right(100), Right(200), Right(50)))
      println(aggregate1a)
      assert(aggregate1a.contains(List(100, 200, 50)))

      println("AC-2a (Empty): aggregateCreditsForComp(List()) == Right(List()) ---")
      val aggregate2a = aggregateCreditsForComp(List())
      println(aggregate2a)
      assert(aggregate2a.contains(List()))

      println("--- AC-3a (Single Valid): aggregateCreditsForComp(List(Right(300))) == Right(List(300)) ---")
      val aggregate3a = aggregateCreditsForComp(List(Right(300)))
      println(aggregate3a)
      assert(aggregate3a.contains(List(300)))

      println("--- AC-4a (Single Error): aggregateCreditsForComp(List(Left(InsufficientFunds(\"w1\", 0, 100)))) == Left(InsufficientFunds(\"w1\", 0, 100)) ---")
      val aggregate4a = aggregateCreditsForComp(List(Left(InsufficientFunds("w1", 0, 100))))
      println(aggregate4a)
      assert(aggregate4a == Left(InsufficientFunds("w1", 0, 100)))

      println("--- AC-5a (Error at Start): aggregateCreditsForComp(List(Left(FrozenAccount(\"w1\")), Right(100))) == Left(FrozenAccount(\"w1\")) ---")
      val aggregate5a = aggregateCreditsForComp(List(Left(FrozenAccount("w1"))))
      println(aggregate5a)
      assert(aggregate5a == Left(FrozenAccount("w1")))

      println("--- AC-6a (Error in Middle): aggregateCreditsForComp(List(Right(100), Left(InsufficientFunds(\"w2\", 50, 200)), Right(50))) == Left(InsufficientFunds(\"w2\", 50, 200)) ---")
      val aggregate6a = aggregateCreditsForComp(List(Right(100), Left(InsufficientFunds("w2", 50, 200)), Right(50)))
      println(aggregate6a)
      assert(aggregate6a == Left(InsufficientFunds("w2", 50, 200)))

      println("--- AC-7a (Error at End): aggregateCreditsForComp(List(Right(100), Right(200), Left(ThresholdExceeded(300, 250)))) == Left(ThresholdExceeded(300, 250)) ---")
      val aggregate7a = aggregateCreditsForComp(List(Right(100), Right(200), Left(ThresholdExceeded(300, 250))))
      println(aggregate7a)
      assert(aggregate7a == Left(ThresholdExceeded(300, 250)))

      println("--- AC-8a (Multiple Errors): aggregateCreditsForComp(List(Left(InsufficientFunds(\"w1\", 0, 100)), Left(FrozenAccount(\"w2\")))) == Left(InsufficientFunds(\"w1\", 0, 100)) ---")
      val aggregate8a = aggregateCreditsForComp(List(Left(InsufficientFunds("w1", 0, 100)), Left(FrozenAccount("w2"))))
      println(aggregate8a)
      assert(aggregate8a == Left(InsufficientFunds("w1", 0, 100)))
  }
}
