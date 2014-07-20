/** @see http://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
  */
package org.gs.digraph

import scala.collection.mutable.Queue

/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new EdgeWeightedDepthFirstOrder with an EdgeWeightedDigraph, vertex count
  */
class EdgeWeightedDepthFirstOrder(g: EdgeWeightedDigraph) extends BaseDepthFirstOrder(g.V){

  def dfs(v: Int): Unit = {
    marked(v) = true
    preCounter += 1
    _pre(v) = preCounter
    preOrder.enqueue(v)
    g.adj(v) foreach(e => if (!marked(e.to)) dfs(e.to))
    postOrder.enqueue(v)
    postCounter += 1
    _post(v) = postCounter
  }
}
