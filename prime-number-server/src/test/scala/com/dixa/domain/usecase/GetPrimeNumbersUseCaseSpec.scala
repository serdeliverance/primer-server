package com.dixa.domain.usecase

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GetPrimeNumbersUseCaseSpec extends AnyWordSpec with Matchers {

  private val subject = GetPrimeNumbersUseCase()

  "#GetPrimeNumbersUseCase" should {
    "retrieve primer numbers" in {
      // TODO
      val result = subject.getPrimeNumbers(5)

      result mustBe 1 #:: 2 #:: 3 #:: 5 #:: LazyList.empty
    }

    "return error" in {
      // TODO
    }
  }
}
