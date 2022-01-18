package com.dixa

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import com.dixa.adapter.grpc.GrpcPrimeNumberService
import com.dixa.domain.usecase.GetPrimeNumbersUseCase
import com.typesafe.config.ConfigFactory
import prime.PrimeNumbersServiceHandler

import scala.concurrent.Future

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
  val binding = Http().newServerAt(host, port).bind(service)

  binding.foreach { binding =>
    system.log.info(s"gRPC server running on: ${binding.localAddress}")
  }
}
