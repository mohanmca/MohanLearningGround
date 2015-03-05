package ground.learning.lasy.streams
/**
 * Alway refer worksheet related to this class to find the usage.
 * fibs take 5 foreach println
 * lazy_stream_ws.sc
 */
object Streams {
  def natNumbers: Stream[Int] = { def x: Stream[Int] = Stream.cons(1, x map (_ + 1)); x }

  val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map { n => n._1 + n._2 }
}