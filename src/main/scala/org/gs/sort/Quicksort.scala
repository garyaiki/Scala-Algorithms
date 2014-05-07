/**
 * @see
 */
package org.gs.sort
/*
 * @see http://stackoverflow.com/questions/9247504/how-to-implement-tail-recursive-quick-sort-in-scala
 */
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.annotation.tailrec

/**
 *
 * @param <A> elements are generic and ordered
 */
class Quicksort[A] {

  /**
   *
   * @param xs
   * @param lt
   * @return
   */
  def quicksort[A](xs: List[A])(implicit ord: A => Ordered[A]) = {
    def less(a: A, b: A)(implicit ord: Ordering[A]) = ord.lt(a, b)
    @annotation.tailrec
    def qsort(todo: List[List[A]], done: List[A]): List[A] = todo match {
      case Nil => done
      case xs :: rest => xs match {
        case Nil => qsort(rest, done)
        case x :: xrest =>
          val (ls, rs) = (xrest partition (less(x, _)))
          if (ls.isEmpty) {
            if (rs.isEmpty) qsort(rest, x :: x :: done)
            else qsort(rs :: rs :: rest, x :: x :: done)
          } else qsort(ls :: ls :: List(x) :: List(x) :: rs :: rs :: rest, done)
      }
    }
    qsort(List(xs), Nil)
  }

}
