package ground.learning.fp.Monad

object ScalazStateMonad extends App {
  def example1() {
    import scalaz._
    val s = State[Int, String](i => (i + 1, "str"))
    // start with state of 1, pass it to s, returns result value "str"
    assert(s.eval(1) == "str")

    //same but only retrieve the state
    assert(s.exec(1) == 2)

    // get both state and value or s.run(1)
    assert(s(1) == (2, "str"))

  }

  def example2() {
    import java.util.Random
    def dice = State[Random, Int](r => (r, r.nextInt(1000) + 1))

    def TwoDice() = for {
      r1 <- dice
      r2 <- dice
    } yield (r1, r2)

    println(TwoDice().f(new Random(1L)))

    val list = List.fill(10)(TwoDice())

  }

  def example3() {
  }

  //  example1()
  //  example2()
  example3()
}


