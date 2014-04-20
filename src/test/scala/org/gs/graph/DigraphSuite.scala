package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class DigraphSuite extends FlatSpec {
  trait DigraphBuilder {
    var tinyDAGdata = Array[(Int, Int)]((2, 3), (0, 6), (0, 1), (2, 0), (11, 12), (9, 12),
      (9, 10), (9, 11), (3, 5), (8, 7), (5, 4), (0, 5), (6, 4), (6, 9), (7, 6))
    var tinyDAG = new Digraph(13)
    for (t <- tinyDAGdata) tinyDAG.addEdge(t._1, t._2)

    var tinyDGdata = Array[(Int, Int)]((4, 2), (2, 3), (3, 2), (6, 0), (0, 1), (2, 0),
      (11, 12), (12, 9), (9, 10), (9, 11), (7, 9), (10, 12), (11, 4), (4, 3), (3, 5),
      (6, 8), (8, 6), (5, 4), (0, 5), (6, 4), (6, 9), (7, 6))

    var tinyDG = new Digraph(13)
    for (t <- tinyDGdata) tinyDG.addEdge(t._1, t._2)

    val equals = (_: Int) == (_: Int)
  }
  behavior of "a DirectedDFS"
  it should "mark reachable verticies" in new DigraphBuilder {
    val from1 = new DirectedDFS(tinyDG, 1)
    assert(from1.marked === Array[Boolean](false, true, false, false, false, false, false, false, false,
      false, false, false, false))
    val from2 = new DirectedDFS(tinyDG, 2)
    assert(from2.marked === Array[Boolean](true, true, true, true, true, true, false, false, false, false,
      false, false, false))
  }

  behavior of "a DepthFirstDirectedPaths"
  it should "find paths from source vertex to end vertex" in new DigraphBuilder {
    val from3 = new DepthFirstDirectedPaths(tinyDG, 3)

    assert(from3.pathTo(0).corresponds(List(3, 5, 4, 2, 0))(equals))
    assert(from3.pathTo(1).corresponds(List(3, 5, 4, 2, 0, 1))(equals))
    assert(from3.pathTo(2).corresponds(List(3, 5, 4, 2))(equals))
    assert(from3.pathTo(3).corresponds(List(3))(equals))
    assert(from3.pathTo(4).corresponds(List(3, 5, 4))(equals))
    assert(from3.pathTo(5).corresponds(List(3, 5))(equals))
    assert(from3.hasPathTo(6) === false)
    assert(from3.hasPathTo(7) === false)
    assert(from3.hasPathTo(8) === false)
    assert(from3.hasPathTo(9) === false)
    assert(from3.hasPathTo(10) === false)
    assert(from3.hasPathTo(11) === false)
    assert(from3.hasPathTo(12) === false)
  }

  behavior of "a BreadthFirstDirectedPaths"
  it should "find paths from source vertex to end vertex" in new DigraphBuilder {
    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert((from3.pathTo(0)).corresponds(List(3,2,0))(equals))
    assert((from3.pathTo(1)).corresponds(List(3,2,0,1))(equals))
    assert((from3.pathTo(2)).corresponds(List(3,2))(equals))
    assert((from3.pathTo(3)).corresponds(List(3))(equals))
    assert((from3.pathTo(4)).corresponds(List(3,5,4))(equals))
    assert((from3.pathTo(5)).corresponds(List(3,5))(equals))
    //@FIXME   assert(from3.hasPathTo(1) === true)
    assert(from3.hasPathTo(6) === false)
    assert(from3.hasPathTo(7) === false)
    assert(from3.hasPathTo(8) === false)
    assert(from3.hasPathTo(9) === false)
    assert(from3.hasPathTo(10) === false)
    assert(from3.hasPathTo(11) === false)
    assert(from3.hasPathTo(12) === false)
    //assert((from3.pathTo(6)).mkString(",") === "3") //@FIXME
    val fromZero = new BreadthFirstDirectedPaths(tinyDG, 0)
    assert((fromZero.pathTo(4)).corresponds(List(0,5,4))(equals))
    val fromSeven = new BreadthFirstDirectedPaths(tinyDG, 7)
    assert((fromSeven.pathTo(4)).corresponds(List(7,6,4))(equals))
    val fromNine = new BreadthFirstDirectedPaths(tinyDG, 9)
    assert((fromNine.pathTo(4)).corresponds(List(9,11,4))(equals)) //@FIXME,2,0")
  }

  behavior of "a DirectedCycle"
  it should "find digraph's directed cycles" in new DigraphBuilder {
    val tdg = new DirectedCycle(tinyDG)
    assert(tdg.cycle.mkString(",") === "3,5,4,3")
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false)
  }

  behavior of "a DepthFirstOrder"
  it should "find pre-order, post-order and reverse post-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.preOrder.mkString(",") === "0,5,4,1,6,9,11,12,10,2,3,7,8")
    assert(dfo.postOrder.mkString(",") === "4,5,1,12,11,10,9,6,0,3,2,7,8")
    assert(dfo.reversePost.mkString(",") === "8,7,2,3,0,6,9,10,11,12,1,5,4")
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.preOrder.mkString(",") === "0,5,4,3,2,1,6,9,11,12,10,8,7")
    assert(dfoDG.postOrder.mkString(",") === "2,3,4,5,1,0,12,11,10,9,8,6,7")
    assert(dfoDG.reversePost.mkString(",") === "7,6,8,9,10,11,12,0,1,5,4,3,2")
  }
  behavior of "a Topological"
  it should "find topological order of a Digraph" in new DigraphBuilder {
    val tdag = new Topological(tinyDAG)
    assert(tdag.order === None)
    val tdg = new Topological(tinyDG)
    val order = tdg.order
    order match {
      case Some(x) => assert(x.mkString(",") === "7,6,8,9,10,11,12,0,1,5,4,3,2")
      case None => fail("no topological order")
    }
  }

  behavior of "a KosarajuSharirSCC"
  it should "find components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    val m = scc.count
    assert(m === 5)
    val components = new Array[Queue[Int]](m)
    for {
      i <- 0 until m
    } {
      components(i) = new Queue[Int]()
    }
    for {
      v <- 0 until tinyDG.v
    } {
      components(scc.id(v)).enqueue(v)
    }
    assert(components.flatten.mkString(",") === "1,0,2,3,4,5,9,10,11,12,6,8,7")
  }

}