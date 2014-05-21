/**
 * @see http://algs4.cs.princeton.edu/23quicksort/QuickX.java.html
 */
package org.gs.sort

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.annotation.tailrec

/**
 * @author Gary Struthers
 *
 * @param <A> elements are generic and ordered
 */
class QuickX[A](implicit ord: A => Ordered[A]) {

  private var _input: ArrayBuffer[A] = _
  
  def input() = _input
  
  def setInput(a: ArrayBuffer[A]) { _input = a }
  
  private def shuffleArrayBuffer[A](xs: ArrayBuffer[A]) { Random.shuffle(xs) }

  def exchange(i: Int, j: Int) {
    val iVal = _input(j)
    val jVal = _input(i)
    _input.update(i, iVal)
    _input.update(j, jVal)
  }

  def insertionSort(): Unit = {
    var i = 1
    @tailrec
    def loopI(): Unit = {
      var j = i
      @tailrec
      def loopJ(): Unit = {
        if (_input(j) >= _input(j - 1)) j = 0 else {
          exchange(j, j - 1)
          j -= 1
        }
        if (j > 0) loopJ
      }
      i += 1
      loopJ
      if (i < _input.length) loopI
    }
    loopI
  }

  private def partition(lo: Int, hi: Int): Int = {

    def medianOf3() = { //@TODO
      val center = (lo + hi) / 2
      if (_input(lo) > _input(center)) exchange(lo, center)
      if (_input(lo) > _input(hi)) exchange(lo, hi)
      if (_input(center) > _input(hi)) exchange(center, hi)
      exchange(center, hi - 1)
      hi - 1
    }

    def scanLR(i: Int): Int = {
      def stopInc(x: A) = x >= _input(lo)
      def from = if (lo < i) i else lo + 1
      _input.indexWhere(stopInc(_), from)
    }

    def scanRL(j: Int): Int = {
      def stopDec(x: A) = (x <= _input(lo))
      _input.lastIndexWhere(stopDec(_), j)
    }

    @tailrec
    def loop(i: Int, j: Int): Int = {
      val newIJ = (scanLR(i), scanRL(j))
      if (newIJ._1 == newIJ._2) newIJ._2
      else if (newIJ._1 > newIJ._2) {
        exchange(lo, newIJ._2)
        newIJ._2
      } else {
        exchange(newIJ._1, newIJ._2)
        loop(newIJ._1, newIJ._2)
      }
    }
    loop(lo + 1, hi)
  }

  private def sort(low: Int, high: Int) {
    if (low < high) {
      if ((low + 10) > high) insertionSort else {
        val j = partition(low, high: Int)
        sort(low, j - 1)
        sort(j + 1, high)
      }
    }
  }

  /**
   * @param a
   * @param shuffle
   * @return
   */
  def sort(a: ArrayBuffer[A], shuffle: Boolean = true): ArrayBuffer[A] = {
    if (shuffle) shuffleArrayBuffer(a)
    _input = a
    sort(0, _input.length - 1)
    _input
  }
}
