/*
  * @see http://algs4.cs.princeton.edu/42directed/Topological.java.html
  */
package org.gs.digraph

/** Find topological order of a digraph
  *  
  * The reverse post-order of a directed acyclic graph (DAG) is its topological order 
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * 
  * @constructor creates a new Topological with either a Digraph or EdgeWeightedDigraph
  * @tparam A constrains g to classes that mixin [[org.gs.digraph.DigraphMarker]]
  * @param g either a [[org.gs.digraph.Digraph]] or [[org.gs.digraph.EdgeWeightedDigraph]]
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

  /** returns if it has a topological order */
  def hasOrder(): Boolean = _order != None

  /** returns list of vertex numbers in topological order */
  def order(): Option[List[Int]] = _order 
}
