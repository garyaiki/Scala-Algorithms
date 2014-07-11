/** @see http://algs4.cs.princeton.edu/44sp/EdgeWeightedDirectedCycle.java.html
  */
package org.gs.digraph

import scala.annotation.tailrec

/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new EdgeWeightedDirectedCycle with an EdgeWeightedDigraph
  */
class EdgeWeightedDirectedCycle(g: EdgeWeightedDigraph) 
    extends BaseDirectedCycle[DirectedEdge](g.V) {

  protected def dfs(v: Int): Unit = {
    onStack(v) = true
    marked(v) = true
    
    @tailrec
    def loopEdges(es: List[DirectedEdge]): Unit = {
      es match {
        case e :: xs => {
          search(e)
          loopEdges(xs)
        }
        case _ =>
      }
    }
    
    def search(e: DirectedEdge): Unit = {
      if (!hasCycle) {
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
    val es = g.adj(v)
    loopEdges(es)

    if (!hasCycle) onStack(v) = false
  }
}
