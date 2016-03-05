resolvers += Resolver.sonatypeRepo("releases")

scalacOptions += "-deprecation"

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.2")

addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.1.6")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.7")

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

