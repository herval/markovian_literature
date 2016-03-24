import NativePackagerKeys._


name := "markovian_literature"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

packageArchetype.java_application
