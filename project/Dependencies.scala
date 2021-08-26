import sbt._
object Dependencies {

  lazy val typeSafeConfigVersion = "1.4.1"

  lazy val typeSafeConfig = "com.typesafe" % "config" % typeSafeConfigVersion

  def compileDependenceies:Seq[ModuleID]=Seq(typeSafeConfig)

}
