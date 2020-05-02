name := "functional-shopping-cart-zio"

organization := "ayon"
version := "0.0.1"

lazy val akkaHttpVersion = "10.1.8"
lazy val akkaVersion = "2.6.0-M2"
val CatsVersion = "2.1.1"
val CirceVersion = "0.13.0"
val zio_version   = "1.0.0-RC18-2"
scalaVersion := "2.12.8"

lazy val config = (project in file("."))
  .settings(
    //DEPENDENCIES
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zio_version,
      "io.circe" %% "circe-core" % CirceVersion,
      "io.circe" %% "circe-config" % "0.8.0",
      "io.circe" %% "circe-parser" % CirceVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "io.circe" %% "circe-generic-extras" % CirceVersion,
      "org.typelevel" %% "cats-core" % CatsVersion,
      "de.heikoseeberger" %% "akka-http-circe" % "1.32.0"
    )
  )

scalacOptions ++= Seq("-language:postfixOps" // allow writing e.g. `5 seconds` with a space in between
)
    