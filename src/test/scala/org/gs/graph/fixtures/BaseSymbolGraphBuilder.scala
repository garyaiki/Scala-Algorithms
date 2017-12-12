package org.gs.graph.fixtures

import org.gs.fixtures.{BufferedSourceBuilder, SymbolTableBuilder}
import org.gs.graph.BaseGraph
import scala.collection.immutable.TreeMap
import scala.io.BufferedSource

/** @author Gary Struthers */
trait BaseSymbolGraphBuilder {
  
  def buildGraph[A <: BaseGraph](
      savedLines: Array[String],
      st: TreeMap[String, Int],
      g: A, delimiter: String) = {
    for( a <- savedLines) {
      val s: Array[String] = a.split(delimiter)
      val v = st.get(s(0))
      v match {
        case Some(x) => for {
          i <- 1 until s.size
          w <- st.get(s(i))
        } g.addEdge(x, w)
        case None =>
      }
    }
    g
  }
}
