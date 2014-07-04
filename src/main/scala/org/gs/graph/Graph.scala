/**
 * @see http://algs4.cs.princeton.edu/41undirected/Graph.java.html
 */
package org.gs.graph

/**
 * Undirected graph extends [[org.gs.graph.BaseGraph]]
 * @author Gary Struthers
 * @param v number of vertices
 */
class Graph(v: Int) extends BaseGraph(v) {

  /** add edge between vertices v and other then add v to other's adjacency list */ 
  override def addEdge(v: Int, other: Int): Unit = {
    super.addEdge(v, other)
    adj(other) = v :: adj(other)
  }
}
