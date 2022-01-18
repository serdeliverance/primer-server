package com.dixa

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings
import akka.http.scaladsl.Http
import com.dixa.route.PrimeNumbersRoute
import com.dixa.service.PrimeNumbersService
import com.typesafe.config.ConfigFactory
import prime.PrimeNumbersServiceClient

import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object ProxyServer extends App {
  implicit val system           = ActorSystem(Behaviors.empty, "ProxyServer")
  implicit val executionContext = system.executionContext

  val config = ConfigFactory.load()

  val host = config.getString("host")
  val port = config.getInt("port")

  val primeServerHost = config.getString("prime-server.host")
  val primeServerPort = config.getInt("prime-server.port")

  val clientSettings = GrpcClientSettings.connectToServiceAt(primeServerHost, primeServerPort).withTls(false)

  val client = PrimeNumbersServiceClient(clientSettings)

  val primeNumbersService = new PrimeNumbersService(client)

  val primeNumbersRoute = new PrimeNumbersRoute(primeNumbersService)

  val bound = Http()
    .newServerAt(host, port)
    .bind(primeNumbersRoute.routes)
    .map(_.addToCoordinatedShutdown(5.seconds))

  bound.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      system.log.info("server running on: {}:{}", address.getHostString, address.getPort)
    case Failure(ex) =>
      system.log.error("Failed to start web server, terminating system", ex)
      system.terminate()
  }
}
