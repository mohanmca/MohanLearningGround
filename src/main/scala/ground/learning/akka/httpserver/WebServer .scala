//package ground.learning.akka.httpserver
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model._
//import akka.http.scaladsl.server.Directives._
//import akka.stream.ActorMaterializer
//import scala.io.StdIn
//
//object WebServer {
//  def main(args: Array[String]) {
//
//    implicit val system = ActorSystem("my-system")
//    implicit val materializer = ActorMaterializer()
//    // needed for the future flatMap/onComplete in the end
//    implicit val executionContext = system.dispatcher
//
//    /*
//     * Withoug materializer compiler would throw below issue.
//     * found   : akka.http.scaladsl.server.Route     (which expands to)  akka.http.scaladsl.server.RequestContext => scala.concurrent.Future[akka.http.scaladsl.server.RouteResult]
//     * required: akka.stream.scaladsl.Flow[akka.http.scaladsl.model.HttpRequest,akka.http.scaladsl.model.HttpResponse,Any]
//     */
//
//
//    val route =
//      path("hello") {
//        get {
//          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
//        }
//      }
//
//    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
//
//    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
//    StdIn.readLine() // let it run until user presses return
//    bindingFuture
//      .flatMap(_.unbind()) // trigger unbinding from the port
//      .onComplete(_ => system.terminate()) // and shutdown when done
//  }
//}