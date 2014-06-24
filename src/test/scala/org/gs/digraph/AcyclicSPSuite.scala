/**
 * ScalaTest, JUnit tests for AcyclicSP
 */
package org.gs.digraph

import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class AcyclicSPSuite extends FlatSpec {
  it should "match expected paths" in new DirectedEdgeBuilder {    
    def expectedPaths() = {
      val paths = new Array[List[DirectedEdge]](tuple._1)
      paths(0) = List(tuple._3(0), tuple._3(4))
      paths(1) = List(tuple._3(3))
      paths(2) = List(tuple._3(2), tuple._3(8))
      paths(3) = List(tuple._3(3), tuple._3(7))
      paths(4) = List(tuple._3(0))
      paths(5) = List()
      paths(6) = List(tuple._3(3), tuple._3(7), tuple._3(10))
      paths(7) = List(tuple._3(2))

      paths
    }
    val managedResource = readURI("http://algs4.cs.princeton.edu/44sp/tinyEWDAG.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new EdgeWeightedDigraph(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    val a = new AcyclicSP(g, 5)
    val paths = expectedPaths
    for(i <- 0 until tuple._1) {
      a.pathTo(i) match {
        case None => fail(s"path 5-$i not there")
        case Some(x) => assert(x.diff(paths(i)) === List())
      } 
    }
  }
}