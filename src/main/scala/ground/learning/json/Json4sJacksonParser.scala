package ground.learning.json

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.HashMap

object Json4sJacksonParser extends App {

  val jsonString = """
         { "name": "joe",
           "children": [
             {
               "name": "Mary",
               "age": 5
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """

  val objectMapper = new ObjectMapper();
  val myMap = objectMapper.readValue(jsonString, classOf[HashMap[String, Any]]);
  System.out.println("Map is: " + myMap);

  val jsonObject = parse(jsonString)

  println(jsonObject.getClass)
  println(jsonObject)
  
  println(jsonObject \ "name")
}