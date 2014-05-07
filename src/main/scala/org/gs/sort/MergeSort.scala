/**
 * This is uses an example from Stack Overflow instead of translating from Algorthms
 * @see http://stackoverflow.com/questions/2201472/merge-sort-from-programming-scala-causes-stack-overflow
 * @see http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 *
 */
package org.gs.sort

import math.Ordering
import scala.annotation.tailrec

object MergeSort {

  def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    
    def less(a: T, b: T)(implicit ord: Ordering[T]) = ord.lt(a, b)

    @tailrec
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