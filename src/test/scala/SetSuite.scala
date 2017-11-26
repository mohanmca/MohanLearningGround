import org.scalatest.FunSuite
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.Matchers


class SetSuite extends FunSuite with Matchers with LazyLogging {

  test("An empty Set should have size 0") {
    "Hello, " ++ "Scala!" shouldBe  "Hello, Scala!"
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}