// properties
val APP_VERSION = "0.1.0-SNAPSHOT"
val SCALA_VERSION = "2.11.8"
val SPARK_VERSION = "1.6.2"

// settings
name := "spark-rx"
organization := "com.uralian"
version := APP_VERSION
scalaVersion := SCALA_VERSION
crossScalaVersions := Seq("2.10.5", SCALA_VERSION)

// building
scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-Xlint", "-language:_", "-target:jvm-1.7", "-encoding", "UTF-8")
run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

// publishing docs to github
site.settings
site.includeScaladoc()
ghpages.settings
git.remoteRepo := "https://github.com/uralian/spark-rx.git"

// publishing to maven repo
publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
pomIncludeRepository := { _ => false }
pomExtra := (
  <url>https://github.com/uralian/spark-rx</url>
  <licenses>
    <license>
      <name>The Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>scm:git:https://github.com/uralian/spark-rx.git</url>
    <connection>scm:git:git@github.com:uralian/spark-rx.git</connection>
  </scm>
  <developers>
    <developer>
      <id>snark</id>
      <name>Vlad Orzhekhovskiy</name>
      <email>vlad@uralian.com</email>
      <url>http://uralian.com</url>
    </developer>
  </developers>)
  
pgpSecretRing := file("local.secring.gpg")

pgpPublicRing := file("local.pubring.gpg")

// dependencies
val sparkLibs = Seq(
  "org.apache.spark"         %% "spark-core"                 % SPARK_VERSION           % "provided",
  "org.apache.spark"         %% "spark-streaming"            % SPARK_VERSION           % "provided",
  "org.apache.spark"         %% "spark-streaming-kafka"      % SPARK_VERSION           % "provided",
  "org.apache.spark"         %% "spark-sql"                  % SPARK_VERSION           % "provided",
  "org.apache.spark"         %% "spark-mllib"                % SPARK_VERSION           % "provided",
  "org.apache.spark"         %% "spark-hive"                 % SPARK_VERSION           % "provided"
)

libraryDependencies ++= Seq(
  "io.reactivex"             %% "rxscala"                    % "0.26.4",
  "org.scalatest"            %% "scalatest"                  % "2.2.1"                 % "test",
  "org.scalacheck"           %% "scalacheck"                 % "1.12.1"                % "test",
  "org.mockito"               % "mockito-core"               % "1.10.19"               % "test"
) ++ sparkLibs
