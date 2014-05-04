/**
 * @see http://algs4.cs.princeton.edu/43mst/Edge.java.html
 */
package org.gs.graph
/**
 * @author Gary Struthers
 *
 */
class Edge(v: Int, w: Int, weight: Double) extends BaseEdge(v, w, weight) with Ordered[Edge] {
  
  def either() = v
  
  def other(vertex: Int) = vertex match {
    case `v` => w
    case `w` => v
    case _ => throw new IllegalArgumentException(s"Illegal endpoint vertex:${vertex}")
  }
  
  def compare(that: Edge) = weight.compareTo(that.weight)
  
  
  def canEqual(other: Any) = other.isInstanceOf[Edge]
  
  override def hashCode = 41 * ( 41 + v) + w + weight.hashCode
  
  override def equals(that: Any) = that match {
    case that: Edge => (that canEqual this) && this.hashCode == that.hashCode
  }
}
