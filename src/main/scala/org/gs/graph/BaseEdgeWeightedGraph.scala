/**
 * Common code for EdgeWeightedGraph, EdgeWeightedDigraph
 */
package org.gs.graph

import scala.collection.mutable.ListBuffer

/**
 * @author Gary Struthers
 *
 * @param <T> Edge, DirectedEdge
 * @param v number of vertices in EdgeWeightedGraph, EdgeWeightedDigraph
 */
abstract class BaseEdgeWeightedGraph[T <: BaseEdge](val v: Int) {
  require(v >= 0, s"Number of vertices, v:$v must be nonnegative")
  var e = 0
  val adj = Array.fill[List[T]](v)(List[T]())

  def buildADJ[U <: BaseEdgeWeightedGraph[T]](g: U) {
    e = g.e

    for {
      v <- 0 until g.v
      reverse = ListBuffer[T]()
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

  def edgesOnVertex(eV: Int) = {
    require(rangeGuard(eV), s"verticies eV:$eV  not in 0..$v ")
    adj(eV).toSeq
  }

  def edges():Seq[T]

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