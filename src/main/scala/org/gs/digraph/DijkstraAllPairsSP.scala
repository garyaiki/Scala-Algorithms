/** @see http://algs4.cs.princeton.edu/44sp/DijkstraAllPairsSP.java.html
  */
package org.gs.digraph


/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new DijkstraAllPairsSP with an edge weighted digraph
  */
class DijkstraAllPairsSP(g: EdgeWeightedDigraph) {
  private val all = for (v <- 0 until g.V) yield new DijkstraSP(g, v)

  def path(s: Int, t: Int): Option[List[DirectedEdge]] = all(s).pathTo(t)

  def dist(s: Int, t: Int): Double = all(s).distTo(t)

  def hasPath(s: Int, t: Int): Boolean = dist(s, t) < Double.PositiveInfinity
}
