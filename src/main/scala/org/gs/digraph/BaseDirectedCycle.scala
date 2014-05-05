/**
 * Common code for DirectedCycle, EdgeWeightedDirectedCycle
 */
package org.gs.digraph

import scala.reflect.ClassTag

/**
 * @author Gary Struthers
 *
 * @param <T> Int or DirectedEdge
 * @param v number of vertices in a Digraph or EdgeWeightedDigraph
 */
abstract class BaseDirectedCycle[T: ClassTag](v: Int) {
  val marked = Array.fill[Boolean](v)(false)
  val onStack = Array.fill[Boolean](v)(false)
  val edgeTo = new Array[T](v)
  var cycle = List[T]()
  for {
    v <- 0 until v
    if (!marked(v))
  } dfs(v)

  protected def dfs(v: Int)
  
  def hasCycle() = !cycle.isEmpty
}