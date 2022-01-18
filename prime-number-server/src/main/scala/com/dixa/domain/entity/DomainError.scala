package com.dixa.domain.entity

sealed trait DomainError {
  def getMessage(): String
}
case class InvalidNumberError(num: Int) extends DomainError {
  override def getMessage(): String = s"Invalid number $num"
}
