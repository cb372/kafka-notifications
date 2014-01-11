package models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/**
 * Author: chris
 * Created: 1/11/14
 */
object JsonSerializer {
  val objectMapper = new ObjectMapper() with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)

  def toJsonString(x: Any): String = {
    objectMapper.writeValueAsString(x)
  }
}
