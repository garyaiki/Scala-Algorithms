/**
 * @see http://algs4.cs.princeton.edu/44sp/DijkstraAllPairsSP.java.html
 */
package org.gs.digraph


/**
 * @author Gary Struthers
 *
 */
class DijkstraAllPairsSP(g: EdgeWeightedDigraph) {
  private val all = for (v <- 0 until g.v) yield new DijkstraSP(g, v)

  def path(s: Int, t: Int): Option[List[DirectedEdge]] = all(s).pathTo(t)

  def dist(s: Int, t: Int): Double = all(s).distTo(t)

  def hasPath(s: Int, t: Int): Boolean = dist(s, t) < Double.PositiveInfinity
}
