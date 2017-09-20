package ground.worksheet.scalacheck
import org.scalacheck.Gen
import Gen.{ choose, alphaNumChar, sized }

object runLengthItem {

  val genOutput : Gen[List[(Int, Char)]] = {

    def rleItem : Gen[(Int, Char)] = for {
      n <- choose(1, 10)
      c <- alphaNumChar
    } yield (n, c)

    def rleList(size : Int) : Gen[List[(Int, Char)]] = {
      if (size <= 1) rleItem.map(List(_))
      else for {
        tail @ (_, c1) :: _ <- rleList(size - 1)
        head <- rleItem retryUntil (_._2 != c1)
      } yield head :: tail
    }

    sized(rleList)
  }                                               //> genOutput  : org.scalacheck.Gen[List[(Int, Char)]] = org.scalacheck.Gen$$ano
                                                  //| n$3@61baa894
  def runlengthDec[A](r : List[(Int, A)]) : List[A] =    r flatMap { case (n, x) => List.fill(n)(x) }
                                                  //> runlengthDec: [A](r: List[(Int, A)])List[A]
  val t = genOutput.sample                        //> t  : Option[List[(Int, Char)]] = Some(List((9,p), (8,1), (2,d), (3,R), (6,k)
                                                  //| , (1,R), (9,r), (4,4), (2,m), (2,I), (9,f), (8,C), (5,d), (7,k), (8,q), (7,d
                                                  //| ), (8,m), (9,z), (4,h), (1,m), (5,x), (7,Q), (1,K), (3,o), (8,v), (4,i), (6,
                                                  //| 3), (6,O), (4,e), (4,k), (3,q), (10,k), (9,f), (10,s), (4,v), (10,g), (1,P),
                                                  //|  (4,h), (5,z), (2,2), (5,d), (10,m), (8,z), (8,f), (4,v), (6,g), (5,m), (1,p
                                                  //| ), (8,q), (3,j), (1,m), (7,f), (2,g), (1,a), (5,o), (2,k), (7,h), (10,t), (8
                                                  //| ,i), (9,j), (8,u), (7,y), (1,7), (10,a), (10,u), (4,9), (2,y), (4,t), (3,3),
                                                  //|  (5,n), (5,y), (9,t), (1,J), (1,c), (2,g), (9,a), (9,T), (6,s), (3,k), (8,r)
                                                  //| , (6,2), (6,n), (2,L), (4,8), (1,p), (7,d), (8,q), (4,v), (1,Y), (6,c), (4,z
                                                  //| ), (8,w), (9,a), (9,9), (4,x), (3,u), (7,y), (8,u), (3,5), (1,i)))
                                                  //
                                                  
 t.map(runlengthDec)                              //> res0: Option[List[Char]] = Some(List(p, p, p, p, p, p, p, p, p, 1, 1, 1, 1, 
                                                  //| 1, 1, 1, 1, d, d, R, R, R, k, k, k, k, k, k, R, r, r, r, r, r, r, r, r, r, 4
                                                  //| , 4, 4, 4, m, m, I, I, f, f, f, f, f, f, f, f, f, C, C, C, C, C, C, C, C, d,
                                                  //|  d, d, d, d, k, k, k, k, k, k, k, q, q, q, q, q, q, q, q, d, d, d, d, d, d, 
                                                  //| d, m, m, m, m, m, m, m, m, z, z, z, z, z, z, z, z, z, h, h, h, h, m, x, x, x
                                                  //| , x, x, Q, Q, Q, Q, Q, Q, Q, K, o, o, o, v, v, v, v, v, v, v, v, i, i, i, i,
                                                  //|  3, 3, 3, 3, 3, 3, O, O, O, O, O, O, e, e, e, e, k, k, k, k, q, q, q, k, k, 
                                                  //| k, k, k, k, k, k, k, k, f, f, f, f, f, f, f, f, f, s, s, s, s, s, s, s, s, s
                                                  //| , s, v, v, v, v, g, g, g, g, g, g, g, g, g, g, P, h, h, h, h, z, z, z, z, z,
                                                  //|  2, 2, d, d, d, d, d, m, m, m, m, m, m, m, m, m, m, z, z, z, z, z, z, z, z, 
                                                  //| f, f, f, f, f, f, f, f, v, v, v, v, g, g, g, g, g, g, m, m, m, m, m, p, q, q
                                                  //| , q, q, q, q, q, q, j, j, j, m, f, f, f, f, f, f, f, g, g, a, o, o, o, o, o,
                                                  //|  k, k, h, h, h, h, h, h,
                                                  //| Output exceeds cutoff limit.
}