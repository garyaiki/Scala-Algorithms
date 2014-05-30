/**
 * @see http://algs4.cs.princeton.edu/53substring/KMP.java.html
 */
package org.gs.search

import scala.annotation.tailrec
import scala.math.max

/**
 * @author Gary Struthers
 *
 */
class BoyerMoore(pattern: Array[Char], R: Int = 256) {
  val M = pattern.length
  val right = Array.fill[Int](R)(-1)
  for(j <- 0 until pattern.length) right(pattern(j)) = j

  def search(text: Array[Char]): Int = {
    val M = pattern.length
    val N = text.length
    @tailrec
    def loop(i: Int): Int = {
      @tailrec
      def findSkip(j: Int): Int = {
        if(j < 0) 0
        else if(pattern(j) != text(i + j)) max(1, j - right(text(i + j)))
        else findSkip(j - 1)
      }
      if(i <= N - M) {
        val skip = findSkip(M - 1)
        if(skip == 0) i
        else loop(i + skip)
      } else N
    }
    loop(0)
  }
}
