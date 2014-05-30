/**
 * @see http://algs4.cs.princeton.edu/23quicksort/QuickX.java.html
 */
package org.gs.sort

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.annotation.tailrec
import scala.reflect.ClassTag

/**
 * @author Gary Struthers
 *
 * @param <A> elements are generic and ordered
 */
class QuickX[A: ClassTag](implicit ord: A => Ordered[A]) {

  private def shuffleArrayBuffer[A: ClassTag](xs: Array[A]): Array[A] = {
    val a = Random.shuffle(xs.toBuffer)
    a.toArray
  }

  def exchange(i: Int, j: Int, xs: Array[A]) {
    val iVal = xs(j)
    val jVal = xs(i)
    xs.update(i, iVal)
    xs.update(j, jVal)
  }

  def insertionSort(xs: Array[A]): Unit = {
    var i = 1
    @tailrec
    def loopI(): Unit = {
      var j = i
      @tailrec
      def loopJ(): Unit = {
        if (xs(j) >= xs(j - 1)) j = 0 else {
          exchange(j, j - 1, xs)
          j -= 1
        }
        if (j > 0) loopJ
      }
      i += 1
      loopJ
      if (i < xs.length) loopI
    }
    loopI
  }

  private def partition(lo: Int, hi: Int, xs: Array[A]): Int = {

    def medianOf3(xs: Array[A]): Int = { //@TODO
      val center = (lo + hi) / 2
      if (xs(lo) > xs(center)) exchange(lo, center, xs)
      if (xs(lo) > xs(hi)) exchange(lo, hi, xs)
      if (xs(center) > xs(hi)) exchange(center, hi, xs)
      exchange(center, hi - 1, xs)
      hi - 1
    }

    def scanLR(i: Int, xs: Array[A]): Int = {
      def stopInc(x: A): Boolean = x >= xs(lo)

      def from(): Int = if (lo < i) i else lo + 1

      xs.indexWhere(stopInc(_), from)
    }

    def scanRL(j: Int, xs: Array[A]): Int = {
      def stopDec(x: A) = (x <= xs(lo))
      xs.lastIndexWhere(stopDec(_), j)
    }

    @tailrec
    def loop(i: Int, j: Int, xs: Array[A]): Int = {
      val newIJ = (scanLR(i, xs), scanRL(j, xs))
      if (newIJ._1 == newIJ._2) newIJ._2
      else if (newIJ._1 > newIJ._2) {
        exchange(lo, newIJ._2, xs)
        newIJ._2
      } else {
        exchange(newIJ._1, newIJ._2, xs)
        loop(newIJ._1, newIJ._2, xs)
      }
    }
    loop(lo + 1, hi, xs)
  }

  private def sort(low: Int, high: Int, xs: Array[A]): Unit = {
    if (low < high) {
      if ((low + 10) > high) insertionSort(xs) else {
        val j = partition(low, high: Int, xs)
        sort(low, j - 1, xs)
        sort(j + 1, high, xs)
      }
    }
  }

  /**
   * @param a
   * @param shuffle
   * @return
   */
  def sort(a: Array[A], shuffle: Boolean = true): Array[A] = {
    val unsorted = if (shuffle) shuffleArrayBuffer(a) else a

    sort(0, unsorted.length - 1, unsorted)
    unsorted
  }
}
