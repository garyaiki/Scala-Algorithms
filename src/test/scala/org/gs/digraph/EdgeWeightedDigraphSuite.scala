/** @see http://algs4.cs.princeton.edu/44sp/tinyEWD.txt
 */
package org.gs.digraph

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import scala.util.control.Breaks._
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.TinyEdgeWeightedDigraphBuilder


/** @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class EdgeWeightedDigraphSuite extends FlatSpec {

  behavior of "a EdgeWeightedDigraph"

  it should "have no negative weights" in new TinyEdgeWeightedDigraphBuilder {
    assert(g.edges.forall(_.weight >= 0))
  }
}