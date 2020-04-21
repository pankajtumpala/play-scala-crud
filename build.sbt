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
      "com.h2database" % "h2" % "1.4.192",
      "com.mohiva" %% "play-silhouette" % "7.0.0",
      "com.mohiva" %% "play-silhouette-password-bcrypt" % "7.0.0",
      "com.mohiva" %% "play-silhouette-crypto-jca" % "7.0.0",
      "com.mohiva" %% "play-silhouette-persistence" % "7.0.0",
      "com.mohiva" %% "play-silhouette-testkit" % "7.0.0" % "test",
      "net.codingwell" %% "scala-guice" % "4.2.6",
      "com.iheart" %% "ficus" % "1.4.7",
      "com.typesafe.play" %% "play-json" % "2.8.1",
      "com.typesafe.play" %% "play-json-joda" % "2.8.1",
      caffeine
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
 
swaggerDomainNameSpaces := Seq("models")

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"
