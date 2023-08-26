package Tools.scala.scalaz

object ReaderApp extends App {
  //import scalaz.Reader

  case class Reader[A](f: Config => A) {

    def map[B](g: A => B): Reader[B] = {
      val reader: Config => B = { config =>
        val x: A = f(config)
        g(x)
      }
      Reader(reader)
    }

    def flatMap[B](g: A => Reader[B]): Reader[B] = {
      val sequence: Config => B = { config =>
        g(f(config)).f(config)
      }
      Reader(sequence)
    }

    def run(config: Config): A = f(config)

  }

  case class Config(batchSize: Int)
  
  type IntToConfig = (Int => Config)

  implicit def ReaderFun[A](f: Config => A) = Reader(f)

  //(A, B) = C ~~~ A => (B => C) 
  def setBatchSize(conf: Config, size: Int): Config = conf.copy(batchSize = size)
  def setBatch: Config => IntToConfig = (setBatchSize _).curried
  def getBatchSize(conf: Config): Int = conf.batchSize
  

  def scaleBatchSize(scale: Double) =
    for {
      f <- Reader((setBatchSize _).curried)
      s <- Reader(getBatchSize)
    } yield f((scale * s).toInt)

  val newConf = scaleBatchSize(2.0).run(Config(3)) // scalaz.Id.Id[Config]

  // Recall `type Id[A] = A` so no need to unwrap anything.
  println(newConf.batchSize) // Int = 6
}