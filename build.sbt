name := "wordify"

version := "0.1"

scalaVersion := "2.12.6"

mainClass in assembly := Some("com.wordify.main.Main")
assemblyJarName in assembly := "wordify.jar"

mainClass in (Compile, run) := Some("com.wordify.main.Main")
mainClass in (Compile, packageBin) := Some("com.wordify.main.Main")

val catsCoreVersion = "1.0.1"
val shapelessVersion = "2.3.3"
val scalaTestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "org.typelevel"     %% "cats-core"       % catsCoreVersion,
  "com.chuusai"       %% "shapeless"       % shapelessVersion,
  "org.scalatest"     %% "scalatest"       % scalaTestVersion % "test"
)