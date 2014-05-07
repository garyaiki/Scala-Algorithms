/**
 * Common code for DirectedCycle, EdgeWeightedDirectedCycle
 */
package org.gs.digraph

import scala.reflect.ClassTag

/**
 * @author Gary Struthers
 *
 * @param <A> Int or DirectedEdge
 * @param v number of vertices in a Digraph or EdgeWeightedDigraph
 */
abstract class BaseDirectedCycle[A: ClassTag](v: Int) {
  val marked = Array.fill[Boolean](v)(false)
  val onStack = Array.fill[Boolean](v)(false)
  val edgeTo = new Array[A](v)
  var cycle = List[A]()
  for {
    v <- 0 until v
    if (!marked(v))
  } dfs(v)

  protected def dfs(v: Int)
  
  def hasCycle() = !cycle.isEmpty
}