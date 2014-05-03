/**
 * @see http://algs4.cs.princeton.edu/42directed/SymbolDigraph.java.html
 */
package org.gs.digraph.fixtures

import scala.io.Source
import scala.collection.immutable.TreeMap
import org.gs.digraph.Digraph
import scala.io.BufferedSource
import scala.collection.mutable.ArrayBuffer
import org.gs.fixtures.SymbolTableBuilder
import org.gs.graph.fixtures.BaseSymbolGraphBuilder
import org.gs.graph.fixtures.SymbolGraph
import org.gs.fixtures.StringArrayBuilder

/**
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