/**
 *
 */
package org.gs.digraph

import scala.annotation.tailrec

/**
 * @author Gary Struthers
 *
 */
class EdgeWeightedDirectedCycle(g: EdgeWeightedDigraph) extends BaseDirectedCycle[DirectedEdge](g.v) {

  def dfs( v: Int) {
    onStack(v) = true
    marked(v) = true
    def recurOnNewVertex(e: DirectedEdge): Boolean = {
      val w = e.to
      if (!marked(w)) {
        edgeTo(w) = e
        dfs( w)
        true
      } else false
    }
    def traceBack(e: DirectedEdge): Boolean = {
      val w = e.to
      if (onStack(w)) {
        cycle = List[DirectedEdge]()
        @tailrec
        def loop(x: DirectedEdge): Unit = {
          if (x.from != w) {
            cycle = x :: cycle
            loop(edgeTo(e.from))
          }
        }
        loop(e)
        cycle = e :: cycle
        true
      } else false
    }
    for {
      e <- g.adj(v)
    } {
      if (!hasCycle) {
        if (!recurOnNewVertex(e)) traceBack(e)
      }
    }
    onStack(v) = false
  }
  
}