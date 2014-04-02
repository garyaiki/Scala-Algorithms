name := "Scala-Algorithms"

version := "0.0.1"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-deprecation", "-feature")

//scalacOptions in Test ++= Seq("-Yrangepos")

site.settings

site.includeScaladoc()

org.scalastyle.sbt.ScalastylePlugin.Settings

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"

resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.3"

libraryDependencies += "org.specs2" %% "specs2" % "2.3.10" % "test"

val slf4jVersion = "1.7.6"

libraryDependencies += "org.slf4j" % "slf4j-api" % slf4jVersion

libraryDependencies += "org.slf4j" % "slf4j-simple" % slf4jVersion



