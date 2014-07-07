/**
 * @see http://algs4.cs.princeton.edu/42directed/Digraph.java.html
 */
package org.gs.digraph

import org.gs.graph.BaseGraph

/** EdgeWeightedDigraph, Topological use this to tell a Graph from a Digraph */
trait DigraphMarker
/**
 * Directed Graph 
 * 
 * Extends [[org.gs.graph.BaseGraph]] and adds DigraphMarker to distinguish it from Graph
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 *
 */
class Digraph(v: Int) extends BaseGraph(v) with DigraphMarker {

  /** @return a reverse order copy */
  def reverse(): Digraph = {
    val r = new Digraph(v)
    for {
      newV <- 0 until v
      w <- adj(newV)
    } r.addEdge(w, newV)
    r
  }
}
