package ground.learning.Combinatorial

object PrimeFactors extends App {
  val n: Int = 8;

  def isPrime(n: Int): Boolean = {
    Range(2, n).filter(k => n % k == 0).seq.size == 0
  }

  val pairs = for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i + j)
  } yield (i, j)

  print(pairs)

}