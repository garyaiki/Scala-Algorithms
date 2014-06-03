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
  
  


  
  def dfs(v: Int): Unit = {
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
          def traceBack(): Option[List[DirectedEdge]] = {
            _cycle = Some(List[DirectedEdge]())
            @tailrec
            def loop(x: DirectedEdge): Unit = {
              if (x.from != w) {
                _cycle = Some(x :: _cycle.get)
                loop(edgeTo(x.from))
              } else {
                _cycle = Some(x :: _cycle.get)
              }
            }
            loop(e)
            _cycle
          }
          val c = traceBack
        }
      }
    }
    if (!hasCycle) onStack(v) = false
  }
}
