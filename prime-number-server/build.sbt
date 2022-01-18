import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.dixa"
ThisBuild / organizationName := "dixa"

enablePlugins(AkkaGrpcPlugin)

val AkkaVersion = "2.6.18"
val AkkaHttpVersion = "10.2.7"
val MockitoCoreVersion = "3.5.13"
val MockitoScalaVersion = "1.16.42"

lazy val root =
  project in file(".")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  // Test
  "org.mockito"            % "mockito-core"        % MockitoCoreVersion % Test,
  "org.mockito"            %% "mockito-scala"        % MockitoScalaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  scalaTest % Test
)