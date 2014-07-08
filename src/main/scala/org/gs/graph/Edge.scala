/** @see http://algs4.cs.princeton.edu/43mst/Edge.java.html
 */
package org.gs.graph
/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param v from vertex
 * @param w to vertex
 * @param weight
 */
class Edge(v: Int, w: Int, weight: Double) extends BaseEdge(v, w, weight) with Ordered[Edge] {

  def either(): Int = v

  def other(vertex: Int): Int = vertex match {
    case `v` => w
    case `w` => v
    case _ => throw new IllegalArgumentException(s"Illegal endpoint vertex:${vertex}")
  }

  def compare(that: Edge): Int = weight.compareTo(that.weight)

  def canEqual(other: Any): Boolean = other.isInstanceOf[Edge]

  override def hashCode(): Int = 41 * ( 41 + v) + w + weight.hashCode

  override def equals(that: Any): Boolean = that match {
    case that: Edge => (that canEqual this) && this.hashCode == that.hashCode
  }
}
