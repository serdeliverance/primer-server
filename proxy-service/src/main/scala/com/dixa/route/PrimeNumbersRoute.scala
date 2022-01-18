package com.dixa.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.util.ByteString
import com.dixa.model.ResponseError
import com.dixa.service.PrimeNumbersService
import spray.json.DefaultJsonProtocol._

class PrimeNumbersRoute(primeNumbersService: PrimeNumbersService) {

  implicit val responseErrorFormat = jsonFormat1(ResponseError)

  val routes =
    path("prime" / IntNumber) { number =>
      get {
        primeNumbersService.getPrimeNumbers(number) match {
          case Left(error) =>
            complete(BadRequest, ResponseError(error.getMessage()))
          case Right(primeNumbersSource) =>
            complete(
              HttpEntity(
                ContentTypes.`text/plain(UTF-8)`,
                primeNumbersSource
                  .map(number => s"$number, ")
                  .map(str => ByteString(str))
              )
            )
        }
      }
    }
}
