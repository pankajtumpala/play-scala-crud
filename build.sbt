lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SwaggerPlugin)
  .settings(
    name := """play scala crud""",
    version := "2.8.x",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "mysql" % "mysql-connector-java" % "8.0.15",
      specs2 % Test,
      "org.webjars" % "swagger-ui" % "2.2.0",
      "org.flywaydb" %% "flyway-play" % "6.0.0",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.h2database" % "h2" % "1.4.192"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
 
swaggerDomainNameSpaces := Seq("models")

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"
