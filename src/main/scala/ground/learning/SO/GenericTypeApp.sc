object GenericTypeApp extends App {

  abstract class Drink
  abstract class SoftDrink() extends Drink
  abstract class Juice() extends Drink
  case class Cola() extends SoftDrink
  case class Sprite() extends SoftDrink

  val al = List(Cola(), Cola())

  val bl = List(Sprite())

  val cl = al ++ bl
}