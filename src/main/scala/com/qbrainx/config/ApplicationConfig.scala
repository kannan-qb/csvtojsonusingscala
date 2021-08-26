package com.qbrainx.config

import com.typesafe.config.{Config, ConfigFactory}

object ApplicationConfig {
  val config: Config = ConfigFactory.load().getConfig("csvtojson")
}
