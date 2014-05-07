/**
 * @see http://algs4.cs.princeton.edu/44sp/DirectedCycle.java.html
 */
package org.gs.digraph

import scala.annotation.tailrec


/**
 * @author Gary Struthers
 *
 */
class DirectedCycle(g: Digraph) extends BaseDirectedCycle[Int](g.v){

   def dfs(v: Int) {
    onStack(v) = true
    marked(v) = true
    def recurOnNewVertex(w: Int):Boolean = {
      if (!marked(w)) {
        edgeTo(w) = v
        dfs(w)
        true
      } else false
    }
    def traceBack(w: Int):Boolean =  {
      if(onStack(w)) {
    	cycle = List[Int]()
    	@tailrec
        def loop(x: Int): Unit = {
          if (x != w) {
            cycle = x :: cycle
            loop(edgeTo(x))
          }
        }
        loop(v)
        cycle = w :: cycle
        cycle = v :: cycle
        true
      } else false
    }
    for {
      w <- g.adj(v)
      if (!hasCycle)
    } if(!recurOnNewVertex(w)) traceBack(w)
    
    onStack(v) = false
  }


}
object DirectedCycle {

}