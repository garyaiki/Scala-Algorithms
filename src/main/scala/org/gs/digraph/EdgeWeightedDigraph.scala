/**
 * @see http://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
 */
package org.gs.digraph

import scala.collection.mutable.ListBuffer
import org.gs.graph.BaseEdgeWeightedGraph
/**
 * @author Gary Struthers
 *
 */
class EdgeWeightedDigraph(v: Int) extends BaseEdgeWeightedGraph[DirectedEdge](v) with DigraphMarker {

  def this(g: EdgeWeightedDigraph) = {
    this(g.v)
    buildADJ(g)
  }

  def addEdge(ed: DirectedEdge): Unit = {
    val v = ed.from
    adj(v) = ed :: adj(v)
    e += 1//e + 1
  }

  def edges(): Seq[DirectedEdge] = {
    val list = ListBuffer[DirectedEdge]()
    for {
      vV <- 0 until v
      e <- adj(vV)
    } list.+=(e)
    list.toSeq
  }

  def outdegree(v: Int): Int = {
    require(rangeGuard(v), s"verticies v:$v  not in 0..$v ")
    adj(v).size
  }
}
