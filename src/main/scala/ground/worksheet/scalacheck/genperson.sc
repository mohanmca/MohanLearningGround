package ground.worksheet
import org.scalacheck.Gen.{ choose, oneOf }
import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAll

object genperson {

  case class Person(firstName : String, lastName : String, age : Int) { def isTeenager = age >= 13 && age <= 19 }

  val genPerson = for {
    firstName <- oneOf("Alan", "Ada", "Alonzo")
    lastName <- oneOf("Lovelace", "Turing", "Church")
    age <- choose(1, 100)
  } yield Person(firstName, lastName, age)        //> genPerson  : org.scalacheck.Gen[ground.worksheet.genperson.Person] = org.sca
                                                  //| lacheck.Gen$$anon$3@5b275dab

  import org.scalacheck.Arbitrary
  implicit val arbPerson = Arbitrary(genPerson)   //> arbPerson  : org.scalacheck.Arbitrary[ground.worksheet.genperson.Person] = o
                                                  //| rg.scalacheck.ArbitraryLowPriority$$anon$1@1bce4f0a
  val propPerson = forAll { p : Person => p.isTeenager == (p.age >= 13 && p.age <= 19) }
                                                  //> propPerson  : org.scalacheck.Prop = Prop
                                                  
  }