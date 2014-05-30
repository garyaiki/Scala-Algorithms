/**
 * Common code for Graph, Digraph
 * @see http://algs4.cs.princeton.edu/41undirected/Graph.java.html
 * @see http://algs4.cs.princeton.edu/42directed/Digraph.java.html
 *
 */
package org.gs.graph

/**
 * @author Gary Struthers
 * @param v number of vertices
 */
abstract class BaseGraph(val v: Int) {
  var e = 0
  val adj = Array.fill[List[Int]](v)(List[Int]())

  def addEdge(aV: Int, otherV: Int): Unit = {
    def rangeGuard(x: Int): Boolean = {
      x match {
        case x if 0 until v contains x => true
        case _ => false
      }
    }
    require(rangeGuard(aV) && rangeGuard(otherV), s"aV:$aV and otherV:$otherV must be in 0-$v")
    e += 1
    adj(aV) = otherV :: adj(aV)
  }
}
