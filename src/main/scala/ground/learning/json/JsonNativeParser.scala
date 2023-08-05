package ground.learning.json

import play.api.libs.json._
object JsonNativeParser extends App {
  val json = """{"name" : "value"}"""
  val jsonObject = Json.parse(json)
}