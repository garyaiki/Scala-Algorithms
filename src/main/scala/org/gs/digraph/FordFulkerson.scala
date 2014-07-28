/** @see http://algs4.cs.princeton.edu/64maxflow/FordFulkerson.java.html
  */
package org.gs.digraph

import scala.math.{ abs, min }
import scala.collection.mutable.Queue
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new FordFulkerson with a FlowNetwork, source and target vertices
  */
class FordFulkerson(g: FlowNetwork, s: Int, t: Int) {
  private val Epsilon = 1e-11

  private def rangeGuard(x: Int): Boolean = x match {
      case x if 0 until g.v contains x => true
      case _ => false
  }

  require(rangeGuard(s) && rangeGuard(t), s"source verticies s:$s or t:$t not in 0..${g.v} ")
  require(s != t, s"source s:$s equals target t:$t")

  private def excess(v: Int) = {

    def excessFlow(z: Double, e: FlowEdge): Double = (if (v == e.from) -e.flow else e.flow) + z

    g.adj(v).foldLeft(0.0)(excessFlow(_, _))
  }

  private var _value = excess(t)

  private def isFeasible(): Boolean = {

    def capacityGuard(e: FlowEdge): Boolean =
      if ((e.flow < -Epsilon) || (e.flow > e.capacity + Epsilon)) false else true

    @tailrec
    def checkCapacityConstraints(v: Int): Boolean = {
      if (v < g.v) {
        val es = g.adj(v)

        @tailrec
        def checkEdgeCapacityConstraints(es: List[FlowEdge]): Boolean = es match {
            case x :: xs => if (capacityGuard(x)) checkEdgeCapacityConstraints(xs) else false
            case Nil => true
        }

        if (checkEdgeCapacityConstraints(es)) checkCapacityConstraints(v + 1) else false

      } else true
    }

    def lessThanSourceExcess(): Boolean = if (abs(_value + excess(s)) > Epsilon) false else true

    def lessThanSinkExcess(): Boolean = if (abs(_value - excess(t)) > Epsilon) false else true

    @tailrec
    def loopVNetFlow(v: Int): Boolean =
      if (v < g.v) {
        if ((v != s && v != t) && (abs(excess(v)) > Epsilon)) false else loopVNetFlow(v + 1)
      } else true

    checkCapacityConstraints(0) && lessThanSourceExcess() && lessThanSinkExcess() && loopVNetFlow(0)
  }
  assert(isFeasible, "initial flow is infeasible")

  private def hasAugmentingPath(): (Option[Array[FlowEdge]], Array[Boolean]) = {
    val edgeTo = new Array[FlowEdge](g.v)
    val marked = Array.fill[Boolean](g.v)(false)
    marked(s) = true

    @tailrec
    def loopQ(q: Queue[Int]): Unit = {
      if (!q.isEmpty) {
        val v = q.dequeue
        def tryPath(e: FlowEdge): Unit = {
          val w = e.other(v)
          if (e.residualCapacityTo(w) > 0) {
            if (!marked(w)) {
              edgeTo(w) = e
              marked(w) = true
              q.enqueue(w)
            }
          }
        }
        g.adj(v) foreach (tryPath)

        loopQ(q)
      }
    }

    loopQ(Queue[Int](s))
    if (marked(t)) (Some(edgeTo), marked) else (None, marked)
  }

  @tailrec
  private def onAugmentedPath(prevPath: Option[Array[FlowEdge]]):
      (Option[Array[FlowEdge]], Array[Boolean]) = {

    hasAugmentingPath() match {
      case (None, marked) => (Some(prevPath.get), marked)
      case (Some(edgeTo), marked) => {

        @tailrec
        def bottleneckCapacity(v: Int, bottle: Double): Double = if (v != s) {
            val minBottle = min(bottle, edgeTo(v).residualCapacityTo(v))
            val nextV = edgeTo(v).other(v)
            bottleneckCapacity(nextV, minBottle)
          } else bottle

        val bottle = bottleneckCapacity(t, Double.PositiveInfinity)

        @tailrec
        def augmentFlow(v: Int): Unit = if (v != s) {
            edgeTo(v).addResidualFlowTo(v, bottle)
            val nextV = edgeTo(v).other(v)
            augmentFlow(nextV)
        }
        augmentFlow(t)

        _value += bottle
        onAugmentedPath(Some(edgeTo))
      }
    }
  }
  private val pathAndMarked = onAugmentedPath(None)
  val edgeTo = pathAndMarked._1
  private val marked = pathAndMarked._2

  def value(): Double = _value

  def inCut(v: Int): Boolean = {
    val R1 = 0 until marked.length
    require(R1.contains(v), s"vertex v:$v is not between 0 and ${marked.length}")
    marked(v)
  }

  override def toString(): String = {
    val sb = new StringBuilder("edgeTo: ")
    edgeTo match {
      case None => sb append ("None ")
      case Some(x) => sb append (x mkString ",")
    }
    sb append (s" value:${_value}")
    sb append (s" marked:${marked mkString ","}")
    sb.toString()
  }
}

/** Factory for [[org.gs.digraph.FordFulkerson]] instances with additional validation */
object FordFulkerson {
  def apply(g: FlowNetwork, s: Int, t: Int): Option[FordFulkerson] = {
    val ff = new FordFulkerson(g, s, t)

    def check(): Boolean = if (ff.isFeasible && ff.inCut(s) && !ff.inCut(t)) {
        val mincutValues = for {
          v <- 0 until g.v
          e <- g.adj(v)
          if (v == e.from && ff.inCut(e.from) && !ff.inCut(e.to))
        } yield e.capacity

        val mincutvalue = mincutValues.foldLeft(0.0)(_ + _)
        if (abs(mincutvalue - ff.value) > ff.Epsilon) false else true
      } else false

    if (check()) Some(ff) else None
  }
}
