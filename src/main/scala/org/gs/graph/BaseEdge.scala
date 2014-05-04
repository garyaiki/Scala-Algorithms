/**
 *
 */
package org.gs.graph

/**
 * @author Gary Struthers
 *
 */
abstract class BaseEdge(v: Int, w: Int, val weight: Double) {
  require(v >= 0 && w >= 0 && !weight.isNaN(), "s invalid arg(s) v:$v w:$w weight$weight")

  override def toString() = f"$v%d-$w%d $weight%.5f "

}