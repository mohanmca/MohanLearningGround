package ground.learning.reactive;
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

/*
 * Credit :: http://www.srirangan.net/2013-01-controlling-flow-with-scala-futures
 */

object WhoWonRacePromise {
}

object WhoWonRacePromiseApp extends App {
  // Two asynchronous blocks `tryComplete` a promise

  val whoWonTheRace = Promise[String]

  whoWonTheRace.future.value match {
    case Some(name) => Console.println(name + " wins")
    case _         => Console.println("No one wins")
  }

  Future {
    Thread.sleep(new Random().nextInt(500))
    whoWonTheRace trySuccess "x"
  }

  Future {
    Thread.sleep(new Random().nextInt(500))
    whoWonTheRace trySuccess "y"
  }

  Console.println("Who won the race?")

  Thread.sleep(1000)
}