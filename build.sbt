name := "Scala-Algorithms"

version := "0.0.2"

scalaVersion := "2.12.3"

scalacOptions ++= Seq("-deprecation", "-feature")

//scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"

libraryDependencies += "junit" % "junit" % "4.12" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"

resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5"

libraryDependencies += "org.specs2" %% "specs2-scalacheck" % "4.0.1" % "test"

val slf4jVersion = "1.7.25"

libraryDependencies += "org.slf4j" % "slf4j-api" % slf4jVersion

libraryDependencies += "org.slf4j" % "slf4j-simple" % slf4jVersion



