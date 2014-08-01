/** @see http://algs4.cs.princeton.edu/15uf/UF.java.html
  */
package org.gs.set

import scala.annotation.tailrec

/** Partition points into connected components
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * @param n number of sites
  */
class UF(n: Int) {
  require(n >= 0)
  private var _count = n
  private val id = Array.range(0, n)
  private val rank = Array.fill[Byte](n)(0)

  /** returns number of components */
  def count(): Int = _count

  /** returns component identifier for p  */
  def find(p: Int): Int = {
    require(p >= 0 && p < id.length)

    @tailrec
    def loop(p: Int): Int = if (p != id(p)) {
      id(p) = id(id(p))
      loop(id(p))
    } else p

    loop(p)
  }

  /** returns if p and q in the same component  */
  def connected(p: Int, q: Int): Boolean = find(p) == find(q)

  /** Add connection between p and q if they aren't already connected
    * @param p
    * @param q
    */
  def union(p: Int, q: Int): Unit = {
    val i = find(p)
    val j = find(q)
    if (!(i == j)) {
      if (rank(i) < rank(j)) id(i) = j
      else if (rank(i) > rank(j)) id(j) = i
      else {
        id(j) = i
        rank(i) = (rank(i) + 1).toByte
      }
      _count -= 1
    }
  }
}
