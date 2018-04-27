package ground.learning.Sam

object ThreadApp extends App {
  println("Main thread - begins")
  val runnable: Runnable = () => println("hello world - from first thread")
  
  val thread = new Thread(runnable)
  println("Main thread - spins first thread")
  thread.start()

  val thread2 = new Thread(() => println("hello world - from second thread"))
  println("Main thread - spins second thread")
  thread2.start
  thread.join()
  thread2.join()
  
  println("Main thread - end")
}