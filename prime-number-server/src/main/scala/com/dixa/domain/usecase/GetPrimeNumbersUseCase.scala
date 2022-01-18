package com.dixa.domain.usecase

import com.dixa.domain.entity.{DomainError, InvalidNumberError}
import com.dixa.domain.functions.NumericFunctions.{isPrime, generateNaturalNumbersUntilN}

import scala.annotation.tailrec

case class GetPrimeNumbersUseCase() {

  def getPrimeNumbers(n: Int): Either[DomainError, LazyList[Int]] = {
    @tailrec
    def getPrimeNumbersTailrec(current: Int): LazyList[Int] =
      current match {
        case num if num == n =>
          if (isPrime(current)) LazyList.cons(num, LazyList.empty)
          else LazyList.empty
        case _ =>
          if (isPrime(current)) LazyList.cons(current, LazyList.empty) // TODO
          else getPrimeNumbersTailrec(current + 1)
      }

    if (n <= 0) Left(InvalidNumberError(n))
    else if (n == 1) Right(LazyList.cons(1, LazyList.empty))
    else Right(generateNaturalNumbersUntilN(n).filter(isPrime))
  }
}