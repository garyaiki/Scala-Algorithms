/**
 * @see http://algs4.cs.princeton.edu/44sp/EdgeWeightedDirectedCycle.java.html
 */
package org.gs.digraph

import scala.annotation.tailrec
import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable

/**
 * @author Gary Struthers
 *
 */
class EdgeWeightedDirectedCycle(g: EdgeWeightedDigraph) extends BaseDirectedCycle[DirectedEdge](g.v) {

  def dfs(v: Int) {
    onStack(v) = true
    marked(v) = true
    breakable {
      for {
        e <- g.adj(v)
      } {
        if (hasCycle) break
        val w = e.to
        val newV = !marked(w)
        if (newV) {
          edgeTo(w) = e
          dfs(w)
        } else if (onStack(w)) {
          def traceBack(): List[DirectedEdge] = {
            cycle = List[DirectedEdge]()
            @tailrec
            def loop(x: DirectedEdge): Unit = {
              if (x.from != w) {
                cycle = x :: cycle
                loop(edgeTo(x.from))
              } else {
                cycle = x :: cycle
              }
            }
            loop(e)
            cycle
          }
          val c = traceBack
        }
      }
    }
    if (!hasCycle) onStack(v) = false
  }
}