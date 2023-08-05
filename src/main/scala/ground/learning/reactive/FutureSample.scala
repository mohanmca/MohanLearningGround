package ground.learning.reactive;
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Random, Try}

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
    f onComplete({
        case scala.util.Success(s) => println(s)
        case _=> println("Failure ")
    })

  }
}

object FutureMainApp extends App {
  Range.apply(1, 10).foreach { _ => FutureSample.printHello() }

  //Let us sleep the main thread so Future would get time to execute.
  Thread.sleep(1000);
}