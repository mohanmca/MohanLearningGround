package ground.learning.scala.async

/**
 * @author Mohan
 */
object CookDemoV1 {
  case class Dish(food: String) {
    def +(other: String): Dish = {
      Dish("${food} with ")
    }
  }

}