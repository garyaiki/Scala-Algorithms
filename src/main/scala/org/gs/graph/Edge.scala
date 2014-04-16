package org.gs.graph

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
}
object Edge {

}