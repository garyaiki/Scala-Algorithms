/**
 * @see http://algs4.cs.princeton.edu/44sp/tinyEWD.txt
 */
package org.gs.digraph

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.DijkstraSPBuilder
import org.gs.digraph.fixtures.TinyEdgeWeightedDigraphBuilder
import scala.annotation.tailrec

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class DijkstraSPSuite extends FlatSpec {

  behavior of "a DijkstraSP"
  it should "build" in new TinyEdgeWeightedDigraphBuilder {
    val dsp = new DijkstraSP(g, 0)
    assert(dsp !== null)
  }

  it should "have consistent distTo and edgeTo for the source vertex" in new DijkstraSPBuilder {
    val distTo = dsp0.distTo(s0)
    val edgeTo = dsp0.edgeTo(s0)
    val consistent = if (distTo != 0.0 || edgeTo != null) false else true
    assert(consistent)
  }

  it should "have consistent distTo and edgeTo for all verticies" in new DijkstraSPBuilder {
    @tailrec
    def loop(i: Int): Boolean = {
      if (i < g.v) {
        if ((i != s0) && (dsp0.edgeTo(i) == null && dsp0.distTo(i) != Double.PositiveInfinity))
          false else loop(i + 1)
      } else true
    }
    var consistent = loop(0)

    assert(consistent)
  }

  it should "have all edges where distTo(w) <= distTo(v) + e.weight" in new DijkstraSPBuilder {
    @tailrec
    def loopV(i: Int): Boolean = {
      if (i < g.v) {
        val es = g.adj(i)
        @tailrec
        def loopE(es: List[DirectedEdge]): Boolean = {
          es match {
            case Nil => true
            case e :: xs => {
              val w = e.to
              if (dsp0.distTo(1) + e.weight < dsp0.distTo(w)) false else loopE(xs)
            }
          }
        }
        loopE(es)
        loopV(i + 1)
      } else true
    }
    var valid = loopV(0)

    assert(valid)
  }

  it should "have all edges where distTo(w) == distTo(v) + e.weight" in new DijkstraSPBuilder {
    def loop(w: Int): Boolean = {
      if(w < g.v) {
        val e = dsp0.edgeTo(w)
        if (e != null) {
          val v = e.from
          if (w != e.to) false
          if (dsp0.distTo(v) + e.weight != dsp0.distTo(w)) false
        }
        loop(w + 1)
      } else true
    }
    var valid = loop(0) //true 

    assert(valid)
  }

  it should "have shortest paths from 0 to all vertices" in new DijkstraSPBuilder {
    val equals = (_: DirectedEdge) == (_: DirectedEdge)
    for {
      v <- 0 until g.v
    } {
      v match {
        case 0 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List[DirectedEdge]())(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case 1 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(6), tinyEdgeArray(0), tinyEdgeArray(5)))(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case 2 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(7)))(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case 3 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(7), tinyEdgeArray(10), tinyEdgeArray(8)))(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case 4 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(6)))(equals))
          case None =>
        }
        case 5 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(6), tinyEdgeArray(0)))(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case 6 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(7), tinyEdgeArray(10), tinyEdgeArray(8), tinyEdgeArray(12)))(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case 7 => dsp0.pathTo(v) match {
          case Some(x) => assert(x.corresponds(List(tinyEdgeArray(7), tinyEdgeArray(10)))(equals))
          case None => fail(s"path from 0 to $v not found")
        }
        case x if 8 until g.v contains x => assertResult(None) { dsp0.pathTo(v) }
        case _ => fail(s"v:$v is not a valid vertex")
      }
    }
  }
}
