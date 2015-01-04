object testing {

  trait greeting[A] {
    val value: A
    def greet: String
  }
  object greeting {
    implicit object IntGreeting extends greeting[Int] {
      def apply(value: Int) = new greeting[Int] {
        def greet = value + ""
      }
    }
    implicit object DoubleGreeting extends greeting[Double] {
      def apply(value: Double) = new greeting[Double] {
        def greet = value + ""
      }
    }
  }

  import greeting._

  def printGreetMessaage[A](implicit a: greeting[A]): String = "Hello! " + a.greet

  val t = printGreetMessaage(implicitly[greeting[Int]])(3)

  /*
- type mismatch;  found   : Int(3)  required: A
- inferred type arguments [Int] do not conform to method printGreetMessaage's type parameter bounds [A <: testing.greeting[A]]
*/
}