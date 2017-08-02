name := "Babel"

lazy val commonSettings: Seq[Def.Setting[_]] = Seq(
  scalaVersion := "2.12.3",
  organization := "edu.upc.dama"
)

/*************   DEPENDENCIES   *************/

lazy val dependencies: Seq[Def.Setting[_]] = Seq(
  libraryDependencies ++= {
    val shapelessVersion = "2.3.2"
    val sourceCodeVersion = "0.1.4"
    val catsVersion = "0.9.0"
    val circeVersion = "0.8.0"
    val specs2Version = "3.9.4"
    val scalaCheckVersion = "1.13.5"
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
      "io.circe" %% "circe-java8" % circeVersion,
      // Specs2 Test Framework - https://etorreborre.github.io/specs2/
      "org.specs2" %% "specs2-core" % specs2Version % "test",
      "org.specs2" %% "specs2-matcher-extra" % specs2Version % "test",
      "org.specs2" %% "specs2-junit" % specs2Version % "test",
      "org.specs2" %% "specs2-scalacheck" % specs2Version % "test",
      // ScalaCheck - https://scalacheck.org/
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
    )
  },
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full)
)

lazy val macroDependencies: Seq[Def.Setting[_]] = Seq(
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
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0",
  libraryDependencies += "org.scalameta" %% "contrib" % "1.8.0"
)

/*************   TEST OPTIONS   *************/

scalacOptions in Test ++= Seq("-Yrangepos")

parallelExecution in Test := true

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

/************    WartRemover    *************/

wartremoverErrors in (Compile, compile) ++= Warts.allBut(Wart.FinalCaseClass)
wartremoverErrors in (Test, test) ++= Warts.allBut(Wart.FinalCaseClass, Wart.NonUnitStatements)

/*********    Bintray Publishing    *********/

val bintraySettings = Seq(
  bintrayOrganization := Some("dama-upc"),
  bintrayRepository := "Babel-Platform",
  licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  homepage := Some(url("https://github.com/DAMA-UPC/Babel"))
)

/**************    Modules    ***************/

lazy val root = (project in file(".")).settings(commonSettings, bintraySettings)
lazy val types = project.settings(commonSettings, dependencies, bintraySettings, macroDependencies)
lazy val generators = project.settings(commonSettings, dependencies, bintraySettings)
lazy val core = project.settings(commonSettings, dependencies, bintraySettings).dependsOn(types, generators)
