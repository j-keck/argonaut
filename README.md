i publish the 6.2 version to bintray when it's released

  * fork of [https://github.com/argonaut-io/argonaut](https://github.com/argonaut-io/argonaut) for scala.js
  * i publish the binary packages for scala-2.11 and scala-js-0.6.7 under https://bintray.com/j-keck/maven/argonaut/view
  * branches:
    - scala.js    : argonaut 6.1
    - 6.2-scala.js: argonaut 6.2


# SBT usage

add the bintray repo: 

    resolvers += Resolver.bintrayRepo("j-keck", "maven")

packages for scala-js (note the three %%%):

     libraryDependencies ++= Seq(
       "io.argonaut" %%% "argonaut" % "6.2-SNAPSHOT" changing()
     )
