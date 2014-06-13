/*
 * @see http://algs4.cs.princeton.edu/42directed/Topological.java.html
 */
package org.gs.digraph

/**
 * @author Gary Struthers
 *
 * @param <A> constrains g to extending DigraphMarker
 * @param g either a Digraph or EdgeWeightedDigraph
 */
class Topological[A <: DigraphMarker](g: A) {
  val finder = g match {
    case d: Digraph => new DirectedCycle(d)
    case e: EdgeWeightedDigraph => new EdgeWeightedDirectedCycle(e)
  }
  
  private def createOrder(noCycle: Boolean): Option[List[Int]] = {
    if (noCycle) {
      val dfs = g match {
        case d: Digraph => new DepthFirstOrder(d)
        case e: EdgeWeightedDigraph => new EdgeWeightedDepthFirstOrder(e)
      }
      Some(dfs.reversePost)
    } else None
  }

  private lazy val _order = createOrder(!finder.hasCycle)

  def hasOrder(): Boolean = _order != None

  def order(): Option[List[Int]] = _order 
}
