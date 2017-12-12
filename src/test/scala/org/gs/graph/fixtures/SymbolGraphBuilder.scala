package org.gs.graph.fixtures

import org.gs.fixtures.{StringArrayBuilder,SymbolTableBuilder}
import org.gs.graph.{Graph, SymbolGraph}
import scala.collection.immutable.TreeMap

/** @see [[https://algs4.cs.princeton.edu/41undirected/SymbolGraph.java.html]]
  *
  * @author Gary Struthers
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
