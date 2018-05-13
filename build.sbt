name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test

val browserifyTask = taskKey[Seq[File]]("Run browserify")

val browserifyOutputDir = settingKey[File]("Browserify output directory")

browserifyOutputDir := target.value / "web" / "dist"

browserifyTask := {
  println("Running browserify");
  val outputFile = browserifyOutputDir.value / "main.js"
  browserifyOutputDir.value.mkdirs
  "./node_modules/.bin/browserify.cmd -t [ babelify --presets [ es2015 ] ] app/assets/javascripts/main.js -o " + outputFile.getPath !;
  Nil
}

sourceGenerators in Assets += browserifyTask.taskValue

unmanagedResources in Assets += baseDirectory.value / "target/web/dist/main.js"
