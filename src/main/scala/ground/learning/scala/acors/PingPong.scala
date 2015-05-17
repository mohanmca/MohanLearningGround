package ground.learning.scala.acors

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/**
 * @author Mohan
 */
object PingPong  {
  implicit val system = ActorSystem("SampleActor")
  val pingActor = system.actorOf(Props[mySampleActor], "mySampleActor")
  val pongActor = system.actorOf(Props[mySampleActor], "mySampleActorPong")

  def main(args: Array[String]) {
    pingActor.tell(new Ping(), pongActor)
  }

}

class mySampleActor extends Actor {
  var count = 0;
  def receive = {
    case Pong() => {
      Console.err.println("Pong")
      sender ! Ping()
    }
    case Ping() => {
      Console.err.println("Ping")
      sender ! Pong()
    }
  }
}

abstract class Message
case class Pong() extends Message
case class Ping() extends Message