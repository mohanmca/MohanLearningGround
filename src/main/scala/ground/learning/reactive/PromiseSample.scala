package ground.learning.reactive;
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random


object PromiseSample {
  val s = "Hello"
  val rand = Random
  val failureHandler: PartialFunction[Throwable, String] = { case exception: Throwable => "Failure" }

  val p = Promise[String]()
  val f = p.future

  def produceSomething(): String = {
    rand.nextInt(2) match {
      case 1 => "Success"
      case _ => throw new NullPointerException("null");
    }
  }
  def startDoingSomething(): Unit = {
    println("startDoingSomething")
  }
  def doSomethingWithResult(): Unit = {
    println("doSomething")
  }

  def continueDoingSomethingUnrelated(): String = {
    rand.nextInt(2) match {
      case 1 => "Success"
      case _ => throw new NullPointerException("null");
    }
  }

  val producer = Future {
    val r = produceSomething()
    p success r
    continueDoingSomethingUnrelated()
  }

  def consumer() = Future {
    startDoingSomething()
    f onComplete  ({
      case value if value!=null  => doSomethingWithResult()
      case _ => println("Failure")
    })
  }
}

object PromiseMainApp extends App {
  PromiseSample.consumer()
  //Let us sleep the main thread so Future would get time to execute.
  Thread.sleep(1000);

}