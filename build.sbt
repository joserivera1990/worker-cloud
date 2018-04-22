name := "scala"

version := "0.1"

scalaVersion := "2.12.2"

lazy val root = (project in file(".")).enablePlugins(JavaServerAppPackaging)

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.11.297"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.297"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "net.bramp.ffmpeg" % "ffmpeg" % "0.6.2"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.5"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.9"


resolvers ++= Seq(Resolver.jcenterRepo,
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "DynamoDBLocal" at "https://s3-us-west-2.amazonaws.com/dynamodb-local/release/",
  "bintray-sbt-plugin-releases" at "http://dl.bintray.com/content/sbt/sbt-plugin-releases"
)