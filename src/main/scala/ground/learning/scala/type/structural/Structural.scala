package ground.learning.scala.traits.selftype

object StructuralTypeMain {

  def main(args: Array[String]) {
    sendStructural("What are you doing", Home);
    sendStylishStructural("What are you doing", Work);
  }

  def sendStructural(msg: String, box: { def receive(msgs: String) }) = box receive msg

  def sendStylishStructural(msg: String, box: MailBoxLike) = box receive msg

  type MailBoxLike = {
    def receive(msg: String)
  }

  object Home { def receive(msg: String) { println(this.getClass().getName() + "@Home +  ${msg}") } }
  object Work { def receive(msg: String) { println(this.getClass().getName() + "@Work +  ${msg}") } }
}

class CloseableClass {
  def close() {
    println(this.getClass().getName() + " : Close")
  }
}

