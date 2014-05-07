/**
 * @see http://algs4.cs.princeton.edu/41undirected/Graph.java.html
 */
package org.gs.graph

/**
 * @author Gary Struthers
 * @param v number of vertices
 */
class Graph(v: Int) extends BaseGraph(v) {

  override def addEdge(aV: Int, otherV: Int): Unit = {
    super.addEdge(aV, otherV)
    adj(otherV) = aV :: adj(otherV)
  }
}
