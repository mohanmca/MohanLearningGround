
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen

object generators {
  val gen = org.scalacheck.Gen.const(3)           //> gen  : org.scalacheck.Gen[Int] = org.scalacheck.Gen$$anon$3@449b2d27
  gen.sample                                      //> res0: Option[Int] = Some(3)

  val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U')  //> vowel  : org.scalacheck.Gen[Char] = org.scalacheck.Gen$$anon$3@e25b2fe
  vowel.map { x => println(x); x }.sample         //> U
                                                  //| res1: Option[Char] = Some(U)
                                                  
val vowelWithFreq = Gen.frequency(
  (3, 'A'),
  (4, 'E'),
  (2, 'I'),
  (3, 'O'),
  (1, 'U')
)                                                 //> vowelWithFreq  : org.scalacheck.Gen[Char] = org.scalacheck.Gen$$anon$1@6325a
                                                  //| 3ee
vowelWithFreq.sample                              //> res2: Option[Char] = Some(U)

val numGen = Gen.posNum[Int]                      //> numGen  : org.scalacheck.Gen[Int] = org.scalacheck.Gen$$anon$3@1dfe2924
numGen.sample                                     //> res3: Option[Int] = Some(8)
}