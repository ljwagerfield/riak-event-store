import sbt.Keys._

org.scalastyle.sbt.ScalastylePlugin.Settings

net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "riak-event-store"

version := "1.0"

scalacOptions += "-unchecked"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

scalaVersion := "2.11.0-RC3"

org.scalastyle.sbt.ScalastylePlugin.Settings

net.virtualvoid.sbt.graph.Plugin.graphSettings

libraryDependencies += "org.specs2" %% "specs2-core" % "2.3.10" % "test"

libraryDependencies += "net.sandrogrzicic" % "scalabuff-runtime_2.11.0-M7" % "1.3.7"
