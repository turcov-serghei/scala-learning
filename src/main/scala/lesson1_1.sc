Some(4).map(x => x + 1)       // Some(5)
Some(4).flatMap(x => Some(x + 1)) // Some(5)


def classify(amount: Int) =
  if (amount <= 0) "invalid"
  else "approved"


  classify(10)

val amount = 10
val res = if (amount <= 0) println("invalid")
else Some("valid")