/**
 * @see http://algs4.cs.princeton.edu/44sp/DijkstraAllPairsSP.java.html
 */
package org.gs.graph

/**
 * @author Gary Struthers
 *
 */
class DijkstraAllPairsSP(g: EdgeWeightedDigraph) {
  val all = for (v <- 0 until g.v) yield new DijkstraSP(g, v) 
  
  def path(s: Int, t: Int) = all(s).pathTo(t)
  
  def dist(s: Int, t: Int) = all(s).distTo(t)
  
  def hasPath(s: Int, t: Int) = dist(s, t) < Double.PositiveInfinity

}