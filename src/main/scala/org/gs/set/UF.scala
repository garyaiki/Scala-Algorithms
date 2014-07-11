/** @see http://algs4.cs.princeton.edu/15uf/UF.java.html
  */
package org.gs.set

import scala.annotation.tailrec

/** Partition points into connected components
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * @param n number of sites
  * count is the number of components
  * id(i) is the parent of i
  * rank is the rank of the subtree rooted at i
  */
class UF(n: Int) {
  require(n >= 0)
  private var _count = n
  private val id = Array.range(0, n) 
  private val rank = Array.fill[Byte](n)(0)

  /** @return number of components */
  def count(): Int = _count

  /** @return component identifier for p  */
  def find(p: Int): Int = {
    require(p >= 0 && p < id.length)
    
    @tailrec
    def loop(p: Int): Int =
      if (p != id(p)) {
        id(p) = id(id(p))
        loop(id(p))
      } else p

    loop(p)
  }

  /** @return if p and q in the same component  */
  def connected(p: Int, q: Int): Boolean = find(p) == find(q)

  /**
   * Add connection between p and q if they aren't already connected
   * @param p
   * @param q
   */
  def union(p: Int, q: Int): Unit = {
    val i = find(p)
    val j = find(q)
    if (!(i == j)) {
      if (rank(i) < rank(j)) id(i) = j else if (rank(i) > rank(j)) id(j) = i else {
        id(j) = i
        rank(i) = (rank(i) + 1).toByte
      }
      _count -= 1
    }
  }
}
