import sbt.Keys._
import pl.project13.scala.sbt.JmhPlugin

val libVersion = "1.0"

val scala = "2.12.1"

def commonSettings(name: String) = Seq(
  scalaVersion := scala,
  version := "1.0"
)

lazy val root = (project in file("."))
  .settings(commonSettings("scala-sandbox"))

lazy val performance = (project in file("modules/performance"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings("performance"))
  .settings(
    javaOptions in(Jmh, run) ++= Seq("-Xmx2G", "-Dfile.encoding=UTF8")
  )

