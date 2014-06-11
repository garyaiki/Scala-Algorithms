/**
 *
 */
package org.gs.graph.fixtures

import org.gs.fixtures.SymbolTableBuilder
import scala.io.BufferedSource
import scala.collection.immutable.TreeMap
import org.gs.graph.BaseGraph
import org.gs.fixtures.BufferedSourceBuilder

/**
 * @author Gary Struthers
 *
 */
trait BaseSymbolGraphBuilder {
  
  def buildGraph[T <: BaseGraph](
      savedLines: Array[String],
      st: TreeMap[String, Int],
      g: T, delimiter: String) = {
    for {
      a <- savedLines
    } {
      val s: Array[String] = a.split(delimiter)
      val v = st.get(s(0))
      v match {
        case Some(x) => for {
          i <- 1 until s.size
          w <- st.get(s(i))
        } {
          g.addEdge(x, w)
        }
        case None =>
      }
    }
    g
  }
}