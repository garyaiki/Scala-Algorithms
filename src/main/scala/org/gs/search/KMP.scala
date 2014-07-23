/** @see http://algs4.cs.princeton.edu/53substring/KMP.java.html
  */
package org.gs.search

import scala.annotation.tailrec

/** Search text for a pattern using Knuth-Morris-Pratt algorithm
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * @constructor creates a new KMP from a character array constrained to an R character alphabet
  * @param pattern
  * @param radix
  */
class KMP(pattern: Array[Char], R: Int = 256) {
  private val M = pattern.length
  private val dfa = Array.ofDim[Int](R, M)
  dfa(pattern(0))(0) = 1
  private var X = 0

  for (j <- 1 until M; c <- 0 until R) {
    dfa(c)(j) = dfa(c)(X)
    dfa(pattern(j))(j) = j + 1
    X = dfa(pattern(j))(X)
  }

  /** returns text offset of match, text length for no match */
  def search(text: Array[Char]): Int = {
    val M = pattern.length
    val N = text.length

    @tailrec
    def loop(i: Int, j: Int): (Int, Int) =
      if(i < N && j < M) loop(i + 1, dfa(text(i))(j)) else (i, j)

    val tuple = loop(0, 0)
    if(tuple._2 == M) tuple._1 -M else N
  }
}
