package ground.learning.scala.traits

/**
 * Created by Mohan on 15/09/2014.
 *
 * Stacks are layered one top of another, when moving from Left -> Right,
 * Right most will be at the top layer, and receives method call.
 */
object ReorderedTraitMain {

  def main(args: Array[String]) {
    import scala.reflect.runtime.universe._
    var classHierarchyExpected = "X extends Humanoid extends Robot extends Lasers extends Object extends Any"
    var classHierarchyActual: String = typeOf[X.type].baseClasses.map(_.name).mkString(" extends ");
    if (classHierarchyActual.equals(classHierarchyExpected))
      println(classHierarchyActual);
  }
}

trait Robot extends Lasers
trait Humanoid
trait Lasers
object X extends Robot with Humanoid with Lasers
