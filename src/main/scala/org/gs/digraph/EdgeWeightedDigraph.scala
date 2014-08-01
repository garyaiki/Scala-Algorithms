/** @see http://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
  */
package org.gs.digraph

import scala.collection.mutable.ListBuffer
import org.gs.graph.BaseEdgeWeightedGraph

/** Digraph with edges having direction and weight
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new EdgeWeightedDigraph with a vertex count
  */
class EdgeWeightedDigraph(v: Int) extends BaseEdgeWeightedGraph[DirectedEdge](v) with DigraphMarker {

  /** @constructor creates a deep copy of an EdgeWeightedDigraph */
  def this(g: EdgeWeightedDigraph) = {
    this(g.V)
    buildADJ(g)
  }

  /** add directed edge to digraph */
  def addEdge(ed: DirectedEdge): Unit = {
    val v = ed.from
    _adj(v) = ed :: _adj(v)
    e += 1//e + 1
  }

  /** returns all edges in digraph */
  def edges(): List[DirectedEdge] = {
    val list = for {
      vV <- 0 until v
      e <- adj(vV)
    } yield e
    list.toList
  }

  /** returns number of directed edges from vertex */
  def outdegree(v: Int): Int = {
    require(rangeGuard(v), s"verticies v:$v  not in 0..$v ")
    adj(v).size
  }
}
