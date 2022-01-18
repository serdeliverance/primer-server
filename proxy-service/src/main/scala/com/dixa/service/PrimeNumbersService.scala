package com.dixa.service

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.dixa.model.{ApplicationError, InvalidParameterError}
import prime.{GetPrimeNumbersRequest, PrimeNumbersServiceClient}

class PrimeNumbersService(client: PrimeNumbersServiceClient) {

  def getPrimeNumbers(num: Int): Either[ApplicationError, Source[Int, NotUsed]] =
    if (num < 1) Left(InvalidParameterError(PRIME_ATTRIBUTE, num.toString))
    else Right(client.getPrimeNumbers(GetPrimeNumbersRequest(num)).map(_.number))
}
