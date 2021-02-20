//package ground.learning.App
//
//object FetchWebPage extends App {
//
//  import scalaj.http._
//
//  import java.nio.file.{ Paths, Files }
//  import java.nio.charset.StandardCharsets
//
//  val response: HttpResponse[String] = Http("https://adobe.wd5.myworkdayjobs.com/en-US/external_experienced/").asString
//  Files.write(Paths.get("response.txt"), response.body.getBytes(StandardCharsets.UTF_8))
//  Files.write(Paths.get("response_meta.txt"), response.cookies.toString().getBytes(StandardCharsets.UTF_8))
//
//  //  response.body
//  //  response.code
//  //  response.headers
//  //  response.cookies
//
//}