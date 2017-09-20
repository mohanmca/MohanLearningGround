package ground.worksheet.scalacheck

object TreeGen extends App {
  trait Tree[T] {
    def size : Int
  }

  case class Leaf[T](
      item : T) extends Tree[T] {
    def size = 1
  }

  case class Node[T](
      children : List[Tree[T]]) extends Tree[T] {
    def size = children.map(_.size).sum
  }

  import org.scalacheck.Gen
  import org.scalacheck.Gen.{
    sized,
    choose,
    resize,
    listOfN,
    oneOf,
    lzy
  }

  def genTree[T](genT : Gen[T]) : Gen[Tree[T]] = lzy {
    oneOf(genLeaf(genT), genNode(genT))
  }                                               //> genTree: [T](genT: org.scalacheck.Gen[T])org.scalacheck.Gen[ground.worksheet
                                                  //| .scalacheck.TreeGen.Tree[T]]
 
  def genLeaf[T](genT : Gen[T]) : Gen[Leaf[T]] =
    genT.map(Leaf(_))                             //> genLeaf: [T](genT: org.scalacheck.Gen[T])org.scalacheck.Gen[ground.worksheet
                                                  //| .scalacheck.TreeGen.Leaf[T]]

  def genNode[T](genT : Gen[T]) : Gen[Node[T]] =
    sized { size =>
      for {
        s <- choose(0, size)
        g = resize(size / (s + 1), genTree(genT))
        children <- listOfN(s, g)
      } yield Node(children)
    }                                             //> genNode: [T](genT: org.scalacheck.Gen[T])org.scalacheck.Gen[ground.worksheet
                                                  //| .scalacheck.TreeGen.Node[T]]
  import org.scalacheck.Arbitrary.arbitrary
  val genIntTree = genTree(arbitrary[Int])       
                                                  //| 	at org.scalacheck.Gen
 println(genIntTree.sample)                                                 //| Output exceeds cutoff limit.
}