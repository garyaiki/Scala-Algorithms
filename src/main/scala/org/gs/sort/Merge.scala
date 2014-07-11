/** This is uses an example from Stack Overflow instead of translating from Algorthms
  * @see http://stackoverflow.com/questions/2201472/merge-sort-from-programming-scala-causes-stack-overflow
  * @see http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
  * I added implicit Ordering @see [[scala.math.Ordering]]
  */
package org.gs.sort

import math.Ordering
import scala.annotation.tailrec
/** Divide list in 2 then sort each half, recursively
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  */
object Merge {

  /**
   * Recursive mergesort
   * @param xs list of generic type A to sort
   * @param ord implicit ordering of type T
   * @return sorted list
   */
  def msort[A](xs: List[A])(implicit ord: Ordering[A]): List[A] = {

    /** generic is a less than b */
    def less(a: A, b: A)(implicit ord: Ordering[A]): Boolean = ord.lt(a, b)

    @tailrec /** prepend smaller element to accumulator then return it when one half is empty */
    def merge(xs: List[A], ys: List[A], acc: List[A]): List[A] =
      (xs, ys) match {
        case (Nil, _) => ys.reverse ::: acc
        case (_, Nil) => xs.reverse ::: acc
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) merge(xs1, ys, x :: acc)
          else merge(xs, ys1, y :: acc)
      }
    
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(ys), msort(zs), Nil).reverse
    }
  }
}
