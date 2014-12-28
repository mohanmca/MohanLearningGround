package ground.learning.scala.types

//Older version of scala
//import scala.reflect._
import scala.reflect.runtime.universe._

// reverse the extension, and expand the trait hierarchy, and keep unique moving from Right.
// X = Robot with Humanoid with Lasers
// X = Lasers with Humanoid with Robot
// X = Lasers with Humanoid with Robot with Lasers (with Object with Any) --(default)
// X = Humanoid with Robot with Lasers (with Object with Any) --(Travel from right, retain unique)

object TypeMain {
  def main(args: Array[String]) {
    object X extends Robot with Humanoid with Lasers;
    val actualInHeritance  = typeOf[X.type].baseClasses.map(_.name).mkString(" extends ")
    val expected = "X extends Humanoid extends Robot extends Lasers extends Object extends Any"
    print( actualInHeritance == expected )
  }
}

trait Lasers;
trait Humanoid;
trait Robot extends Lasers;