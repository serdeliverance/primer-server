package com.dixa

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.dixa.route.PrimeNumbersRoute.primeNumbersRoute
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object ProxyServer extends App {
  implicit val system           = ActorSystem(Behaviors.empty, "ProxyServer")
  implicit val executionContext = system.executionContext

  val config = ConfigFactory.load()

  val host = config.getString("host")
  val port = config.getInt("port")

  val binding = Http().newServerAt(host, port).bind(primeNumbersRoute)

  system.log.info(s"Server running on ${host}:${port}\nPress RETURN to stop...")
  StdIn.readLine()
  binding
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
