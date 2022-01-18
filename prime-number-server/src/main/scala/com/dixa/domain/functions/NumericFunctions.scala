package com.dixa.domain.functions

import scala.annotation.tailrec

object NumericFunctions {
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeTailRec(currentDivisor: Int): Boolean =
      if (currentDivisor == n) true
      else if (n % currentDivisor == 0) false
      else isPrimeTailRec(currentDivisor + 1)

    if (n == 1) true
    else isPrimeTailRec(2)
  }

  def generateNaturalNumbersUntilN(top: Int): LazyList[Int] = {
    def doGenerateNaturalNumbersUntilN(current: Int, top: Int): LazyList[Int] =
      LazyList
        .cons(current, {
          val next = current + 1
          if (next > top) LazyList.empty else doGenerateNaturalNumbersUntilN(next, top)
        })

    doGenerateNaturalNumbersUntilN(1, top)
  }
}
