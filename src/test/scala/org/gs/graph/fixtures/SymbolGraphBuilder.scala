/**
 * @see http://algs4.cs.princeton.edu/41undirected/SymbolGraph.java.html
 */
package org.gs.graph.fixtures

import scala.collection.immutable.TreeMap

import org.gs.fixtures.StringArrayBuilder
import org.gs.fixtures.SymbolTableBuilder
import org.gs.graph.BaseGraph
import org.gs.graph.Graph

/**
 * @author Gary Struthers
 */

class SymbolGraph[T <: BaseGraph](st: TreeMap[String, Int], val keys: Array[String], val g: T) {
  def contains(s: String) = st.contains(s)
  def index(s: String) = st.get(s)
  def name(v: Int) = keys(v)
}

trait SymbolGraphBuilder extends StringArrayBuilder with SymbolTableBuilder with BaseSymbolGraphBuilder {
  def buildSymbolGraph(uri: String, delimiter: String): SymbolGraph[Graph] = {
    val savedLines = buildFromManagedResource(uri)
    val st = buildStringIndex(delimiter, savedLines)
    val keys = invertIndexKeys(st)
    val g = buildGraph(savedLines, st, new Graph(st.size),delimiter)
    new SymbolGraph(st, keys, g)
  }
}