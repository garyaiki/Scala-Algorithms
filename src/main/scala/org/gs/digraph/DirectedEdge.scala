/** @see https://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
  */
package org.gs.digraph

import org.gs.graph.BaseEdge
/** Weighted edge.
  *
  * @constructor creates a DirectedEdge with a start and end vertices and a weight
  * @param v edge of vertex
  * @param w edge of vertex
  * @param weight of edge
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class DirectedEdge(v: Int, w: Int, weight: Double) extends BaseEdge(v, w, weight) with BaseDirectedEdge {

  /** returns vertex at head of directed edge */
  def from(): Int = v

  /** returns vertex at tail of directed edge */
  def to(): Int = w

}
