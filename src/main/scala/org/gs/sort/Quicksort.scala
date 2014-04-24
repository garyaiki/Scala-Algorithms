package org.gs.sort

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.annotation.tailrec

class Quicksort[T](implicit orderer: T => Ordered[T]) {

  private var input: ArrayBuffer[T] = _
  def getInput = input
  def setInput(a: ArrayBuffer[T]) { input = a }
  private def shuffleArrayBuffer[T](xs: ArrayBuffer[T]) { Random.shuffle(xs) }

  def exchange(i: Int, j: Int) {
    val iVal = input(j)
    val jVal = input(i)
    input.update(i, iVal)
    input.update(j, jVal)
  }

  def insertionSort(): Unit = {
    var i = 1
    def loopI(): Unit = {
      var j = i
      def loopJ(): Unit = {
        if (input(j) >= input(j - 1)) j = 0 else {
          exchange(j, j - 1)
          j = j - 1
        }
        if (j > 0) loopJ
      }
      i = i + 1
      loopJ
      if (i < input.length) loopI
    }
    loopI
  }

  private def partition(lo: Int, hi: Int): Int = {

    def medianOf3() = { //@TODO
      val center = (lo + hi) / 2
      if (input(lo) > input(center)) exchange(lo, center)
      if (input(lo) > input(hi)) exchange(lo, hi)
      if (input(center) > input(hi)) exchange(center, hi)
      exchange(center, hi - 1)
      hi - 1
    }

    def scanLR(i: Int): Int = {
      def stopInc(x: T) = x >= input(lo)
      def from = if (lo < i) i else lo + 1
      input.indexWhere(stopInc(_), from)
    }

    def scanRL(j: Int): Int = {
      def stopDec(x: T) = (x <= input(lo))
      input.lastIndexWhere(stopDec(_), j)
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

  /**
   * @TODO
 * @param xs
 * @param lt
 * @return
 */
def quicksort[T](xs: List[T])(lt: (T, T) => Boolean) = {
    @annotation.tailrec
    def qsort(todo: List[List[T]], done: List[T]): List[T] = todo match {
      case Nil => done
      case xs :: rest => xs match {
        case Nil => qsort(rest, done)
        case x :: xrest =>
          val (ls, rs) = (xrest partition (lt(x, _)))
          if (ls.isEmpty) {
            if (rs.isEmpty) qsort(rest, x :: done)
            else qsort(rs :: rest, x :: done)
          } else qsort(ls :: List(x) :: rs :: rest, done)
      }
    }
    qsort(List(xs), Nil)
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
  def sort(a: ArrayBuffer[T], shuffle: Boolean = true): ArrayBuffer[T] = {
    if (shuffle) shuffleArrayBuffer(a)
    input = a
    sort(0, input.length - 1)
    input
  }
}
/*object Quicksort {
  def apply(a: ArrayBuffer[Char], shuffle: Boolean = true) = {
    val qs = new Quicksort()
    qs.input = a
  }
}*/