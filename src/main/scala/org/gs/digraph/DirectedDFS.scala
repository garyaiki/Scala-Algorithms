/**
 * @see http://algs4.cs.princeton.edu/42directed/DirectedDFS.java.html
 */
package org.gs.digraph

/**
 * Find reachable vertices from single or multiple source vertices
 * 
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param g [[org.gs.digraph.Digraph]]
 * @param sources a variable number of vertices
 * One source vertex
 * {{{
 * val from1 = new DirectedDFS(tinyDG, 1)
 * }}}
 * 
 * Two source vertices
 * {{{
 * val from1And2 = new DirectedDFS(tinyDG, 1, 2)
 * }}}
 * 
 * Two source vertices in a collection
 * {{{
 * val from1And2 = new DirectedDFS(tinyDG, Array[Int](1, 2): _*)
 * }}}
 */
class DirectedDFS(g: Digraph, sources: Int*) {
  val marked = new Array[Boolean](g.V)
  private var _count = 0

  /** @return number of vertices reachable from s */
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

  /** @return if there is a path from any source to v */
  def isMarked(v: Int): Boolean = marked(v)
}
