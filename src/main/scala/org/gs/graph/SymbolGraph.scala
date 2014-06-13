/**
 * @see http://algs4.cs.princeton.edu/41undirected/SymbolGraph.java.html
 */
package org.gs.graph

import scala.collection.immutable.TreeMap


/**
 * @author Gary Struthers
 */
class SymbolGraph[T <: BaseGraph](st: TreeMap[String, Int], val keys: Array[String], val g: T) {
  def contains(s: String): Boolean = st.contains(s)
  def index(s: String): Option[Int] = st.get(s)
  def name(v: Int): String = keys(v)
}
