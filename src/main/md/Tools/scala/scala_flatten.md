## How to handle Future\[Try\[Option\[Int\]\]\] 

```scala
val f: Future[Try[Option[Int]]] = ???

case class TryException(ex: Throwable) extends Exception(ex.getMessage)
case object NoneFound extends Exception("None found")

val result: Future[Int] = f.flatMap {
  case Success(Some(value)) => Future.successful(value)
  case Success(None) => Future.failed(NoneFound)
  case Failure(th) => Future.failed(TryException(th))
}

result.map { extractedValue =>
  processTheExtractedValue(extractedValue)
}.recover {
  case NoneFound => "None case"
  case TryException(th) => "try failures"
  case th => "future failures"
}
```
