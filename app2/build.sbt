name := "app2"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.apache.kafka" %% "kafka" % "0.8.0" excludeAll (
     ExclusionRule(organization = "com.sun.jdmk"),
     ExclusionRule(organization = "com.sun.jmx"),
     ExclusionRule(organization = "javax.activation"),
     ExclusionRule(organization = "javax.jms"),
     ExclusionRule(organization = "javax.mail"),
     ExclusionRule(organization = "org.slf4j")
  )
)     

play.Project.playScalaSettings
