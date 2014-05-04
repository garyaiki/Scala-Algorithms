/**
 * @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
 */
package org.gs.digraph

import org.gs.graph.BaseEdge
/**
 * @author Gary Struthers
 *
 */
class DirectedEdge(v: Int, w: Int, weight: Double)  extends BaseEdge(v, w, weight){
  
  def from() = v
  
  def to() = w
  
}
