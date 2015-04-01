package ground.learning.reactive;
import scala.concurrent.Future
import scala.concurrent.Promise._
import scala.concurrent.Promise
import scala.concurrent.{ future, promise }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

/*
 * Credit :: http://www.srirangan.net/2013-01-controlling-flow-with-scala-futures
 */

object WhoWonRacePromise {
}

object WhoWonRacePromiseApp extends App {
  // Two asynchronous blocks `tryComplete` a promise

  val whoWonTheRace = Promise[String]

  whoWonTheRace.future onSuccess {
    case name => Console.println(name + " wins")
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