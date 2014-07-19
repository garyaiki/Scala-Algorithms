/** @see http://algs4.cs.princeton.edu/43mst/EdgeWeightedGraph.java.html
  */
package org.gs.graph

import scala.collection.mutable.ListBuffer
/** Graph where edges have real values as weights.
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new EdgeWeightedGraph with vertex count
  * @param v number of vertices
  */

class EdgeWeightedGraph(v: Int) extends BaseEdgeWeightedGraph[Edge](v) {

  /** @constructor makes a deep copy of  g */
  def this(g: EdgeWeightedGraph) = {
    this(g.V)
    buildADJ(g)
  }

  /** @param ed add [[org.gs.graph.Edge]] to graph */
  def addEdge(ed: Edge): Unit = {
    val either = ed.either
    val other = ed.other(either)
    require(rangeGuard(either) && rangeGuard(other),
      s"verticies either:$either w:$other not in 0..$v ")

    _adj(either) = ed :: _adj(either)
    _adj(other) = ed :: _adj(other)
    e += 1
  }

  /** returns edges in graph */
  def edges(): List[Edge] = {
    val list = ListBuffer[Edge]()
    def addEdgesAndSelfLoops(v: Int) {
      var selfLoops = 0
      
      def addEdges(edg: Edge) {
        if(edg.other(v) > v) list.prepend(edg)
        else if (edg.other(v) == v) {
          if (selfLoops % 2 == 0) list.prepend(edg)
          selfLoops += 1
        }
      }
      
      for( ed <- adj(v)) addEdges(ed)
    }
    
    for( vV <- 0 until v) addEdgesAndSelfLoops(vV)
    list.toList
  }
}
