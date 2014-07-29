
package org.gs.digraph

import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.PrivateMethodTester._
import org.gs.digraph.fixtures.BellmanFordBuilder

/** [[http://doc.scalatest.org/2.2.0/#org.scalatest.FlatSpec ScalaTest]] for
  * [[org.gs.digraph.BellmanFordSP]]
  * @author Gary Struthers
  * @see http://algs4.cs.princeton.edu/44sp/tinyEWDn.txt
  */
@RunWith(classOf[JUnitRunner])
class BellmanFordSuite extends FlatSpec {
  it should "have consistent distTo and edgeTo when no negative cycles" in new BellmanFordBuilder {

    val s = 0
    val tuple = buildVEDirectedEdges("http://algs4.cs.princeton.edu/44sp/tinyEWDn.txt", s)
    val g = buildEdgeWeightedDigraph(tuple._1)
    val a = buildBellmanFordSP(g, tuple._3)
    val getEdgeTo = PrivateMethod[DirectedEdge]('getEdgeTo)

    assert(a.distTo(s) == 0.0, s"distTo(s):${a.distTo(s)} inconsistent")
    val e = a invokePrivate getEdgeTo(s)
    assert(e == null, s"edgeTo(s):$e inconsistent")

    for {
      v <- 0 until g.V
      e <- g.adj(v)
    } {
      val w = e.to
      if (a.distTo(v) + e.weight < a.distTo(w)) fail(s"edge:$e not relaxed")
    }

    for {
      w <- 0 until g.V
      if (a.invokePrivate(getEdgeTo(w)) != null)
    } {
      val e = a invokePrivate getEdgeTo(w)
      val v = e.from
      if (w != e.to) fail(s"w:$w != e.to:${e.to}")
      if (a.distTo(v) + e.weight != a.distTo(w)) fail(s"edge:$e on shortest path not tight")
    }

    val paths = expectedTinyEWDnPaths(tuple._1, tuple._3)
    for (i <- 0 until tuple._1) {
      val p = a.pathTo(i)
      p match {
        case None => fail(s"path 0-$i not there")
        case Some(x) => assert(x.diff(paths(i)) === List())
      }
    }
  }

  it should "have consistent distTo and edgeTo with negative cycles" in new BellmanFordBuilder {

    val s = 0
    val tuple = buildVEDirectedEdges("http://algs4.cs.princeton.edu/44sp/tinyEWDnc.txt", s)
    val g = buildEdgeWeightedDigraph(tuple._1)
    val a = buildBellmanFordSP(g, tuple._3)
    val getEdgeTo = PrivateMethod[DirectedEdge]('getEdgeTo)

    val nc = a.negativeCycle match {
      case None => fail(s"negative cycle from source vertex:$s not found")
      case Some(x) => x
    }
    val weight = nc.foldLeft(0.0)(_ + _.weight)
    assert(weight < 0.0, s"weight:$weight of negative cycle from source vertex:$s must be negative")
    val ncPaths = expectedTinyEWDncPaths(tuple._3)
    assert(nc.diff(ncPaths) === List())
  }

}