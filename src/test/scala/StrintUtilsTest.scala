package learning.scalacheck;

import org.scalacheck.Arbitrary.arbString
import org.scalatest.Finders
import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.Matchers


//learning.scalacheck.StringUtilsSuite
class StringUtilsSuite extends PropSpec with PropertyChecks with Matchers {

  property("String contains should either return true or false") {

    forAll { (content : String, substr : String) =>
      StringUtils.contains(content, substr) == true || StringUtils.contains(content, substr) == false
    }

    forAll { (content : String, substr : String, subsubstr : String) =>
      StringUtils.contains(content + substr + subsubstr, substr) == true
    }

  }

  property("Below test case may fail rarely, Any input that is Palindrome would fail below testcase") {

    forAll { (content : String, substr : String, subsubstr : String) =>
      StringUtils.contains(content + substr + subsubstr, substr) == true
      StringUtils.contains(content + substr + subsubstr, (content + substr + subsubstr).reverse) == false
    }

  }

  property("Truncate") {
    forAll { (content : String, len : Int) =>
      val truncated = StringUtils.truncate(content, len)
      if (len < 0) truncated == ""
      else
        (content.length <= len && truncated == content) ||
          content.substring(0, len) + "..." == truncated
    }

  }

  property("Palindrome always successful") {

    forAll { (content : String) =>
      StringUtils.isPalindrome(content + content.reverse) == true
    }

  }

}