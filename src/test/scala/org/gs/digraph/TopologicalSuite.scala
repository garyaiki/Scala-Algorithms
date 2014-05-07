package org.gs.digraph
/**
 * ScalaTest, JUnit for Topological
 * @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
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
  it should "find routes" in new SymbolDigraphBuilder {
    val d = buildSymbolGraph("http://algs4.cs.princeton.edu/42directed/jobs.txt", "/")
    val t = new Topological(d.g)
    t.order match {
      case Some(x) => for(v <- x) println(s"${d.name(v)}")
      case None => fail("no topological order")
    }
    
  }
  ignore should "find topological order of a Digraph" in new DigraphBuilder {
    val tdg = new Topological(tinyDG)
    tdg.order match {
      case Some(x) => assert(x.diff(expectedOrderTinyDG) === List())
      case None => fail("no topological order")
    }
  }

  ignore should "show a Digraph has no topological order" in new DigraphBuilder {
    val tdag = new Topological(tinyDAG)
    assert(tdag.order === None)
  }


} 