package org.gs.graph.fixtures
/** @see https://algs4.cs.princeton.edu/41undirected/SymbolGraph.java.html
  */
import scala.collection.immutable.TreeMap

import org.gs.fixtures.{StringArrayBuilder,SymbolTableBuilder}
import org.gs.graph.{Graph, SymbolGraph}

/** @author Gary Struthers
  */
trait SymbolGraphBuilder extends StringArrayBuilder with SymbolTableBuilder with BaseSymbolGraphBuilder {
  def buildSymbolGraph(uri: String, delimiter: String): SymbolGraph[Graph] = {
    val savedLines = buildFromManagedResource(uri)
    val st = buildStringIndex(delimiter, savedLines)
    val keys = invertIndexKeys(st)
    val g = buildGraph(savedLines, st, new Graph(st.size),delimiter)
    new SymbolGraph(st, keys, g)
  }
}
