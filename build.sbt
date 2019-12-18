import sbt.Keys._


scalaVersion := tnm.ScalaVersion.curr
crossScalaVersions := Seq(tnm.ScalaVersion.curr, tnm.ScalaVersion.prev, tnm.ScalaVersion.aged)

val core = Project("enum-utils", file("core"))
  .enablePlugins(OssLibPlugin)
  .settings(organization := "com.thenewmotion",
    name := "enum-utils",
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "4.8.1" % "test"
    )
  )

val sprayJson = Project("enum-utils-spray-json", file("spray-json"))
  .enablePlugins(OssLibPlugin)
  .dependsOn(core)
  .settings(
    organization := "com.thenewmotion",
    name := "enum-utils-spray-json",
    libraryDependencies ++= Seq(
      "io.spray"    %% "spray-json"   % "1.3.5",
      "org.specs2"  %% "specs2-core"  % "4.8.1" % "test"
    )
  )


val parent = Project("enum-utils-parent", file("."))
  .aggregate(core, sprayJson)
  .enablePlugins(OssLibPlugin)
  .settings(publish := {})
