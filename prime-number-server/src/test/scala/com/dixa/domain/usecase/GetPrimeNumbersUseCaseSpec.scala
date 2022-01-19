package com.dixa.domain.usecase

import com.dixa.domain.entity.InvalidNumberError
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GetPrimeNumbersUseCaseSpec extends AnyWordSpec with Matchers {

  private val subject = GetPrimeNumbersUseCase()

  "#GetPrimeNumbersUseCase" should {

    "retrieve prime numbers of one" in {
      val result = subject.getPrimeNumbers(1)

      result mustBe Right(1 #:: LazyList.empty)
    }

    "retrieve prime numbers" in {
      val result = subject.getPrimeNumbers(5)

      result mustBe Right(1 #:: 2 #:: 3 #:: 5 #:: LazyList.empty)
    }

    "return error" in {
      val result = subject.getPrimeNumbers(-1)

      result mustBe Left(InvalidNumberError(-1))
    }
  }
}
