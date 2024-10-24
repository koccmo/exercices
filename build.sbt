ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"


lazy val root = (project in file("."))
  .settings(
    name := "exercices"
  )


libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0"
addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)


run / fork := true
run / connectInput := true
run / outputStrategy := Some(StdoutOutput)