package org.gs.graph

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.PrivateMethodTester
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer

@RunWith(classOf[JUnitRunner])
class DigraphSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {
  var testGraph: Digraph = _
  before {
    testGraph = new Digraph(13)
    testGraph.addEdge(4, 2)
    testGraph.addEdge(2, 3)
    testGraph.addEdge(3, 2)
    testGraph.addEdge(6, 0)

    testGraph.addEdge(0, 1)
    testGraph.addEdge(2, 0)
    testGraph.addEdge(11, 12)

    testGraph.addEdge(12, 9)

    testGraph.addEdge(9, 10)
    testGraph.addEdge(9, 11)
    testGraph.addEdge(7, 9)
    testGraph.addEdge(10, 12)
    testGraph.addEdge(11, 4)
    testGraph.addEdge(4, 3)
    testGraph.addEdge(3, 5)
    testGraph.addEdge(6, 8)
    testGraph.addEdge(8, 6)
    testGraph.addEdge(5, 4)
    testGraph.addEdge(0, 5)
    testGraph.addEdge(6, 4)
    testGraph.addEdge(6, 9)
    testGraph.addEdge(7, 6)

  }

  test("depth first search") {
    val fromZero = new DirectedDFS(testGraph, 0)
    assert(fromZero.count === 6)
    val fromSeven = new DirectedDFS(testGraph, 7)
    assert(fromSeven.count === 13)
    val fromNine = new DirectedDFS(testGraph, 9)
    assert(fromNine.count === 10)
  }

  test("depth first directed paths") {
    val fromZero = new DepthFirstDirectedPaths(testGraph, 0)
    val pathZero = fromZero.pathTo(4)
    pathZero.mkString(",")
    assert(pathZero.mkString(",") === "0,5,4")
    val fromSeven = new DepthFirstDirectedPaths(testGraph, 7)
    assert((fromSeven.pathTo(4)).mkString(",") === "7,6,9,11,4")
    val fromNine = new DepthFirstDirectedPaths(testGraph, 9)
    assert((fromNine.pathTo(0)).mkString(",") === "9,11,4,3,2,0")
  }

  test("breadth first directed paths") {
    val fromZero = new BreadthFirstDirectedPaths(testGraph, 0)
    val pathZero = fromZero.pathTo(4)
    pathZero.mkString(",")
    assert(pathZero.mkString(",") === "0,5,4") 
    val fromSeven = new BreadthFirstDirectedPaths(testGraph, 7)
    assert((fromSeven.pathTo(4)).mkString(",") === "7,6,4") 
 
  }
  
  ignore("bf directed paths") {
    val fromNine = new BreadthFirstDirectedPaths(testGraph, 9)
    assert((fromNine.pathTo(0)).mkString(",") === "9,11,4,2,0")
  }

  test("directed cycle") {
    val fromNine = new DirectedCycle(testGraph)
    println(fromNine.cycle.mkString(","))
    assert((fromNine.hasCycle) === true)
  } 
}