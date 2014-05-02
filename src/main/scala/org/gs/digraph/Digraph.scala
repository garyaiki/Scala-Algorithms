/**
 * @see http://algs4.cs.princeton.edu/42directed/Digraph.java.html
 */
package org.gs.digraph

import org.gs.graph.BaseGraph

/**
 * @author Gary Struthers
 *
 */
class Digraph(v: Int) extends BaseGraph(v) {

  def reverse(): Digraph = {
    val r = new Digraph(v)
    for {
      newV <- 0 until v
      w <- adj(newV)
    } r.addEdge(w, newV)
    r
  }
}
