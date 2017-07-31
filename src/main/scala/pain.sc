import scala.util.Either._
import scala.util.Try._
import scala.util.Try

object pain {

  val listOfEmployeeId : List[String] = Nil
  val findHike : String => Some[Int] = (x : String) => Some(0)
  val updatePay : (String, Int) => Either[String, Int] = (x : String, y : Int) => if (x.trim().length() > 0) Right(10 + y) else Left("Sorry")
  val updateDb : Int => Try[Boolean] = pay => Try ( pay > 100000000 )
  

  val t = for {
    employee <- listOfEmployeeId
    hike <- findHike(employee)
    success <- updateDb(hike)
    //newPay <- updatePay("t", 10)
  } yield success

}