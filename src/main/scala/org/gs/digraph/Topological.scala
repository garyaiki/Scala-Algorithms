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
  var _order: Seq[Int] = null
  if(!finder.hasCycle) _order = {
    val dfs = g match {
      case d: Digraph => new DepthFirstOrder(d)
      case e: EdgeWeightedDigraph => new EdgeWeightedDepthFirstOrder(e)
    }
    dfs.reversePost
  }
  
  def hasOrder() = _order != None
  
  def order: Option[Seq[Int]] = _order match {
    case null => None
    case x => Some(x)
  }
}
