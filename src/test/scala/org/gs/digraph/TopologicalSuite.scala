package org.gs.digraph
/**
 * ScalaTest, JUnit for Topological
 * @see http://algs4.cs.princeton.edu/42directed/jobs.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.DigraphBuilder
import org.gs.digraph.fixtures.SymbolDigraphBuilder
/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class TopologicalSuite extends FlatSpec {

  behavior of "a Topological"
  it should "find jobs" in new SymbolDigraphBuilder {
    val d = buildSymbolGraph("http://algs4.cs.princeton.edu/42directed/jobs.txt", "/")
    val t = new Topological(d.g)
    val equals = (_: Int) == (_: Int)
    val check = List(9, 8, 4, 5, 0, 1, 7, 11, 12, 10, 2, 3, 6)
    t.order match {
      case Some(x) => assert(x.corresponds(check)(equals))
      case None => fail("no topological order")
    }
  }

} 