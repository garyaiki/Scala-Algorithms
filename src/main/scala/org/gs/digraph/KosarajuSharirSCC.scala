/**
 * @see http://algs4.cs.princeton.edu/42directed/KosarajuSharirSCC.java.html
 */
package org.gs.digraph

/**
 * @author Gary Struthers
 * @param g digraph
 */
class KosarajuSharirSCC(g: Digraph) {
  val depthFirstOrder = new DepthFirstOrder(g.reverse)
  val marked = Array.fill[Boolean](g.V)(false)
  val id = new Array[Int](g.V)

  def searchUnmarked(count: Int, rp: List[Int]): Int = {
    rp match {
      case v :: xs => {
        if (!marked(v)) {
          dfs(v, count)
          searchUnmarked(count + 1, xs)
        } else {
          searchUnmarked(count, xs)
        }
      }
      case Nil => count
    }
  }
  val count = searchUnmarked(0, depthFirstOrder.reversePost)

  def dfs(v: Int, count: Int): Unit = {
    marked(v) = true
    id(v) = count
    for {
      w <- g.adj(v)
      if (!marked(w))
    } dfs(w, count)
  }

  def stronglyConnected(v: Int, w: Int): Boolean = id(v) == id(w)

  def idStrongComponent(v: Int): Int = id(v)
}

