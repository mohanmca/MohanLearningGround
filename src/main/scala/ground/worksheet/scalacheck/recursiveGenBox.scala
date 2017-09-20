package ground.worksheet.scalacheck
import org.scalacheck.Gen
import org.scalacheck.Gen.{ choose, oneOf }
import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAll

object Box extends App {

  trait Color
  case object Red extends Color
  case object Green extends Color

  sealed trait Shape { def color : Color }
  case class Circle(color : Color) extends Shape
  case class Line(color : Color) extends Shape
  case class Box(val color : Color, val boxed : Shape) extends Shape

  val genColor = Gen.oneOf(Red, Green)
  val genLine = for { color <- genColor } yield Line(color)
  val genCircle = for { color <- genColor } yield Circle(color)

  val genBox = for {
    color <- genColor
    shape <- genShape
  } yield Box(color, shape)

  val genShape : Gen[Shape] = Gen.oneOf(genLine, genCircle, genBox)
  println(genBox.sample)

  import org.scalacheck.Arbitrary
  implicit val arbPerson = Arbitrary(genShape)

  val propShapeColor = org.scalacheck.Prop.forAll { s : Shape =>
    (s.color == Red || s.color == Green)
  }
  
  propShapeColor.check

}