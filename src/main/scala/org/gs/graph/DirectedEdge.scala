/**
 * @see http://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
 */
package org.gs.graph
/**
 * @author Gary Struthers
 *
 */
class DirectedEdge(v: Int, w: Int, val weight: Double) {
  require(v >= 0 && w >= 0 && !weight.isNaN(), "s invalid arg(s) v:$v w:$w weight$weight")
  
  def from() = v
  
  def to() = w
  
  override def toString() = f"$v%d-$w%d $weight%.5f "

}
object DirectedEdge {

}