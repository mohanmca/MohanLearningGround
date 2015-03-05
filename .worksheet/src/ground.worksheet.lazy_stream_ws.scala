package ground.worksheet

import ground.learning.lasy.streams._

object lazy_stream_ws {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(134); 
  println("Welcome to the Scala worksheet");$skip(45); 
  Streams.natNumbers.take(3) foreach println;$skip(39); 
  Streams.fibs take 10 foreach println}
}
