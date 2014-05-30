/**
 * This copies a example from Stack Overflow instead of translating from Algorthms
 * @see http://stackoverflow.com/questions/2201472/merge-sort-from-programming-scala-causes-stack-overflow
 * @see http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 */
package org.gs.sort

import scala.annotation.tailrec

object MergeSortList {

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
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
      merge(msort(less)(ys), msort(less)(zs), Nil).reverse
    }
  }
}
