package ground.worksheet.scalacheck

import Interleaver._
import org.scalatest._
import prop._
import org.scalacheck.Prop.{ AnyOperators, forAll, all }

class InterleaveSpec extends PropSpec with Checkers {

  property("the interleave method must interleave lists") {
    check(
      forAll { (l1 : List[Int], l2 : List[Int]) =>
        val res = interleave(l1, l2)
        val is = (0 to Math.min(l1.length,
          l2.length) - 1).toList
        all(
          "length" |: l1.length + l2.length =? res.length,
          "zip l1" |: l1 =? is.map(i => res(2 * i)) ++
            res.drop(2 * l2.length),
          "zip l2" |: l2 =? is.map(i => res(2 * i + 1)) ++
            res.drop(2 * l1.length)) :| ("res: " + res)
      })
  }
}