name := "SportsCommon"

version := "1.0"

organization := "com.marcom"

scalaVersion := "2.12.1"

libraryDependencies ++= {
  val scalaLibVersion = scalaVersion.value
  val akkaLibVersion = "2.4.16"
  val playVersion = "2.6.0-M2"
  lazy val akkaHttpVersion = "10.0.3"
  Seq(
    "org.scala-lang" % "scala-reflect" % scalaLibVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.play" %% "play-json" % playVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "org.slf4j" % "slf4j-api" % "1.7.12",
    "org.typelevel" %% "cats-core" % "0.9.0",
    "com.typesafe.akka" %% "akka-testkit" % akkaLibVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.1" % Test
  )
}


    