package com.dixa.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.LongNumber
import akka.http.scaladsl.server.directives.RouteDirectives.complete

object PrimeNumbersRoute {
  val primeNumbersRoute =
    path("prime" / LongNumber) { number =>
      get {
        complete(StatusCodes.OK, number.toString)
      }
    }
}
