package org.gs.digraph
/** @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
  */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.DigraphBuilder
/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class BreadthFirstDirectedPathsSuite extends FlatSpec { 
  behavior of "a BreadthFirstDirectedPaths"
  it should "find paths from source vertex to end vertex" in new DigraphBuilder {
    val fromZero = new BreadthFirstDirectedPaths(tinyDG, 0)
    assert((fromZero.pathTo(4)).corresponds(List(0, 5, 4))(equals))
    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert(from3.pathTo(0).corresponds(List(3, 2, 0))(equals))
    assert(from3.pathTo(1).corresponds(List(3, 2, 0, 1))(equals))
    //@FIXME assert(from3.distTo(1) === 2)
    assert(from3.pathTo(2).corresponds(List(3, 2))(equals))
    assert(from3.pathTo(3).corresponds(List(3))(equals))
    assert(from3.pathTo(4).corresponds(List(3, 5, 4))(equals))
    assert(from3.pathTo(5).corresponds(List(3, 5))(equals))
    //@FIXME   assert(from3.hasPathTo(1) === true)
    //assert((from3.pathTo(6)).mkString(",") === "3") //@FIXME
    val from7 = new BreadthFirstDirectedPaths(tinyDG, 7)
    assert(from7.pathTo(4).corresponds(List(7, 6, 4))(equals))
    val from9 = new BreadthFirstDirectedPaths(tinyDG, 9)
    assert(from9.pathTo(4).corresponds(List(9, 11, 4))(equals)) //@FIXME,2,0")
  }

  it should "find absent paths from source vertex to end vertex" in new DigraphBuilder {
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

  it should "find distances from source vertex to end vertex" in new DigraphBuilder {
    val from0 = new BreadthFirstDirectedPaths(tinyDG, 0)
    assert(from0.distTo(4) === 2)
    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert(from3.distTo(0) === 2)
    //@FIXME assert(from3.distTo(1) === 2)
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
