name := "inteticsapp"

version := "1.0"

lazy val `inteticsapp` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  javaJdbc,
  javaEbean,
  cache,
  javaWs)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  