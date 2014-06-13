/**
 * @see http://algs4.cs.princeton.edu/41undirected/DepthFirstSearch.java.html
 */
package org.gs.graph

/**
 * @author Gary Struthers
 * @param g
 * @param s
 */
class DepthFirstSearch(g: Graph, s: Int) {
  val marked = new Array[Boolean](g.v)
  private var _count = 0

  def count(): Int = _count
  
  private def dfs(v: Int): Unit = {
    _count += 1
    marked(v) = true
    for {
      w <- g.adj(v)
      if (!marked(w))
    } dfs(w)
  }
  dfs(s)
}
