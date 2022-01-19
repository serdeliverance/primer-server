package com.dixa.service

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import com.dixa.model.InvalidParameterError
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import prime.{GetPrimeNumbersRequest, PrimeNumber, PrimeNumbersServiceClient}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class PrimeNumbersServiceSpec extends AnyWordSpec with Matchers {

  private val client  = mock[PrimeNumbersServiceClient]
  private val subject = new PrimeNumbersService(client)

  implicit val system = ActorSystem()

  "#PrimeNumbersService" should {
    "return a stream of prime numbers" in {
      when(client.getPrimeNumbers(GetPrimeNumbersRequest(3))).thenReturn(Source(List(1, 2, 3).map(PrimeNumber(_))))

      subject.getPrimeNumbers(3) match {
        case Left(_) => fail()
        case Right(primeNumbersSource) =>
          val materializedResult = primeNumbersSource.runWith(Sink.seq)

          val result = Await.result(materializedResult, 2.seconds)

          result mustBe Seq(1, 2, 3)

      }
    }

    "return error" in {
      val result = subject.getPrimeNumbers(-1)

      result mustBe Left(InvalidParameterError("prime", "-1"))
    }
  }
}
