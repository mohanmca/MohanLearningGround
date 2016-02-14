package ground.learning.monoid
import scala.collection.immutable._
import MonoidApp._

object Application extends App {
  List(1, 2, 3).sum2.println
  List[String]("1", "2", "3").sum2.println
  optInt.sum2.println
  optString.sum2.println
}