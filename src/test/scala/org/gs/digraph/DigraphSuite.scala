package org.gs.digraph
/**
 * @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import scala.collection.mutable.Queue
import org.gs.graph.Topological //@TODO digraph Topological
import org.scalatest.junit.JUnitRunner
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
    assert(from1.marked === Array[Boolean](false, true, false, false, false, false, false, false,
      false, false, false, false, false))
    val from2 = new DirectedDFS(tinyDG, 2)
    assert(from2.marked === Array[Boolean](true, true, true, true, true, true, false, false, false,
      false, false, false, false))
  }

  it should "count reachable verticies" in new DigraphBuilder {
    val from1 = new DirectedDFS(tinyDG, 1)
    assert(from1.count === 1)
    val from2 = new DirectedDFS(tinyDG, 2)
    assert(from2.count === 6)
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
  }

  it should "find absent paths from source vertex to end vertex" in new DigraphBuilder {
    val from3 = new DepthFirstDirectedPaths(tinyDG, 3)
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

  behavior of "a DirectedCycle"
  it should "find digraph's directed cycles" in new DigraphBuilder {
    val tdg = new DirectedCycle(tinyDG)
    assert(tdg.cycle.corresponds(List(3, 5, 4, 3))(equals))
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false)
  }

  it should "show digraph has no directed cycles" in new DigraphBuilder {
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false)
  }

  behavior of "a DepthFirstOrder"
  it should "find pre-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.preOrder.corresponds(List(0, 5, 4, 1, 6, 9, 11, 12, 10, 2, 3, 7, 8))(equals))
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.preOrder.corresponds(List(0, 5, 4, 3, 2, 1, 6, 9, 11, 12, 10, 8, 7))(equals))
  }

  it should "find post-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.postOrder.corresponds(List(4, 5, 1, 12, 11, 10, 9, 6, 0, 3, 2, 7, 8))(equals))
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.postOrder.corresponds(List(2, 3, 4, 5, 1, 0, 12, 11, 10, 9, 8, 6, 7))(equals))
  }

  it should "find reverse post-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.reversePost.corresponds(List(8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 1, 5, 4))(equals))
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.reversePost.corresponds(List(7, 6, 8, 9, 10, 11, 12, 0, 1, 5, 4, 3, 2))(equals))
  }
  behavior of "a Topological"
  it should "find topological order of a Digraph" in new DigraphBuilder {
    val tdg = new Topological(tinyDG)
    tdg.order match {
      case Some(x) => assert(x.corresponds(List(7, 6, 8, 9, 10, 11, 12, 0, 1, 5, 4, 3, 2))(equals))
      case None => fail("no topological order")
    }
  }

  it should "show a Digraph has no topological order" in new DigraphBuilder {
    val tdag = new Topological(tinyDAG)
    assert(tdag.order === None)
  }

  behavior of "a KosarajuSharirSCC"

  it should "find number of components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    val m = scc.count
    assert(m === 5)
  }

  it should "find components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    val m = scc.count
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
    assert(components(0).corresponds(List(1))(equals))
    assert(components(1).corresponds(List(0, 2, 3, 4, 5))(equals))
    assert(components(2).corresponds(List(9, 10, 11, 12))(equals))
    assert(components(3).corresponds(List(6, 8))(equals))
    assert(components(4).corresponds(List(7))(equals))
  }
  
  it should "find strongly connected components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    assert(scc.stronglyConnected(0,2))
    assert(scc.stronglyConnected(0,3))
    assert(scc.stronglyConnected(0,4))
    assert(scc.stronglyConnected(0,5))
    assert(scc.stronglyConnected(6,8))
    assert(scc.stronglyConnected(9,10))
    assert(scc.stronglyConnected(10,11))
    assert(scc.stronglyConnected(11,12))
  }
}