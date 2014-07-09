/**
 */
package org.gs.digraph

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalautils._
import org.scalautils.Tolerance._
import org.gs.digraph.fixtures.TinyEdgeWeightedDigraphBuilder
import org.scalatest.junit.JUnitRunner

trait DijkstraAllPairsSPBuilder extends TinyEdgeWeightedDigraphBuilder {
  val verticesLength = g.V
  val allPairsSP = new DijkstraAllPairsSP(g)
}
/** @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class DijkstraAllPairsSPSuite extends FlatSpec {
  behavior of "a DijkstraAllPairsSP"
  it should "build" in new TinyEdgeWeightedDigraphBuilder {
    val allPairsSP = new DijkstraAllPairsSP(g)
  }
  
  it should "show from = 0 - to = 1..7 vertex distances" in new DijkstraAllPairsSPBuilder {
    for {
      t <- 1 to 7
    } {
      t match {
        case 1 => assert(allPairsSP.dist(0,t) === 1.05 +- 0.01)
        case 2 => assert(allPairsSP.dist(0,t) === 0.26 +- 0.01)
        case 3 => assert(allPairsSP.dist(0,t) === 0.99 +- 0.01)
        case 4 => assert(allPairsSP.dist(0,t) === 0.38 +- 0.01)
        case 5 => assert(allPairsSP.dist(0,t) === 0.73 +- 0.01)
        case 6 => assert(allPairsSP.dist(0,t) === 1.51 +- 0.01)
        case 7 => assert(allPairsSP.dist(0,t) === 0.60 +- 0.01)
        case _ =>
      }
    }
    
  }
}