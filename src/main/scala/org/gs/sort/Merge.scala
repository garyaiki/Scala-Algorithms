/**
 * This is uses an example from Stack Overflow instead of translating from Algorthms
 * @see http://stackoverflow.com/questions/2201472/merge-sort-from-programming-scala-causes-stack-overflow
 * @see http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 * I added implicit Ordering @see [[scala.math.Ordering]]
 */
package org.gs.sort

import math.Ordering
import scala.annotation.tailrec
/**
 * Divide list in 2 then sort each half, recursively
 * @author Gary Struthers
 *
 */
object Merge {

  /**
   * Recursive mergesort
   * @param xs list of generic type T to sort
   * @param ord implicit ordering of type T
   * @return sorted list
   */
  def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {

    /** generic is a less than b */
    def less(a: T, b: T)(implicit ord: Ordering[T]): Boolean = ord.lt(a, b)

    @tailrec /** prepend smaller element to accumulator then return it when one half is empty */
    def merge(xs: List[T], ys: List[T], acc: List[T]): List[T] =
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
