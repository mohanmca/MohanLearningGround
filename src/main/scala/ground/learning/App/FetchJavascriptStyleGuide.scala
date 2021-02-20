//package ground.learning.App
//
//import scalaj.http._
//
//import java.nio.file.{ Paths, Files }
//import java.nio.charset.StandardCharsets
//import org.jsoup.Jsoup
//import org.jsoup.select.Elements
//
//object FetchJavascriptStyleGuide extends App {
//  val file = Paths.get("response.txt")
//  def downloadAndSave() = {
//    val response: HttpResponse[String] = Http("https://github.com/airbnb/javascript/blob/master/README.md").asString
//    val content = new String(response.body.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
//    Files.write(file, content.getBytes(StandardCharsets.UTF_8))
//    Files.write(Paths.get("response_meta.txt"), response.cookies.toString().getBytes(StandardCharsets.UTF_8))
//  }
//
//  def parseHtml() = {
//    val html = new String(Files.readAllBytes(file), StandardCharsets.UTF_8)
//    val doc = Jsoup.parse(html);
//    val links = doc.select("""div#readme""");
//    import scala.collection.JavaConverters._
//    links.asScala.toList.flatMap(elem => elem.select("li > p").asScala.map(element => element.text())).zipWithIndex
//  }
//
//  val result = parseHtml().filter(_._1.length() > 10).map( item => item._2 + "\t:\t" + item._1)
//  result.foreach(println)
//
//}