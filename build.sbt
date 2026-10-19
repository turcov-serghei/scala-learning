ThisBuild / scalaVersion := "2.13.18"

ThisBuild / libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.13.0"
)

lazy val root = (project in file("."))
  .settings(
    name := "scala-learning"
  )
