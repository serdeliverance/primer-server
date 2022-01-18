package com.dixa.domain.adapter.grpc

import akka.actor.ActorSystem
import akka.grpc.GrpcServiceException
import akka.stream.scaladsl.Sink
import com.dixa.adapter.grpc.GrpcPrimeNumberService
import com.dixa.domain.entity.InvalidNumberError
import com.dixa.port.in.GetPrimeNumbersService
import org.mockito.Mockito.when
import org.mockito.MockitoSugar.mock
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import prime.{GetPrimeNumbersRequest, PrimeNumber}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class GrpcPrimeNumberServiceSpec extends AnyWordSpec with Matchers {

  private val getPrimeNumbersService = mock[GetPrimeNumbersService]
  private val subject                = new GrpcPrimeNumberService(getPrimeNumbersService)

  implicit val system = ActorSystem()

  "#GrpcPrimesNumberService" should {
    "return an stream of prime numbers" in {
      when(getPrimeNumbersService.getPrimeNumbers(2)).thenReturn(Right(1 #:: 2 #:: LazyList.empty))

      val materializedResult = subject.getPrimeNumbers(new GetPrimeNumbersRequest(2)).runWith(Sink.seq)

      val result = Await.result(materializedResult, 2.seconds)

      result mustBe Seq(PrimeNumber(1), PrimeNumber(2))
    }

    "return error" in {
      when(getPrimeNumbersService.getPrimeNumbers(-1)).thenReturn(Left(InvalidNumberError(-1)))

      assertThrows[GrpcServiceException] {
        val materializedResult = subject.getPrimeNumbers(new GetPrimeNumbersRequest(-1)).runWith(Sink.ignore)

        Await.result(materializedResult, 2.seconds)
      }
    }
  }
}
