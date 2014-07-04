/**
 * @see http://algs4.cs.princeton.edu/44sp/DirectedCycle.java.html
 */
package org.gs.digraph

import scala.annotation.tailrec

/**
 * @author Gary Struthers
 *
 */
class DirectedCycle(g: Digraph) extends BaseDirectedCycle[Int](g.V) {
  
  protected def dfs(v: Int) {
    onStack(v) = true
    marked(v) = true

    def recurOnNewVertex(w: Int): Boolean = {
      if (!marked(w)) {
        edgeTo(w) = v
        dfs(w)
        true
      } else false
    }

    def traceBack(w: Int): Boolean = {
      if (onStack(w)) {
        _cycle = Some(List[Int]())
        @tailrec
        def loop(x: Int): Unit = {
          if (x != w) {
            _cycle = Some(x :: _cycle.get)
            loop(edgeTo(x))
          }
        }
        loop(v)
        _cycle = Some(v :: w :: _cycle.get)
        true
      } else false
    }

    @tailrec
    def loopW(w: Int, xs: List[Int]): Unit = {
      if (!hasCycle) {
        if (!recurOnNewVertex(w)) traceBack(w)
        xs match {
          case x :: xs => loopW(x, xs)
          case Nil =>
        }
      }
    }

    if (!hasCycle) {
      g.adj(v) match {
        case x :: xs => loopW(x, xs)
        case Nil =>
      }
      onStack(v) = false
    }
  }
}
