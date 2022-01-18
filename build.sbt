import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.dixa"
ThisBuild / organizationName := "dixa"

enablePlugins(AkkaGrpcPlugin)

lazy val `proxy-server` =
  project
    .in(file("proxy-service"))

lazy val `prime-number-server` =
  project
    .in(file("prime-number-server"))
    .settings(commonDependencies)

lazy val proto =
  project
    .in(file("proto"))

lazy val commonDependencies =
  libraryDependencies ++= Seq(
    scalaTest % Test
  )