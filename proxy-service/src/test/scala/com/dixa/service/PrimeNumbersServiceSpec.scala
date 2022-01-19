package com.dixa.service

import akka.stream.scaladsl.Source
import com.dixa.model.InvalidParameterError
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import prime.{GetPrimeNumbersRequest, PrimeNumber, PrimeNumbersServiceClient}

class PrimeNumbersServiceSpec extends AnyWordSpec with Matchers {

  private val client  = mock[PrimeNumbersServiceClient]
  private val subject = new PrimeNumbersService(client)

  "#PrimeNumbersService" should {
    "return a stream of prime numbers" in {
      when(client.getPrimeNumbers(GetPrimeNumbersRequest(3))).thenReturn(Source(List(1, 2, 3).map(PrimeNumber(_))))

      val result = subject.getPrimeNumbers(3)

      // FIXME
      result mustBe Right(Source(List(PrimeNumber(1), PrimeNumber(2), PrimeNumber(3))))
    }

    "return error" in {
      val result = subject.getPrimeNumbers(-1)

      result mustBe Left(InvalidParameterError("prime", "-1"))
    }
  }
}
