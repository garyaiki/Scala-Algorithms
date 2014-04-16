package org.gs.graph

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.PrivateMethodTester
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue

@RunWith(classOf[JUnitRunner])
class DigraphSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {
  var testGraph: Digraph = _
  var tinyDG: Digraph = _
  var tinyDAG: Digraph = _
  before {
    tinyDAG = new Digraph(13)
    tinyDAG.addEdge(2, 3); tinyDAG.addEdge(0, 6); tinyDAG.addEdge(0, 1)
    tinyDAG.addEdge(2, 0); tinyDAG.addEdge(11, 12); tinyDAG.addEdge(9, 12)
    tinyDAG.addEdge(9, 10); tinyDAG.addEdge(9, 11); tinyDAG.addEdge(3, 5)
    tinyDAG.addEdge(8, 7); tinyDAG.addEdge(5, 4); tinyDAG.addEdge(0, 5)
    tinyDAG.addEdge(6, 4); tinyDAG.addEdge(6, 9); tinyDAG.addEdge(7, 6)

    tinyDG = new Digraph(13)
    tinyDG.addEdge(4, 2); tinyDG.addEdge(2, 3); tinyDG.addEdge(3, 2)
    tinyDG.addEdge(6, 0); tinyDG.addEdge(0, 1); tinyDG.addEdge(2, 0)
    tinyDG.addEdge(11, 12); tinyDG.addEdge(12, 9); tinyDG.addEdge(9, 10)
    tinyDG.addEdge(9, 11); tinyDG.addEdge(7, 9); tinyDG.addEdge(10, 12)
    tinyDG.addEdge(11, 4); tinyDG.addEdge(4, 3); tinyDG.addEdge(3, 5)
    tinyDG.addEdge(6, 8); tinyDG.addEdge(8, 6); tinyDG.addEdge(5, 4)
    tinyDG.addEdge(0, 5); tinyDG.addEdge(6, 4); tinyDG.addEdge(6, 9)
    tinyDG.addEdge(7, 6)
  }

  test("directed DFS") {
    val from1 = new DirectedDFS(tinyDG, 1)
    assert(from1.marked.mkString(",") === 
    		"false,true,false,false,false,false,false,false,false,false,false,false,false")
    val from2 = new DirectedDFS(tinyDG, 2)
    assert(from2.marked.mkString(",") ===
      "true,true,true,true,true,true,false,false,false,false,false,false,false")
  }

  test("depth first directed paths") {
    val from3 = new DepthFirstDirectedPaths(tinyDG, 3)
    assert((from3.pathTo(0)).mkString(",") === "3,5,4,2,0")
    assert((from3.pathTo(1)).mkString(",") === "3,5,4,2,0,1")
    assert((from3.pathTo(2)).mkString(",") === "3,5,4,2")
    assert((from3.pathTo(3)).mkString(",") === "3")
    assert((from3.pathTo(4)).mkString(",") === "3,5,4")
    assert((from3.pathTo(5)).mkString(",") === "3,5")
    assert(from3.hasPathTo(0) === true)
    assert(from3.hasPathTo(1) === true)
    assert(from3.hasPathTo(2) === true)
    assert(from3.hasPathTo(3) === true)
    assert(from3.hasPathTo(4) === true)
    assert(from3.hasPathTo(5) === true)
    assert(from3.hasPathTo(6) === false)
    assert(from3.hasPathTo(7) === false)
    assert(from3.hasPathTo(8) === false)
    assert(from3.hasPathTo(9) === false)
    assert(from3.hasPathTo(10) === false)
    assert(from3.hasPathTo(11) === false)
    assert(from3.hasPathTo(12) === false)
  }

  test("breadth first directed paths") {
    val from3 = new BreadthFirstDirectedPaths(tinyDG, 3)
    assert((from3.pathTo(0)).mkString(",") === "3,2,0")
    assert((from3.pathTo(1)).mkString(",") === "3,2,0,1")
    assert((from3.pathTo(2)).mkString(",") === "3,2")
    assert((from3.pathTo(3)).mkString(",") === "3")
    assert((from3.pathTo(4)).mkString(",") === "3,5,4")
    assert((from3.pathTo(5)).mkString(",") === "3,5")
    assert(from3.hasPathTo(0) === true)
    //   assert(from3.hasPathTo(1) === true)
    assert(from3.hasPathTo(2) === true)
    assert(from3.hasPathTo(3) === true)
    assert(from3.hasPathTo(4) === true)
    assert(from3.hasPathTo(5) === true)
    assert(from3.hasPathTo(6) === false)
    assert(from3.hasPathTo(7) === false)
    assert(from3.hasPathTo(8) === false)
    assert(from3.hasPathTo(9) === false)
    assert(from3.hasPathTo(10) === false)
    assert(from3.hasPathTo(11) === false)
    assert(from3.hasPathTo(12) === false)
    //assert((from3.pathTo(6)).mkString(",") === "3") //@FIXME
    val fromZero = new BreadthFirstDirectedPaths(tinyDG, 0)
    val pathZero = fromZero.pathTo(4)
    pathZero.mkString(",")
    assert(pathZero.mkString(",") === "0,5,4")
    val fromSeven = new BreadthFirstDirectedPaths(tinyDG, 7)
    assert((fromSeven.pathTo(4)).mkString(",") === "7,6,4")

  }

  test("bf directed paths") {
    val fromNine = new BreadthFirstDirectedPaths(tinyDG, 9)
    assert((fromNine.pathTo(4)).mkString(",") === "9,11,4") //@FIXME,2,0")
  }

  test("directed cycle") {
    val tdg = new DirectedCycle(tinyDG)
    assert(tdg.cycle.mkString(",") === "3,5,4,3")
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false)
  }

  test("DepthFirstOrder") {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.preOrder.mkString(",") === "0,5,4,1,6,9,11,12,10,2,3,7,8")
    assert(dfo.postOrder.mkString(",") === "4,5,1,12,11,10,9,6,0,3,2,7,8")
    assert(dfo.reversePost.mkString(",") === "8,7,2,3,0,6,9,10,11,12,1,5,4")
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.preOrder.mkString(",") === "0,5,4,3,2,1,6,9,11,12,10,8,7")
    assert(dfoDG.postOrder.mkString(",") === "2,3,4,5,1,0,12,11,10,9,8,6,7")
    assert(dfoDG.reversePost.mkString(",") === "7,6,8,9,10,11,12,0,1,5,4,3,2")
  }
  
  test("topological") {
    val tdag = new Topological(tinyDAG)
    assert(tdag.order === None)
    val tdg = new Topological(tinyDG)
    val order = tdg.order
    order match {
      case Some(x) => assert(x.mkString(",") === "7,6,8,9,10,11,12,0,1,5,4,3,2")
      case None => fail("no topological order")
    }
  }
  
  test("KosarajuSharirSCC") {
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