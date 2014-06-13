/**
 * Common code for Graph, Digraph
 * @see http://algs4.cs.princeton.edu/41undirected/Graph.java.html
 * @see http://algs4.cs.princeton.edu/42directed/Digraph.java.html
 *
 */
package org.gs.graph

/**
 * @author Gary Struthers
 * @param v number of vertices
 */
abstract class BaseGraph(val v: Int) {
  private var e = 0
  protected[gs] val adj = Array.fill[List[Int]](v)(List[Int]())

  def addEdge(vv: Int, w: Int): Unit = {
    def rangeGuard(x: Int): Boolean = {
      x match {
        case x if 0 until v contains x => true
        case _ => false
      }
    }
    require(rangeGuard(vv) && rangeGuard(w), s"aV:$vv and otherV:$w must be in 0-$v")
    e += 1
    adj(vv) = w :: adj(vv)
  }
  
  override def toString(): String = {
    val lf = sys.props("line.separator")
    val sb = new StringBuilder()
    sb.append(s"$v ${e} $lf")
    def addLines(v: Int) {
      sb.append(s"$v : ")
      for {
        e <- adj(v)
      } sb.append(s"$e  ")
      sb.append(lf)
    }
    for {
      vV <- 0 until v
    } addLines(vV)
    sb.toString
  }
}
