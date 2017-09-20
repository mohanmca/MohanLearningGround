
import org.scalacheck.Prop
import org.scalacheck.Gen

object generators {
  val gen = org.scalacheck.Gen.const(3)           //> gen  : org.scalacheck.Gen[Int] = org.scalacheck.Gen$$anon$3@27082746
  gen.sample                                      //> res0: Option[Int] = Some(3)

  val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U')  //> vowel  : org.scalacheck.Gen[Char] = org.scalacheck.Gen$$anon$3@146ba0ac
  vowel.map { x => println(x); x }.sample         //> E
                                                  //| res1: Option[Char] = Some(E)

  val vowelWithFreq = Gen.frequency(
    (3, 'A'),
    (4, 'E'),
    (2, 'I'),
    (3, 'O'),
    (1, 'U'))                                     //> vowelWithFreq  : org.scalacheck.Gen[Char] = org.scalacheck.Gen$$anon$1@67b92
                                                  //| f0a
  vowelWithFreq.sample                            //> res2: Option[Char] = Some(E)

  val numGen = Gen.posNum[Int]                    //> numGen  : org.scalacheck.Gen[Int] = org.scalacheck.Gen$$anon$3@1dfe2924
  numGen.sample                                   //> res3: Option[Int] = Some(57)
  
  val propStringLength = org.scalacheck.Prop.forAll { s: String =>
    val len = s.length
    (s+s).length == len+len
  }                                               //> propStringLength  : org.scalacheck.Prop = Prop
  
  val propDivByZero =
    org.scalacheck.Prop.throws(classOf[ArithmeticException]) { 1/0 }
                                                  //> propDivByZero  : Boolean = true
  
  val propListIdxOutOfBounds = org.scalacheck.Prop.forAll { xs: List[Int] =>
    Prop.throws(classOf[IndexOutOfBoundsException]) {
      xs(xs.length+1)
    }
  }                                               //> propListIdxOutOfBounds  : org.scalacheck.Prop = Prop
}