/**
 * @see http://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
 */
package org.gs.digraph

import scala.collection.mutable.ListBuffer
/**
 * @author Gary Struthers
 *
 */
class EdgeWeightedDigraph(val v: Int) {
  require(v >= 0, s"Number of vertices, v:$v must be nonnegative")
  var e = 0
  val adj = Array.fill[List[DirectedEdge]](v)(List[DirectedEdge]())

  def this(g: EdgeWeightedDigraph) = {
    this(g.v)
    e = g.e
    var reverse = null.asInstanceOf[ListBuffer[DirectedEdge]]

    for {
      v <- 0 until g.v
      reverse = ListBuffer[DirectedEdge]()
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

  def addEdge(ed: DirectedEdge) {
    val v = ed.from
    adj(v) = ed :: adj(v)
    e += 1//e + 1
  }

  def edgesOnVertex(eV: Int) = {
    require(rangeGuard(eV), s"verticies eV:$eV  not in 0..$v ")
    adj(eV).toSeq
  }

  def edges() = {
    val list = ListBuffer[DirectedEdge]()
    for {
      vV <- 0 until v
      e <- adj(vV)
    } list.+=(e)
    list.toSeq
  }

  def outdegree(v: Int) = {
    require(rangeGuard(v), s"verticies v:$v  not in 0..$v ")
    adj(v).size
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
