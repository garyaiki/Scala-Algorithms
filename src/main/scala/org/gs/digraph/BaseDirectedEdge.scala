/** Common trait for DirectedEdge, FlowEdge
  * @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
  * @see http://algs4.cs.princeton.edu/64maxflow/FlowEdge.java.html
  */
package org.gs.digraph

import org.gs.graph.BaseEdge
/** Differentiate directed edge vertices from vertices in undirected edge
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  */
trait BaseDirectedEdge{

  /** start vertex of directed edge */
  def from():Int

  /** end vertex of directed edge */
  def to():Int

}
