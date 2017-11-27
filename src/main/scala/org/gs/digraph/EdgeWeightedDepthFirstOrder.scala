/** @see https://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
  */
package org.gs.digraph

import scala.collection.mutable.Queue

/** Depth first order for an EdgeWeightedDigraph
  *
  * @constructor creates a new EdgeWeightedDepthFirstOrder with an EdgeWeightedDigraph, vertex count
  * @param g acyclic digraph, edges have direction and weight
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class EdgeWeightedDepthFirstOrder(g: EdgeWeightedDigraph) extends BaseDepthFirstOrder(g.V){

  def dfs(v: Int): Unit = {
    marked(v) = true
    preCounter += 1
    _pre(v) = preCounter
    preOrder.enqueue(v)
    g.adj(v) foreach (e => if (!marked(e.to)) dfs(e.to))
    postOrder.enqueue(v)
    postCounter += 1
    _post(v) = postCounter
  }
}
