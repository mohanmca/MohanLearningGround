package ground.learning.scala.traits.selftype

/**
 * Created by Mohan on 31/08/2014.
 *
 * Stacks are layered one top of another, when moving from Left -> Right,
 * Right most will be at the top layer, and receives method call.
 */


object TraitSelfTypeMain {

  def main(args: Array[String]) {
    val strangers: List[NoEmotion] = List(
      new Stranger("Ray") with NoEmotion,
      new Stranger("Ray") with Bad,
      new Stranger("Ray") with Good,
      new Stranger("Ray") with Good with Bad,
      new Stranger("Ray") with Bad with Good,
      new Wanderer("John")
    )
    println(strangers.map(_.sayHello + "\n"))
  }
}

trait Hi {
  def hi: String;
}

trait NoEmotion {
  self: Hi =>
  def sayHello = hi
}

trait Good extends NoEmotion {
  self: Hi =>
  override def sayHello = hi + ", It is a beautiful day!"
}

trait Bad extends NoEmotion {
  self: Hi =>
  override def sayHello = hi + ", It is a bad day!"
}

case class Stranger(value: String) extends Hi {
  def hi = "I am " + value;
}

case class Wanderer(value: String) extends Hi with Good with Bad {
  def hi = "I am " + value;
}

