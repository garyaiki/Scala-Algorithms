name := "Scala-Algorithms"

version := "0.0.3"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"

resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"

val slf4jVersion = "1.7.25"

libraryDependencies += "org.slf4j" % "slf4j-api" % slf4jVersion

libraryDependencies += "org.slf4j" % "slf4j-simple" % slf4jVersion



