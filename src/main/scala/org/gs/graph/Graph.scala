/**
 * @see http://algs4.cs.princeton.edu/41undirected/Graph.java.html
 */
package org.gs.graph

/**
 * @author Gary Struthers
 *
 */
class Graph(val v: Int) {
  var e = 0
  val adj = Array.fill[List[Int]](v)(List[Int]())

  def addEdge(aV: Int, otherV: Int) {
    def rangeGuard(x: Int) = {
      x match {
        case x if 0 until v contains x => true
        case _ => false
      }
    }
    require(rangeGuard(aV) && rangeGuard(otherV), s"aV:$aV and otherV:$otherV must be in 0-$v" )
    e = e + 1
    adj(aV) = otherV :: adj(aV)
    adj(otherV) = aV :: adj(otherV)
  }
}
