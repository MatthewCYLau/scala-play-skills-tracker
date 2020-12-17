name := """scala-play-skills-tracker"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  jdbc,
  "org.postgresql" % "postgresql" % "42.1.1",
  "org.playframework.anorm" %% "anorm" % "2.6.7",
  "org.flywaydb" %% "flyway-play" % "6.0.0",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
