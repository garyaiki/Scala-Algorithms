/**
 * @see http://algs4.cs.princeton.edu/42directed/DirectedDFS.java.html
 */
package org.gs.digraph

/**
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 *
 */
class DirectedDFS(g: Digraph, sources: Int*) {
  val marked = new Array[Boolean](g.V)
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
  for{
    v <- sources
    if(!marked(v))
  } dfs(v)

  def isMarked(v: Int): Boolean = marked(v)
}
