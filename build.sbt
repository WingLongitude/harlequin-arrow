name := """sibcolombia-indexer-backend"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.1.0-M2",
  "com.typesafe.play" %% "play-slick" % "0.6.1",
  "joda-time" % "joda-time" % "2.3",
  filters,
  cache,
  "com.codahale.metrics" % "metrics-core" % "3.0.1",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  // API generator library and utils
  "com.wordnik" %% "swagger-play2" % "1.3.7",
  "com.wordnik" %% "swagger-play2-utils" % "1.3.7"
)

play.Project.playScalaSettings