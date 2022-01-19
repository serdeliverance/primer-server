package com.dixa.domain.functions

import com.dixa.domain.functions.NumericFunctions.{generateNaturalNumbersUntilN, isPrime}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class NumericFunctionsSpec extends AnyWordSpec with Matchers {
  "#isPrimer function" should {

    "primer of 1 return true" in {
      val result = isPrime(1)

      result mustBe true
    }

    "return true" in {
      val result = isPrime(5)

      result mustBe true
    }

    "return false" in {
      val result = isPrime(4)

      result mustBe false
    }
  }

  "#generateNaturalNumbersUntilN" should {

    "return [1]" in {
      val result = generateNaturalNumbersUntilN(1)

      result mustBe 1 #:: LazyList.empty
    }

    "return numbers from 1 to 5" in {
      val result = generateNaturalNumbersUntilN(5)

      result mustBe 1 #:: 2 #:: 3 #:: 4 #:: 5 #:: LazyList.empty
    }
  }
}
