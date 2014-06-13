package org.gs.digraph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/routes.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.SymbolDigraphBuilder
/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class SymbolDigraphSuite extends FlatSpec {
  behavior of "a SymbolDigraph"
  
  it should "have edges" in new SymbolDigraphBuilder {
    val d = buildSymbolGraph("http://algs4.cs.princeton.edu/41undirected/routes.txt", "\\s+")
    val size = d.keys.size
    for(v <- 0 until size) println(s"index:$v key${d.name(v)} contains:${d.contains(d.name(v))} index:${d.index(d.name(v))}")
    
  }
  
  it should "find routes" in new SymbolDigraphBuilder {
    val d = buildSymbolGraph("http://algs4.cs.princeton.edu/41undirected/routes.txt", "\\s+")
    val keys = d.keys
    val g = d.g
    assert("JFK" === keys(0))
    val wJFK = for (w <- g.adj(0)) yield keys(w)
    assert(wJFK.diff(List("MCO", "ATL", "ORD")) === List())
    assert("LAX" === keys(8))
    val wLAX = for (w <- g.adj(8)) yield keys(w)
    assert(wLAX.diff(List("LAS", "PHX")) === List())
  }
} 