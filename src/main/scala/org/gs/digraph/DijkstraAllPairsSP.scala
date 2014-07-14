/** @see http://algs4.cs.princeton.edu/44sp/DijkstraAllPairsSP.java.html
  */
package org.gs.digraph


/** Solves for all pairs shortest path from a source where edge weights are non-negative
 *  
 *  @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new DijkstraAllPairsSP with an edge weighted digraph
  */
class DijkstraAllPairsSP(g: EdgeWeightedDigraph) {
  private val all = for (v <- 0 until g.V) yield new DijkstraSP(g, v)

  /** returns path from source to t if it exists */
  def path(s: Int, t: Int): Option[List[DirectedEdge]] = all(s).pathTo(t)

  /** returns length of shortest path from source to t */
  def dist(s: Int, t: Int): Double = all(s).distTo(t)

  /** returns if there is a path from source to t */
  def hasPath(s: Int, t: Int): Boolean = dist(s, t) < Double.PositiveInfinity
}
