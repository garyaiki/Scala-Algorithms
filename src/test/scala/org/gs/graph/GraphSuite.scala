package org.gs.graph

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.PrivateMethodTester
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer

@RunWith(classOf[JUnitRunner])
class GraphSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {
  var testGraph: Graph = _
  before {
    testGraph = new Graph(13)
    testGraph.addEdge(0, 1)
    testGraph.addEdge(0, 2)
    testGraph.addEdge(0, 5)
    testGraph.addEdge(0, 6)
    testGraph.addEdge(5, 3)
    testGraph.addEdge(5, 4)
    testGraph.addEdge(6, 4)

    testGraph.addEdge(7, 8)

    testGraph.addEdge(9, 10)
    testGraph.addEdge(9, 11)
    testGraph.addEdge(9, 12)
  }

  test("depth first search") {
    val fromZero = new DepthFirstSearch(testGraph, 0)
    assert(fromZero.count === 7)
    val fromSeven = new DepthFirstSearch(testGraph, 7)
    assert(fromSeven.count === 2)
    val fromNine = new DepthFirstSearch(testGraph, 9)
    assert(fromNine.count === 4)
  }

  test("breadth first paths") {
    val toZero = new BreadthFirstPaths(testGraph, 0)
    assert(toZero.hasPathTo(1) === true)
    assert(toZero.distance(1) === 1)
    assert(toZero.hasPathTo(2) === true)
    assert(toZero.distance(2) === 1)
    assert(toZero.hasPathTo(3) === true)
    assert(toZero.distance(3) === 2)
    assert(toZero.hasPathTo(4) === true)
    assert(toZero.distance(4) === 2)
    assert(toZero.hasPathTo(5) === true)
    assert(toZero.distance(5) === 1)
    assert(toZero.hasPathTo(6) === true)
    assert(toZero.distance(6) === 1)
    assert(toZero.hasPathTo(7) === false)
    assert(toZero.distance(7) === Int.MaxValue)
    assert(toZero.hasPathTo(8) === false)
    assert(toZero.distance(8) === Int.MaxValue)
    assert(toZero.hasPathTo(9) === false)
    assert(toZero.distance(9) === Int.MaxValue)
    assert(toZero.hasPathTo(10) === false)
    assert(toZero.distance(10) === Int.MaxValue)
    assert(toZero.hasPathTo(11) === false)
    assert(toZero.distance(11) === Int.MaxValue)
    assert(toZero.hasPathTo(12) === false)
    assert(toZero.distance(12) === Int.MaxValue)
  }
}