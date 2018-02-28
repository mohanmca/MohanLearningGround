package ground.learning.json

import scala.util.parsing.json.JSON

object JsonNativeParser extends App {
  val json = """{"name" : "value"}"""
  val jsonObject = JSON.parseRaw(json)
  println(JSON.getClass)
  println(JSON.parseRaw(json))
}