import sbt._
import Keys._
import com.typesafe.sbt.pgp.PgpKeys._

object PublishSettingsScalaJS {
  type Sett = Def.Setting[_]

  lazy val all = Seq[Sett](
    resolvers += Resolver.bintrayRepo("j-keck", "argonaut-scala.js")
  , licenses += ("BSD", url("https://raw.githubusercontent.com/argonaut-io/argonaut/master/LICENCE"))
  )
}
