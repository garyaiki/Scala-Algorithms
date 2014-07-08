/** Common code for Edge, DirectedEdge
 */
package org.gs.graph

/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @ param v edge from vertex
 * @param w edge to vertex
 * @param weight
 */
abstract class BaseEdge(v: Int, w: Int, val weight: Double) {
  require(v >= 0 && w >= 0 && !weight.isNaN(), "s invalid arg(s) v:$v w:$w weight$weight")

  override def toString(): String = f"$v%d-$w%d $weight%.5f "

}
