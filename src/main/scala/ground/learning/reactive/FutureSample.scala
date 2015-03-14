package ground.learning.reactive;
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object FutureSample {
  val s = "Hello"
  val rand = Random
  val failureHandler: PartialFunction[Throwable, String] = { case exception: Throwable => "Failure" }

  def printHello(): Unit = {
    val f: Future[String] = Future {
      rand.nextInt(2) match {
        case 1 => "Success"
        case _ => throw new NullPointerException("null");
      }
    }
    f onSuccess {
      case msg => println(msg)
    }
    f onFailure { case _ => println("Failure") }
  }
}

object FutureMainApp extends App {
  Range.apply(1, 10).foreach { _ => FutureSample.printHello() }

  //Let us sleep the main thread so Future would get time to execute.
  Thread.sleep(1000);
}