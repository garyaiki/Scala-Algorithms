/** @see https://algs4.cs.princeton.edu/42directed/KosarajuSharirSCC.java.html
  */
package org.gs.digraph

import scala.annotation.tailrec

/** Compute strongly-connected components of a digraph
  *
  * @constructor creates a new KosarajuSharirSCC with a digraph
  * @param g digraph
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class KosarajuSharirSCC(g: Digraph) {
  private val depthFirstOrder = new DepthFirstOrder(g.reverse)
  private val marked = Array.fill[Boolean](g.V)(false)
  private val _id = new Array[Int](g.V)

  @tailrec
  private def searchUnmarked(count: Int, rp: List[Int]): Int = rp match {
      case v :: xs => if (!marked(v)) {
          dfs(v, count)
          searchUnmarked(count + 1, xs)
        } else searchUnmarked(count, xs)
      case Nil => count
    }

  /** returns number of strong components */
  val count = searchUnmarked(0, depthFirstOrder.reversePost)

  private def dfs(v: Int, count: Int): Unit = {
    marked(v) = true
    _id(v) = count
    g.adj(v) foreach (w => if (!marked(w)) dfs(w, count))
  }

  /** returns if  v and  w are strongly connected */
  def stronglyConnected(v: Int, w: Int): Boolean = _id(v) == _id(w)

  /** returns component id of  v vertex */
  def id(v: Int): Int = _id(v)
}
