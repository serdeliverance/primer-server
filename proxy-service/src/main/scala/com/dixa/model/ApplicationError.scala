package com.dixa.model

sealed trait ApplicationError {
  def getMessage(): String
}
case class InvalidParameterError(entity: String, value: String) extends ApplicationError {
  override def getMessage(): String = s"Invalid parameter $entity=$value"
}
