/** @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
 */
package org.gs.digraph

import org.gs.graph.BaseEdge
/** Weighted edge. Extends [[org.gs.graph.BaseEdge]] with [[org.gs.digraph.BaseDirectedEdge]]
 *  
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param v edge of vertex
 * @param w edge of vertex
 * @param weight of edge
 */
class DirectedEdge(v: Int, w: Int, weight: Double) 
    extends BaseEdge(v, w, weight) with BaseDirectedEdge {

  /** @return vertex at start of directed edge */
  def from(): Int = v

  /** @return vertex at end of directed edge */
  def to(): Int = w

}
