/**
 * @see http://algs4.cs.princeton.edu/43mst/EdgeWeightedGraph.java.html
 */
package org.gs.graph

import scala.collection.mutable.ListBuffer
/**
 * @author Gary Struthers
 * @param v number of vertices
 */
class EdgeWeightedGraph(v: Int) extends BaseEdgeWeightedGraph[Edge](v) {

  def this(g: EdgeWeightedGraph) = {
    this(g.v)
    buildADJ(g)
  }

  def addEdge(ed: Edge): Unit = {
    val either = ed.either
    val other = ed.other(either)
    require(rangeGuard(either) && rangeGuard(other),
      s"verticies either:$either w:$other not in 0..$v ")

    adj(either) = ed :: adj(either)
    adj(other) = ed :: adj(other)
    e += 1
  }

  def edges(): List[Edge] = {
    val list = ListBuffer[Edge]()
    def addEdgesAndSelfLoops(vV: Int) {
      var selfLoops = 0
      def addEdges(edg: Edge) {
        if(edg.other(vV) > vV) list.prepend(edg) else if (edg.other(vV) == vV) {
          if (selfLoops % 2 == 0) list.prepend(edg)
          selfLoops += 1
        }
      }
      for {
        ed <- adj(vV)
      } addEdges(ed)
    }
    for {
      vV <- 0 until v
    } addEdgesAndSelfLoops(vV)
    list.toList
  }
}
