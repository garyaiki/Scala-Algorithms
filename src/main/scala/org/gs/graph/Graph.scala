/** @see https://algs4.cs.princeton.edu/41undirected/Graph.java.html
  */
package org.gs.graph

/** Undirected graph
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new Graph with vertex count
  * @param v number of vertices
  */
class Graph(v: Int) extends BaseGraph(v) {

  /** add edge between vertices v and other then add v to other's adjacency list */
  override def addEdge(v: Int, other: Int): Unit = {
    super.addEdge(v, other)
    adj(other) = v :: adj(other)
  }
}
