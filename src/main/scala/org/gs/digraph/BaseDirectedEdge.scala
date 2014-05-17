/**
 * Common trait for DirectedEdge, FlowEdge
 * @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
 * @see http://algs4.cs.princeton.edu/64maxflow/FlowEdge.java.html
 */
package org.gs.digraph

import org.gs.graph.BaseEdge
/**
 * @author Gary Struthers
 *
 */
trait BaseDirectedEdge{
  
  def from():Int
  
  def to():Int
  
}
