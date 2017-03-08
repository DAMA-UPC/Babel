name := "Babel"

version := "0.0.1-SNAPSHOT"

organization := "edu.upc.dama"

scalaVersion := "2.12.1"

/*************   DEPENDENCIES   *************/

lazy val dependencies: Seq[Def.Setting[_]] = Seq(
  scalaVersion := "2.12.1",
  libraryDependencies ++= {
    val shapelessVersion = "2.3.2"
    val sourceCodeVersion = "0.1.3"
    val catsVersion = "0.9.0"
    val circeVersion = "0.7.0"
    val specs2Version = "3.8.8"
    val scalaCheckVersion = "1.13.4"
    Seq(
      // https://github.com/milessabin/shapeless
      "com.chuusai" %% "shapeless" % shapelessVersion,
      // https://github.com/lihaoyi/sourcecode
      "com.lihaoyi" %% "sourcecode" % sourceCodeVersion,
      // https://github.com/typelevel/cats
      "org.typelevel" %% "cats" % catsVersion,
      // https://github.com/circe/circe
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      // Specs2 Test Framework - https://etorreborre.github.io/specs2/
      "org.specs2" %% "specs2-core" % specs2Version % "test",
      "org.specs2" %% "specs2-matcher-extra" % specs2Version % "test",
      "org.specs2" %% "specs2-junit" % specs2Version % "test",
      "org.specs2" %% "specs2-scalacheck" % specs2Version % "test",
      // ScalaCheck - https://scalacheck.org/
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
    )
  },
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M7" cross CrossVersion.full)
)

/*************   TEST OPTIONS   *************/

scalacOptions in Test ++= Seq("-Yrangepos")

parallelExecution in Test := true

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

/************    WartRemover    *************/

wartremoverErrors in (Compile, compile) ++= Warts.allBut(Wart.FinalCaseClass)
wartremoverErrors in (Test, test) ++= Warts.allBut(Wart.FinalCaseClass, Wart.NonUnitStatements)

/************    ScalaStyle    **************/

scalastyleFailOnError := true

lazy val testScalastyle = taskKey[Unit]("testScalastyle")
testScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Test).toTask("").value
(test in Test) := ((test in Test) dependsOn testScalastyle).value

/**************    Modules    ***************/

// Define macros in this project.
lazy val types = project.settings(
  dependencies,
  // A dependency on macro paradise 3.x is required to both write and expand
  // new-style macros.  This is similar to how it works for old-style macro
  // annotations and a dependency on macro paradise 2.x.
  scalacOptions += "-Xplugin-require:macroparadise",
  // temporary workaround for https://github.com/scalameta/paradise/issues/10
  scalacOptions in (Compile, console) := Seq(), // macroparadise plugin doesn't work in repl yet.
  // temporary workaround for https://github.com/scalameta/paradise/issues/55
  sources in (Compile, doc) := Nil, // macroparadise doesn't work with scaladoc yet.
  // A dependency on scala.meta is required to write new-style macros, but not
  // to expand such macros.  This is similar to how it works for old-style
  // macros and a dependency on scala.reflect.
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.6.0"
)

// Use macros in this project.
lazy val core = project.settings(dependencies).dependsOn(types)
