/** @see http://algs4.cs.princeton.edu/41undirected/SymbolGraph.java.html
  */
package org.gs.graph

import scala.collection.immutable.TreeMap


/** Graph where vertex names are strings
  * 
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * 
  * @constructor creates a new SymbolGraph with a symbol table, keys and a graph
  * @tparam A generic graph type, must extend [[org.gs.graph.BaseGraph]]
  * @param st ordered key value map
  * @param keys from st without nulls
  * @param g graph constructed from st
  */
class SymbolGraph[A <: BaseGraph](st: TreeMap[String, Int], val keys: Array[String], val g: A) {
  
  /** Does graph contain @param s */
  def contains(s: String): Boolean = st.contains(s)
  
  /**
   * @param s vertex name
   * @return associated index of vertex
   */
  def index(s: String): Option[Int] = st.get(s)
  
  /**
   * @param v associated index of vertex
   * @return associated name of vertex
   */
  def name(v: Int): String = keys(v)
}
