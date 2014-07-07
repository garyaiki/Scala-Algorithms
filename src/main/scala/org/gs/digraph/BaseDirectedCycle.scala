
package org.gs.digraph

import scala.reflect.ClassTag

/** Superclass of [[org.gs.digraph.DirectedCycle]], [[org.gs.digraph.EdgeWeightedDirectedCycle]]
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 *
 * @param <A> use Int for DirectedCycle or use DirectedEdge for EdgeWeightedDirectedCycle
 * @param v number of vertices in a Digraph or EdgeWeightedDigraph
 */
abstract class BaseDirectedCycle[A: ClassTag](v: Int) {
  protected val marked = Array.fill[Boolean](v)(false)
  protected val onStack = Array.fill[Boolean](v)(false)
  protected val edgeTo = new Array[A](v)
  protected var _cycle: Option[List[A]] = None
  for {
    v <- 0 until v
    if (!marked(v))
  } dfs(v)

  protected def dfs(v: Int): Unit

  /**  @return directed cycle as a List if a cycle exists */
  def cycle(): Option[List[A]] = _cycle

  /** @return if digraph has a directed cycle */
  def hasCycle(): Boolean = _cycle != None
}
