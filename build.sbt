import sbt.Keys._

val libraries = Seq(
  "io.spray" %% "spray-json" % "1.3.2",
  "org.specs2" %% "specs2-core" % "3.8.5" % "test"
)

lazy val root = (project in file("."))
  .enablePlugins(LibPlugin)
  .settings(
  name := "tnm-utils",
  description := "Useful bits of code for TNM applications",
  organization := "com.thenewmotion",
  libraryDependencies ++= libraries
)