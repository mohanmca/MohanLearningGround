package ground.learning.scala.pf.example

class EventHandler {

}

trait Event
case class Click(x: Int, y: Int) extends Event
object Event {
  type Handler[A <: Event] = PartialFunction[Click, Any]
}
object Application extends App {
  val handleLeftClick: Event.Handler[Click] = {
    case Click(x, y) if x <= 20 && y <= 20 =>
      println("Clicked the left corner!")
  }
  val diagonalLeftClick: Event.Handler[Click] = {
    case Click(x, y) if x == y =>
      println("Clicked somewhere digonal!")
  }
  val default: Event.Handler[Click] = {
    case Click(x, y) =>
      println("Clicked somewhere!")
  }
  val handler = handleLeftClick orElse diagonalLeftClick orElse default
  handler(Click(12, 20))
  handler(Click(22, 22))
  handler(Click(222, 2222))

}