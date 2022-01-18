package com.dixa.domain.entity

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class InvalidNumberErrorSpec extends AnyWordSpec with Matchers {

  "invalid number error contains expected message" in {

    val error  = InvalidNumberError(-1)
    val result = error.getMessage()

    result mustBe "Invalid number -1"
  }
}
