/** @see https://algs4.cs.princeton.edu/23quicksort/QuickX.java.html
  */
package org.gs.sort

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.util.Random

/** Quicksort
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new QuickX
  * @tparam A elements are generic and ordered ClassTag preserves Array type at runtime
  * @param ord implicitly provides ordering
  */
class QuickX[A: ClassTag](implicit ord: A => Ordered[A]) {

  private def shuffleArrayBuffer[A: ClassTag](xs: Array[A]): Array[A] = {
    val a = Random.shuffle(xs.toBuffer)
    a.toArray
  }

  /** exchange i and j in array */
  def exchange(i: Int, j: Int, xs: Array[A]) {
    val iVal = xs(j)
    val jVal = xs(i)
    xs.update(i, iVal)
    xs.update(j, jVal)
  }

  /** Insertion sort is faster when partition or array has fewer than 10 elements
    * Exchange 2 elements when the one on the left is greater than the one on the right
    *
    * @param generic array to sort
    */
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
        if (j > 0) loopJ()
      }

      i += 1
      loopJ()
      if (i < xs.length) loopI()
    }

    loopI()
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

    /** Scan from left of array until finding element greater than partition
      *
      * stop incrementing when x >= lo
      *
      * @param i partition
      * @param xs array
      * @return new i index
      */
    def scanLR(i: Int, xs: Array[A]): Int = {

      def stopInc(x: A): Boolean = x >= xs(lo)

      def from(): Int = if (lo < i) i else lo + 1

      xs indexWhere (stopInc(_), from)
    }

    /** Scan from right of array until finding element greater than partition
      *
      * stop decrementing when x <= lo
      *
      * @param i partition
      * @param xs array
      * @return new j index
      */
    def scanRL(j: Int, xs: Array[A]): Int = {

      def stopDec(x: A) = (x <= xs(lo))

      xs lastIndexWhere (stopDec(_), j)
    }

    @tailrec /** scan both partition, put i, j in order, loop */
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

  private def sort(low: Int, high: Int, xs: Array[A]): Unit = if (low < high) {
    if ((low + 10) > high) insertionSort(xs) else {
      val j = partition(low, high: Int, xs)
      sort(low, j - 1, xs)
      sort(j + 1, high, xs)
    }
  }

  /** Quicksort recursively partition and sort partions
    *
    * @param a generic array
    * @param shuffle optionally shuffle for performance
    * @return sorted array
    */
  def sort(a: Array[A], shuffle: Boolean = true): Array[A] = {
    val myArray = if (shuffle) shuffleArrayBuffer(a) else a

    sort(0, myArray.length - 1, myArray)
    myArray
  }
}
