package org.gs.digraph

import org.scalatest.FlatSpec
import org.gs.digraph.fixtures.{DigraphBuilder, UnweightedDigraphBuilder}

/** @see [[https://algs4.cs.princeton.edu/44sp/tinyDG.txt]]
  * @author Gary Struthers
  */
class BreadthFirstDirectedPathsSuite extends FlatSpec {
  val builder = new UnweightedDigraphBuilder("https://algs4.cs.princeton.edu/42directed/tinyDG.txt")
  val tinyDG = builder.g
  val equals = (_: Int) == (_: Int)
  
  behavior of "a BreadthFirstDirectedPaths"
  it should "find paths from source vertex to reachable verticies" in {

    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert(from3.pathTo(0).corresponds(List(3, 2, 0))(equals))
    assert(from3.pathTo(1).corresponds(List(3, 2, 0, 1))(equals))
    assert(from3.distTo(1) === 3, s"3 distTo 1:${from3.distTo(1)}")
    assert(from3.pathTo(2).corresponds(List(3, 2))(equals))
    assert(from3.pathTo(3).corresponds(List(3))(equals))
    assert(from3.pathTo(4).corresponds(List(3, 5, 4))(equals))
    assert(from3.pathTo(5).corresponds(List(3, 5))(equals))
    assert(from3.hasPathTo(1) === true)
    assert((from3.hasPathTo(6)) === false)
  }

  it should "find unreachable vertices from source" in {
    val fromZero = new BreadthFirstDirectedPaths(tinyDG, 0)
    assert((fromZero.pathTo(4)).corresponds(List(0, 5, 4))(equals))
    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert(from3.hasPathTo(6) === false)
    assert(from3.hasPathTo(7) === false)
    assert(from3.hasPathTo(8) === false)
    assert(from3.hasPathTo(9) === false)
    assert(from3.hasPathTo(10) === false)
    assert(from3.hasPathTo(11) === false)
    assert(from3.hasPathTo(12) === false)
  }

  it should "find shortest paths from source vertex" in {
    val from0 = new BreadthFirstDirectedPaths(tinyDG, 0)
    assert(from0.distTo(4) === 2)
    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert(from3.distTo(0) === 2)
    assert(from3.distTo(1) === 3)
    assert(from3.distTo(2) === 1)
    assert(from3.distTo(3) === 0)
    assert(from3.distTo(4) === 2)
    assert(from3.distTo(5) === 1)
    val from7 = new BreadthFirstDirectedPaths(tinyDG, 7)
    assert(from7.distTo(4) === 2)
    val from9 = new BreadthFirstDirectedPaths(tinyDG, 9)
    assert(from9.distTo(4) === 2)
  }
}
