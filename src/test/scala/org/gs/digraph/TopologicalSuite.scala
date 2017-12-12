package org.gs.digraph

import org.gs.digraph.fixtures.{DigraphBuilder, SymbolDigraphBuilder}
import org.scalatest.FlatSpec

/** ScalaTestfor Topological
  *
  * @see [[https://algs4.cs.princeton.edu/42directed/jobs.txt]]
  *
  * @author Gary Struthers
  */
class TopologicalSuite extends FlatSpec {

  behavior of "a Topological"
  it should "find topological order of jobs in a digraph " in new SymbolDigraphBuilder {
    val d = buildSymbolGraph("https://algs4.cs.princeton.edu/42directed/jobs.txt", "/")
    val t = new Topological(d.g)
    val equals = (_: Int) == (_: Int)
    t.order match {
      case Some(x) => assert(x.corresponds(List(9, 8, 4, 5, 0, 1, 7, 11, 12, 10, 2, 3, 6))(equals))
      case None => fail("no topological order")
    }
  }
}
