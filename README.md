
  * fork of [https://github.com/argonaut-io/argonaut](https://github.com/argonaut-io/argonaut) 
  * only for scala.js bits
  * i publish the binary packages for scala-2.11 and scala-js-0.6.2 under https://bintray.com/j-keck/maven/argonaut/view

# SBT usage

add the bintray repo: 

    resolvers += Resolver.bintrayRepo("j-keck", "maven")

packages for scala-js (note the three %%%):

    libraryDependencies += "io.argonaut" %%% "argonaut" % "6.1"


argonaut dependencies:

    libraryDependencies += "com.github.japgolly.fork.scalaz" %%% "scalaz-core" % "7.1.2"
    libraryDependencies += "com.github.japgolly.fork.monocle" %%% "monocle-core" % "1.1.1"
    libraryDependencies += "com.github.japgolly.fork.monocle" %%% "monocle-macro" % "1.1.1"

    addCompilerPlugin(compilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full))
