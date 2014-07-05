/**
 * @see http://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
 */
package org.gs.digraph

import scala.collection.mutable.Queue

/**
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 *
 */
class EdgeWeightedDepthFirstOrder(g: EdgeWeightedDigraph) extends BaseDepthFirstOrder(g.v){

  def dfs(v: Int): Unit = {
    marked(v) = true
    preCounter += 1
    _pre(v) = preCounter
    preOrder.enqueue(v)
    for {
      e <- g.adj(v)
      if (!marked(e.to))
    } dfs(e.to)
    postOrder.enqueue(v)
    postCounter += 1
    _post(v) = postCounter
  }
}
