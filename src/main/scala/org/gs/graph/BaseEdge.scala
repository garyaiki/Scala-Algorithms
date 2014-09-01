/** @see http://algs4.cs.princeton.edu/43mst/Edge.java.html
  * @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
  */
package org.gs.graph

/** Common code for edge, directed edge
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor called by subclass with both vertices and a weight
  * @param v edge of vertex
  * @param w edge of vertex
  * @param weight of edge
  */
abstract class BaseEdge(v: Int, w: Int, val weight: Double) {
  require(v >= 0 && w >= 0 && !weight.isNaN, "s invalid arg(s) v:$v w:$w weight$weight")

  override def toString: String = f"$v%d-$w%d $weight%.5f "

}
