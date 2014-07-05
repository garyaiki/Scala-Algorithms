/**
 * @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
 */
package org.gs.digraph

import org.gs.graph.BaseEdge
/**
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 *
 */
class DirectedEdge(v: Int, w: Int, weight: Double) 
					                         extends BaseEdge(v, w, weight) with BaseDirectedEdge {

  def from(): Int = v

  def to(): Int = w

}
