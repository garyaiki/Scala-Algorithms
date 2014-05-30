/**
 * @see http://algs4.cs.princeton.edu/42directed/DirectedDFS.java.html
 */
package org.gs.digraph

/**
 * @author Gary Struthers
 *
 */
class DirectedDFS(g: Digraph, sources: Int*) {
  val marked = new Array[Boolean](g.v)
  var count = 0

  private def dfs(v: Int): Unit = {
    count += 1
    marked(v) = true
    for {
      w <- g.adj(v)
      if (!marked(w))
    } dfs(w)
  }
  for{
    v <- sources
    if(!marked(v))
  } dfs(v)

  def isMarked(v: Int): Boolean = marked(v)
}
