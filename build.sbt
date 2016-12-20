name := "factor-former"

version := "1.0"

scalaVersion := "2.12.1"

/*************   DEPENDENCIES   *************/

libraryDependencies ++= {
  val catsVersion = "0.8.1"
  Seq(
    // https://github.com/typelevel/cats
    "org.typelevel" %% "cats" % catsVersion
  )
}

/**********  TEST DEPENDENCIES   ************/

libraryDependencies ++= {
  val specs2Version = "3.8.6"
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