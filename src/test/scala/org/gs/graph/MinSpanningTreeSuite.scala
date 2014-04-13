package org.gs.graph

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.PrivateMethodTester
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue

@RunWith(classOf[JUnitRunner])
class MinSpanningTreeSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {
  test("edge") {
    val piEdge = new Edge(12, 23, 3.14)
    val ltEdge = new Edge(12, 23, 3.13)
    val gtEdge = new Edge(12, 23, 3.15)

    assert(piEdge.compareTo(gtEdge) === -1)
    assert(piEdge.compareTo(ltEdge) === 1)
    assert(piEdge.compareTo(piEdge) === 0)
    assert(piEdge.toString === "12-23 3.14000")
  }
}