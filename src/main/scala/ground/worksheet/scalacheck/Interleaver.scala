package ground.worksheet.scalacheck

object Interleaver {
  def interleave[T](l1 : List[T], l2 : List[T]) : List[T] = {
    if (l1.isEmpty) l2
    else if (l2.isEmpty) l1
    else l1.head :: l2.head :: interleave(l2.tail, l1.tail)
  }
}