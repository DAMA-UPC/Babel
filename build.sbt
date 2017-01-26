name := "Babel"

version := "0.0.1-SNAPSHOT"

organization := "edu.upc.dama"

scalaVersion := "2.11.8"

/*************   DEPENDENCIES   *************/

val shapelessVersion = "2.3.2"
val scalametaVersion = "1.5.0"
val scalametaParadiseVersion = "3.0.0-beta4"
val sourceCodeVersion = "0.1.2"
val catsVersion = "0.9.0"
val circeVersion = "0.7.0"

libraryDependencies ++= {
  Seq(
    "com.chuusai" %% "shapeless" % shapelessVersion,
    // http://scalameta.org/
    "org.scalameta" %% "scalameta" % scalametaVersion,
    // https://github.com/lihaoyi/sourcecode
    "com.lihaoyi" %% "sourcecode" % sourceCodeVersion,
    // https://github.com/typelevel/cats
    "org.typelevel" %% "cats" % catsVersion,
    // https://github.com/circe/circe
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion
  )
}

addCompilerPlugin("org.scalameta" % "paradise" % scalametaParadiseVersion cross CrossVersion.full)

scalacOptions += "-Xplugin-require:macroparadise"

/**********  TEST DEPENDENCIES   ************/

libraryDependencies ++= {
  val specs2Version = "3.8.7"
  val scalaCheckVersion = "1.13.4"
  Seq(
    // Specs2 Test Framework - https://etorreborre.github.io/specs2/
    "org.specs2" %% "specs2-core" % specs2Version % "test",
    "org.specs2" %% "specs2-matcher-extra" % specs2Version % "test",
    "org.specs2" %% "specs2-junit" % specs2Version % "test",
    "org.specs2" %% "specs2-scalacheck" % specs2Version % "test",
    // ScalaCheck - https://scalacheck.org/
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
  )
}

/*************   TEST OPTIONS   *************/

scalacOptions in Test ++= Seq("-Yrangepos")

parallelExecution in Test := true

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

/************    WartRemover    *************/

wartremoverErrors in (Compile, compile) ++= Warts.allBut(Wart.FinalCaseClass)
wartremoverErrors in (Test, test) ++= Warts.allBut(Wart.FinalCaseClass, Wart.NonUnitStatements)

/*************    Scapegoat    **************/

scapegoatVersion := "1.3.0"

import com.sksamuel.scapegoat.sbt.ScapegoatSbtPlugin.autoImport.scapegoat
(test in Test) := ((test in Test) dependsOn scapegoat).value

/************    ScalaStyle    **************/

scalastyleFailOnError := true

lazy val testScalastyle = taskKey[Unit]("testScalastyle")
testScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Test).toTask("").value
(test in Test) := ((test in Test) dependsOn testScalastyle).value
