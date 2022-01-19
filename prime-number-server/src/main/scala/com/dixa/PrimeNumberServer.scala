package com.dixa

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import com.dixa.adapter.grpc.GrpcPrimeNumberService
import com.dixa.domain.usecase.GetPrimeNumbersUseCase
import com.typesafe.config.ConfigFactory
import prime.PrimeNumbersServiceHandler

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object PrimeNumberServer extends App {

  // basic infrastructure setup
  implicit val system = ActorSystem("PrimeNumberServer")
  implicit val ec     = system.dispatcher

  val config = ConfigFactory.load()

  val host = config.getString("host")
  val port = config.getInt("port")

  // service and components instantiation

  val getPrimeNumbersService = GetPrimeNumbersUseCase()

  val service: HttpRequest => Future[HttpResponse] =
    PrimeNumbersServiceHandler(new GrpcPrimeNumberService(getPrimeNumbersService))

  // final http server binding
  val bound = Http()
    .newServerAt(host, port)
    .bind(service)
    .map(_.addToCoordinatedShutdown(5.seconds))

  bound.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      system.log.info("gRPC server running on: {}:{}", address.getHostString, address.getPort)
    case Failure(ex) =>
      system.log.error("Failed to bind gRPC endpoint, terminating system", ex)
      system.terminate()
  }
}
