package org.gs.digraph

/** Edge with capacity and flow
  *
  * @constructor creates a new FlowEdge with start and end vertices and capacity
  * @param v from vertex
  * @param w to vertex
  * @param capacity of edge
  * @see [[https://algs4.cs.princeton.edu/64maxflow/FlowEdge.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class FlowEdge(v: Int, w: Int, val capacity: Double) extends BaseDirectedEdge {
  require(v >= 0 && w >= 0 && capacity >= 0 && !capacity.isNaN,
    "s invalid arg(s) v:$v w:$w capacity$capacity")
  private var _flow = 0.0

  /** @constructor creates a new FlowEdge with start and end vertices, capacity and flow  */
  def this(v: Int, w: Int, capacity: Double, flow: Double) {
    this(v, w, capacity)
    require(_flow >= 0 && flow <= capacity,
        s"flow:${_flow} must not be negative and can't exceed capacity:$capacity")
    _flow = flow
  }

  override def toString(): String = f"$v%d->$w%d $capacity ${_flow} "

  def from(): Int = v

  def to(): Int = w

  def flow(): Double = _flow

  def other(vertex: Int): Int = vertex match {
    case `v` => w
    case `w` => v
    case _ => throw new IllegalArgumentException(s"Illegal endpoint vertex:${vertex}")
  }

  def residualCapacityTo(vertex: Int): Double = vertex match {
    case `v` => _flow
    case `w` => capacity - _flow
    case _ => throw new IllegalArgumentException(s"Illegal endpoint vertex:${vertex}")
  }

  def addResidualFlowTo(vertex: Int, delta: Double): Unit = {
    require(!delta.isNaN, "s invalid arg(s) vertex:$vertex delta:$delta")
    vertex match {
      case `v` => _flow -= delta
      case `w` => _flow += delta
      case _ => throw new IllegalArgumentException(s"Illegal endpoint vertex:${vertex}")
    }
    assert(_flow >= 0 && flow <= capacity,
      s"flow:${_flow} must not be negative and can't exceed capacity:$capacity")
  }
}
