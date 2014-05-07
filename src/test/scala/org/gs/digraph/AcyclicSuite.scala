/**
 *
 */
package org.gs.digraph

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.DirectedEdgeBuilder

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class AcyclicSuit extends FlatSpec {
  it should "build" in new DirectedEdgeBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/44sp/tinyEWDAG.txt")
    val tuple = managedResource.loan(readFileToTuple)
    println(s"v:${tuple._1}")
    println(s"e:${tuple._2}")
    for(e <- tuple._3) println(s"directed edge:$e" )
  }

}