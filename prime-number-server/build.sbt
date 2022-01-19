name := "prime-number-server"

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.dixa"
ThisBuild / organizationName := "dixa"

enablePlugins(AkkaGrpcPlugin)

akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server)

val AkkaVersion = "2.6.18"
val MockitoCoreVersion = "3.5.13"
val MockitoScalaVersion = "1.16.42"
val ScalatestVersion = "3.2.9"
val LogbackVersion = "1.2.3"

lazy val root =
  project in file(".")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  // Test
  "org.mockito"            % "mockito-core"        % MockitoCoreVersion % Test,
  "org.mockito"            %% "mockito-scala"        % MockitoScalaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % ScalatestVersion % Test
)