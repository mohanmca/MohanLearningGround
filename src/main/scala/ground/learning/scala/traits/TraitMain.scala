package ground.learning.scala.traits

;


/**
 * Created by Mohan on 31/08/2014.
 *
 * Stacks are layered one top of another, when moving from Left -> Right,
 * Right most will be at the top layer, and receives method call.
 */
object TraitMain {

  def main(args: Array[String]) {
    val strangers: List[NoEmotion] = List(
      new Stranger("Ray") with NoEmotion,
      new Stranger("Ray") with Bad,
      new Stranger("Ray") with Good,
      new Stranger("Ray") with Good with Bad,
      new Stranger("Ray") with Bad with Good)
    println(strangers.map(_.hi + "\n"))
  }
}

trait NoEmotion {
  def hi = {
    "I am Ray"
  }
}

trait Good extends NoEmotion {
  def value: String

  override def hi = {
    "I am " + value + ", It is a beautiful day!"
  }
}

trait Bad extends NoEmotion {
  def value: String

  override def hi = {
    "I am " + value + ", It is a bad day!"
  }

  def sayHello = hi
}

case class Stranger(value: String) {
}

