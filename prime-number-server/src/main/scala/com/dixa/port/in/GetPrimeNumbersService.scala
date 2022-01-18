package com.dixa.port.in

import com.dixa.domain.entity.DomainError

trait GetPrimeNumbersService {
  def getPrimeNumbers(n: Int): Either[DomainError, LazyList[Int]]
}
