/** 
  * @see http://algs4.cs.princeton.edu/41undirected/Graph.java.html
  * @see http://algs4.cs.princeton.edu/42directed/Digraph.java.html
  *
  */
package org.gs.graph

/** Common code for graph and digraph
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * 
  * @constructor called by subclass with a vertex count
  * @param V number of vertices
  */
abstract class BaseGraph(val V: Int) {
  private var e = 0
  protected[gs] val adj = Array.fill[List[Int]](V)(List[Int]())

  /** add edge between vertices v and w */ 
  def addEdge(v: Int, w: Int): Unit = {
    
    def rangeGuard(x: Int): Boolean = {
      x match {
        case x if 0 until V contains x => true
        case _ => false
      }
    }

    require(rangeGuard(v) && rangeGuard(w), s"aV:$v and otherV:$w must be in 0-$V")
    e += 1
    adj(v) = w :: adj(v)
  }
  
  override def toString(): String = {
    val lf = sys.props("line.separator")
    val sb = new StringBuilder()
    sb append (s"$V ${e} $lf")

    def addLines(v: Int) {
      sb append (s"$v : ")
      adj(v) foreach(e => sb append (s"$e  "))
      sb append (lf)
    }

    for(v <- 0 until V) addLines(v)
    sb.toString
  }
}
