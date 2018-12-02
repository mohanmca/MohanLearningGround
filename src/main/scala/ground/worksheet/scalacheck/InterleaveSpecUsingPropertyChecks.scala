package ground.worksheet.scalacheck

import Interleaver._
import org.scalatest._
import prop._
import org.scalacheck.Prop.{ AnyOperators, forAll, all }

import Interleaver._
import org.scalatest._
import prop._
import Matchers._
import java.lang.Math

class InterleaveSpecUsingPropertyChecks extends PropSpec with PropertyChecks {

  property("the interleave method must interleave lists") {
    forAll { (l1: List[Int], l2: List[Int]) =>
      val res = interleave(l1, l2)
      val is = (0 to Math.min(
        l1.length,
        l2.length) - 1).toList
      l1.length + l2.length shouldBe res.length
      l1 shouldBe is.map(i => res(2 * i)) ++
        res.drop(2 * l2.length)
      l2 shouldBe is.map(i => res(2 * i + 1)) ++
        res.drop(2 * l1.length)
    }
  }
}