package org.gs.graph

/** Undirected graph
  *
  * @constructor creates a new Graph with vertex count
  * @param v number of vertices
  * @see [[https://algs4.cs.princeton.edu/41undirected/Graph.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class Graph(v: Int) extends BaseGraph(v) {

  /** add edge between vertices v and other then add v to other's adjacency list */
  override def addEdge(v: Int, other: Int): Unit = {
    super.addEdge(v, other)
    adj(other) = v :: adj(other)
  }
}
