/**
 *
 */
package org.gs.digraph

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalautils._
import org.scalautils.Tolerance._
import org.scalatest.junit.JUnitRunner

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class FlowEdgeSuite extends FlatSpec {
  behavior of "a FlowEdge"
  
  it should "create a valid FlowEdge from the primary constructor" in {
    val e = new FlowEdge(12,23,3.14)
    assert(e.from === 12)
    assert(e.to === 23)
    assert(e.capacity === 3.14 +- 0.01)
  }
    
  it should "create a valid FlowEdge from the secondary constructor" in {
    val e = new FlowEdge(12,23,3.14, 2.0)
    assert(e.from === 12)
    assert(e.to === 23)
    assert(e.capacity === 3.14 +- 0.01)
    assert(e.flow === 2.0 +- 0.01)
  }

}