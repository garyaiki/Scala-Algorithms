/** @see https://algs4.cs.princeton.edu/44sp/tinyEWD.txt
  */
package org.gs.digraph

import org.scalatest.FlatSpec
import org.gs.digraph.fixtures.TinyEdgeWeightedDigraphBuilder

/** @author Gary Struthers
  *
  */
class EdgeWeightedDigraphSuite extends FlatSpec {

  behavior of "a EdgeWeightedDigraph"

  it should "have no negative weights" in new TinyEdgeWeightedDigraphBuilder {
    assert(g.edges.forall(_.weight >= 0))
  }
}
