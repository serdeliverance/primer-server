package com.dixa.adapter.grpc

import akka.NotUsed
import akka.grpc.GrpcServiceException
import io.grpc.Status
import akka.stream.scaladsl.Source
import com.dixa.port.in.GetPrimeNumbersService
import prime.{GetPrimeNumbersRequest, PrimeNumber, PrimeNumbersService}

class GrpcPrimeNumberService(getPrimeNumbersService: GetPrimeNumbersService) extends PrimeNumbersService {

  override def getPrimeNumbers(in: GetPrimeNumbersRequest): Source[PrimeNumber, NotUsed] =
    getPrimeNumbersService.getPrimeNumbers(in.number) match {
      case Left(error) =>
        Source.failed(new GrpcServiceException(Status.INVALID_ARGUMENT.withDescription(error.getMessage())))
      case Right(primeNumbers) =>
        Source.fromIterator(() => primeNumbers.iterator).map(n => new PrimeNumber(n))
    }
}
