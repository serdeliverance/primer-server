package com.dixa.domain.entity

sealed trait DomainError
case class InvalidNumberError(num: Int) extends DomainError
