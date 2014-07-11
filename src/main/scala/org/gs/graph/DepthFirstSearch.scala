/** @see http://algs4.cs.princeton.edu/41undirected/DepthFirstSearch.java.html
  */
package org.gs.graph

/** Depth first search of a graph
  * 
  * Mark a vertex as visited then, recursively visit its adjacent vertices that haven't been marked
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new DepthFirstSearch with a graph and source vertex
  * @param g [[org.gs.graph.Graph]]
  * @param s source vertex
  */
class DepthFirstSearch(g: Graph, s: Int) {
  val marked = new Array[Boolean](g.V)
  private var _count = 0

   /** @return number of vertices connected to s */
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
