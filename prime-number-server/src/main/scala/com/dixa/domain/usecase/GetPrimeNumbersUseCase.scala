package com.dixa.domain.usecase

import com.dixa.domain.entity.{DomainError, InvalidNumberError}
import com.dixa.domain.functions.NumericFunctions.{generateNaturalNumbersUntilN, isPrime}
import com.dixa.port.in.GetPrimeNumbersService

case class GetPrimeNumbersUseCase() extends GetPrimeNumbersService {

  def getPrimeNumbers(n: Int): Either[DomainError, LazyList[Int]] =
    if (n <= 0) Left(InvalidNumberError(n))
    else if (n == 1) Right(LazyList.cons(1, LazyList.empty))
    else Right(generateNaturalNumbersUntilN(n).filter(isPrime))
}
