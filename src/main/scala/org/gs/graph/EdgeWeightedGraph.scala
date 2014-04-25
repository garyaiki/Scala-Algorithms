/**
 * @see http://algs4.cs.princeton.edu/43mst/EdgeWeightedGraph.java.html
 */
package org.gs.graph
/**
 * @author Gary Struthers
 *
 */
import scala.collection.mutable.ListBuffer

class EdgeWeightedGraph(val v: Int) {
  require(v >= 0, s"Number of vertices, v:$v must be nonnegative")
  var e = 0
  val adj = Array.fill[List[Edge]](v)(List[Edge]())

  def this(g: EdgeWeightedGraph) = {
    this(g.v)
    e = g.e
    var reverse = null.asInstanceOf[ListBuffer[Edge]]

    for {
      v <- 0 until g.v
      reverse = ListBuffer[Edge]()
    } {
      for {
        e <- g.adj(v)
      } reverse.prepend(e)
      for {
        er <- reverse
      } adj(v) = er :: adj(v)
    }
  } 
 
  def rangeGuard(x: Int) = {
    x match {
      case x if 0 until v contains x => true
      case _ => false
    }
  }

  def addEdge(ed: Edge) {
    val either = ed.either
    val other = ed.other(either)
    require(rangeGuard(either) && rangeGuard(other),
      s"verticies either:$either w:$other not in 0..$v ")

    adj(either) = ed :: adj(either)
    adj(other) = ed :: adj(other)
    e = e + 1
  }
  
  def edgesOnVertex(eV: Int) = {
    require(rangeGuard(eV), s"verticies eV:$eV  not in 0..$v ")
    adj(eV).toSeq
  }

  def edges() = {
    var list = ListBuffer[Edge]()
    def addEdgesAndSelfLoops(vV: Int) {
      var selfLoops = 0
      def addEdges(edg: Edge) {
        if(edg.other(vV) > vV) list.prepend(edg) else if (edg.other(vV) == vV) {
          if (selfLoops % 2 == 0) list.prepend(edg)
          selfLoops = selfLoops + 1
        }        
      }
      for {
        ed <- adj(vV)
      } addEdges(ed)
    }
    for {
      vV <- 0 until v
    } addEdgesAndSelfLoops(vV)
    list.toSeq
  }
  
  override def toString(): String = {
    val lf = sys.props("line.separator")
    val sb = new StringBuilder()
    sb.append(s"$v $e $lf")
    def addLines(vV: Int) {
      sb.append(s"$vV : ")
      for {
        ed <- adj(vV)
      } sb.append(s"$ed  ")
      sb.append(lf)
    }
    for {
      vV <- 0 until v
    } addLines(vV)
    sb.toString
  }
}

object EdgeWeightedGraph {

}