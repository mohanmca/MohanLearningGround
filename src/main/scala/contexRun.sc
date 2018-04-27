object contexRun {
  abstract class Drink
  abstract class SoftDrink() extends Drink
  abstract class Juice() extends Drink
  case class Cola() extends SoftDrink
  case class Sprite() extends SoftDrink
  case class Orange() extends Juice

  val al = List(Cola(), Cola())                   //> al  : List[contexRun.Cola] = List(Cola(), Cola())

  val bl = List(Sprite())                         //> bl  : List[contexRun.Sprite] = List(Sprite())

  val cl = al ++ bl ++ List(Orange())             //> cl  : List[Product with Serializable with contexRun.Drink] = List(Cola(), Co
                                                  //| la(), Sprite(), Orange())

  def print[A >: SoftDrink](list: List[A]): String = {
    list.mkString(", ")
  }                                               //> print: [A >: contexRun.SoftDrink](list: List[A])String

  print(cl)                                       //> res0: String = Cola(), Cola(), Sprite(), Orange()

}