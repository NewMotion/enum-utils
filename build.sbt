import sbt.Keys._

val specs2 = "org.specs2" %% "specs2-core" % "3.9.5" % "test"

def baseProject(id: String, base: String) =
  Project(id, file(base))
    .enablePlugins(OssLibPlugin)
    .settings(
      organization := "com.thenewmotion",
      name := id
    )

val core = baseProject("enum-utils", "core")
  .settings(
    libraryDependencies ++= Seq(specs2)
  )

val sprayJson = baseProject("enum-utils-spray-json", "spray-json")
  .dependsOn(core)
  .settings(
    libraryDependencies ++= Seq(
      "io.spray"    %% "spray-json"   % "1.3.3",
      specs2
    )
  )

val circeV = "0.10.0"

val circe = baseProject("enum-utils-circe", "circe")
  .dependsOn(core)
  .settings(
    scalacOptions ++= Seq(
      "-Ywarn-macros:after" // Get rid of "is never used" warnings for implicit encoders and decoders
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeV,
      "io.circe" %% "circe-generic" % circeV % "test",
      "io.circe" %% "circe-parser" % circeV % "test",
      specs2
    )
  )


val parent = Project("enum-utils-parent", file("."))
  .aggregate(core, sprayJson, circe)
  .enablePlugins(LibPlugin)
  .settings(publish := {})
