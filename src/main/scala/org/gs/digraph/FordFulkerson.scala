/**
 * @see http://algs4.cs.princeton.edu/64maxflow/FordFulkerson.java.html
 */
package org.gs.digraph

import scala.math.{ abs, min }
import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable
import scala.collection.mutable.Queue
import scala.annotation.tailrec
/**
 * @author Gary Struthers
 *
 */
class FordFulkerson(g: FlowNetwork, s: Int, t: Int) {
  def rangeGuard(x: Int) = {
    x match {
      case x if 0 until g.v contains x => true
      case _ => false
    }
  }
  require(rangeGuard(s) && rangeGuard(t), s"source verticies s:$s or t:$t not in 0..${g.v} ")
  require(s != t, s"source s:$s equals target t:$t")

  private var edgeTo = null.asInstanceOf[Array[FlowEdge]]
  private var marked = null.asInstanceOf[Array[Boolean]]

  private def excess(v: Int) = {
    var excess = 0.0
    for (e <- g.adj(v)) if (v == e.from) excess -= e.flow else excess += e.flow
    excess
  }
  private var _value = excess(t)

  private def isFeasible(): Boolean = {
    val Epsilon = 1e-11
    var feasible = true
    var msg = ""
    def capacityGuard(e: FlowEdge) = {
      e match {
        case e if e.flow < -Epsilon => false
        case e if e.flow > e.capacity + Epsilon => false
        case _ => true
      }
    }
    breakable {
      for {
        v <- 0 until g.v
        e <- g.adj(v)
      } if (!capacityGuard(e)) {
        msg = s"Edge e:$e does not satisfy capacity constraints"
        feasible = false
        break
      }
    }
    if (feasible)
      if (abs(_value + excess(s)) > Epsilon) {
        msg = s"Excess at source:${excess(s)} Max flow:${_value}"
        feasible = false
      }
    if (feasible)
      if (abs(_value - excess(t)) > Epsilon) {
        msg = s"Excess at sink:${excess(s)} Max flow:${_value}"
        feasible = false
      }
    if (feasible) breakable {
      for {
        v <- 0 until g.v
        if (v != s && v != t)
      } if (abs(excess(v)) > Epsilon) {
        msg = s"net flow out of v:$v doesn't equal zero"
        feasible = false
        break
      }
    }
    feasible
  }
  assert(isFeasible, "initial flow is infeasible")
//@FIXME endless loop marked(t) always becomes true
  private def hasAugmentingPath() = {println(s"hasAP marked:${marked} edgeTo:${edgeTo}")
    edgeTo = new Array[FlowEdge](g.v)
    marked = Array.fill[Boolean](g.v)(false)
    val q = new Queue[Int]
    q.enqueue(s)
    marked(s) = true
    @tailrec
    def loop(): Unit = {println(s"AP innerLoop marked:${marked.mkString(",")}")
      if (!q.isEmpty) {
        val v = q.dequeue
        for (e <- g.adj(v)) {
          val w = e.other(v)
          if (e.residualCapacityTo(w) > 0) {
            if (!marked(w)) {
              edgeTo(w) = e
              marked(w) = true
              q.enqueue(w)
            }
          }
        }
        loop
      }
    }
    loop
    marked(t)
  }

  private def loopUsingAugmentedPath(): Unit = { println(s"loopAP value$value marked :${marked} edgeTo:${edgeTo}")
    if (hasAugmentingPath) {
      var bottle = Double.PositiveInfinity
      val start = t
      val end = edgeTo(start).other(start)
      breakable {
        for (v <- start to end) {
          if (v == s) break else {
            bottle = min(bottle, edgeTo(v).residualCapacityTo(v))
          }
        }
      }
      breakable {
        for (v <- start to end) {
          if (v == s) break else {
            edgeTo(v).addResidualFlowTo(v, bottle)
          }
        }
      }
      _value += bottle
    }
    loopUsingAugmentedPath
  }
  loopUsingAugmentedPath
  def value(): Double = _value
  
  def inCut(v: Int): Boolean = {
    val R1 = 0 until marked.length
    require(R1.contains(v), s"vertex v:$v is not between 0 and ${marked.length}")
    marked(v)
  }
}

object FordFulkerson {
  def apply(g: FlowNetwork, s: Int, t: Int): Option[FordFulkerson] = {
    val ff = new FordFulkerson(g,s,t)
    def check(): Boolean = {
      if(ff.isFeasible && ff.inCut(s) && ff.inCut(t)) {
        val mincutValues = for {
          v <- 0 until g.v
          e <- g.adj(v)
          if(v == e.from && ff.inCut(e.from) && !ff.inCut(e.to))
        } yield e.capacity
        val mincutvalue = mincutValues.foldLeft(0.0)(_ + _)
        val Epsilon = 1e-11
        if(abs(mincutvalue - ff.value) > Epsilon) false else true
      } else false
    }
    if(check) Some(ff) else None
  }
}