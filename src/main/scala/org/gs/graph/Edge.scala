/**
 * @see http://algs4.cs.princeton.edu/43mst/Edge.java.html
 */
package org.gs.graph
/**
 * @author Gary Struthers
 *
 */
class Edge(v: Int, w: Int, val weight: Double) extends Ordered[Edge] {
  require(v >= 0 && w >= 0 && !weight.isNaN(), "s invalid arg(s) v:$v w:$w weight$weight")
  
  def either() = v
  
  def other(vertex: Int) = vertex match {
    case `v` => w
    case `w` => v
    case _ => throw new IllegalArgumentException(s"Illegal endpoint vertex:${vertex}")
  }
  
  def compare(that: Edge) = weight.compareTo(that.weight)
  
  override def toString() = f"$v%d-$w%d $weight%.5f "
  
  def canEqual(other: Any) = other.isInstanceOf[Edge]
  
  override def hashCode = 41 * ( 41 + v) + w + weight.hashCode
  
  override def equals(that: Any) = that match {
    case that: Edge => (that canEqual this) && this.hashCode == that.hashCode
  }
}
object Edge {

}