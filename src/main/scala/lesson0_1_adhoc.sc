trait A {
  def foo(): String = "A"
}

trait B {
  def bar(): String = "B"
}

class C extends A with B
// C получает и foo(), и bar()

val c = new C
println(c.foo())  // A
println(c.bar())  // B


trait A { def greet(): String = "Hello from A" }
trait B { def greet(): String = "Hello from B" }

class C extends A with B {
  override def greet(): String = super[B].greet()  // явно берём из A
}

val c1 = new C
println(c1.greet())