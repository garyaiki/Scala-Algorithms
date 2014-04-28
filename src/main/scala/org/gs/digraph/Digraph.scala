/**
 * @see http://algs4.cs.princeton.edu/42directed/Digraph.java.html
 */
package org.gs.digraph

/**
 * @author Gary Struthers
 *
 */
class Digraph(val v: Int) {
  var e = 0
  val adj = Array.fill[List[Int]](v)(List[Int]())

  def addEdge(aV: Int, otherV: Int) {
    def rangeGuard(x: Int) = {
      x match {
        case x if 0 until v contains x => true
        case _ => false
      }
    }
    require(rangeGuard(aV))
    require(rangeGuard(otherV))
    e += 1
    adj(aV) = otherV :: adj(aV)
  }
  
  def reverse(): Digraph = {
    val r = new Digraph(v)
    for {
      newV <- 0 until v
      w <- adj(newV)
    } r.addEdge(w, newV)
    r
  }
}
