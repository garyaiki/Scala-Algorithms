package org.gs.graph.fixtures
/**
 * @see http://algs4.cs.princeton.edu/41undirected/SymbolGraph.java.html
 */
import scala.collection.immutable.TreeMap

import org.gs.fixtures.StringArrayBuilder
import org.gs.fixtures.SymbolTableBuilder
import org.gs.graph.SymbolGraph
import org.gs.graph.Graph

/**
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