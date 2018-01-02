package ground.learning.fp.Monad;

import IndexedState._

object IndexedStateExample extends App {

  val someIntToCharFunction = (i : Int) => i.toChar

  val printMonad : IState[Int, Char, Int] => Unit = s => {
    s.exec(66)
    println("Result of the function: " + s.eval(66))
    println("Result of the state: " + s.exec(66))
  }

  /**
   * Performs `someIntToCharFunction` on the input state, returning the old state.
   */
  val myIStateComputation : IState[Int, Char, Int] = for {
    original <- IState.get[Int]
    _ <- IState.modify[Int, Char](someIntToCharFunction)
  } yield original

  printMonad(myIStateComputation)

  val myIStateCounter : IState[Int, Char, Int] = for {
    original <- IState.get[Int]
    _ <- IState.put(original + 1)
    _ <- IState.modify[Int, Char](someIntToCharFunction)
  } yield original

  printMonad(myIStateComputation)

  val myIStateCounterForMany : Int => IState[Int, Char, Int] = { state =>
    for {
      _ <- IState.put(state)
      original <- IState.get[Int]
      _ <- IState.put(original + 1)
      _ <- IState.modify[Int, Char](someIntToCharFunction)
      _ <- IState.put(original + 1)
      _ <- IState.modify[Int, Char](someIntToCharFunction)
      _ <- IState.put(original + 1)
      _ <- IState.modify[Int, Char](someIntToCharFunction)
      _ <- IState.put(original + 1)
      _ <- IState.modify[Int, Char](someIntToCharFunction)
      _ <- IState.put(original + 1)
      _ <- IState.modify[Int, Char](someIntToCharFunction)
    } yield original
  }
  
  printMonad(myIStateCounterForMany(66))
  

}