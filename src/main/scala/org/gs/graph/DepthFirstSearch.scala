package org.gs.graph

/** Depth first search of a graph
  *
  * Mark a vertex as visited then, recursively visit its adjacent vertices that haven't been marked
  *
  * @constructor creates a new DepthFirstSearch with a graph and source vertex
  * @param g Graph
  * @param s source vertex
  * @see [[https://algs4.cs.princeton.edu/41undirected/DepthFirstSearch.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class DepthFirstSearch(g: Graph, s: Int) {
  val marked = new Array[Boolean](g.numV)
  private var _count = 0

   /** returns number of vertices connected to s */
  def count(): Int = _count

  private def dfs(v: Int): Unit = {
    _count += 1
    marked(v) = true
    g.adj(v) foreach (w => if (!marked(w)) dfs(w))
  }
  dfs(s)
}
