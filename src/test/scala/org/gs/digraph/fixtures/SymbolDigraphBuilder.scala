package org.gs.digraph.fixtures

import org.gs.digraph.Digraph
import org.gs.fixtures.{StringArrayBuilder, SymbolTableBuilder}
import org.gs.graph.SymbolGraph
import org.gs.graph.fixtures.BaseSymbolGraphBuilder
import scala.collection.immutable.TreeMap
import scala.io.{BufferedSource, Source}

/** @see [[https://algs4.cs.princeton.edu/42directed/SymbolDigraph.java.html]]
  *
  * @author Gary Struthers
  */
trait SymbolDigraphBuilder extends StringArrayBuilder with SymbolTableBuilder with BaseSymbolGraphBuilder {
  
  def buildSymbolGraph(uri: String, delimiter: String): SymbolGraph[Digraph] = {
    val savedLines = buildFromManagedResource(uri)
    val st = buildStringIndex(delimiter, savedLines)
    val keys = invertIndexKeys(st)
    val g = buildGraph(savedLines, st, new Digraph(st.size),delimiter)
    new SymbolGraph(st, keys, g)
  }
}
