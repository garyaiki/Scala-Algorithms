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

/**
 * @author Gary Struthers
 */


class SymbolDigraph(st: TreeMap[String, Int], val keys: Array[String], val g: Digraph) {
  def contains(s: String) = st.contains(s)
  def index(s: String) = st.get(s)
  def name(v: Int) = keys(v)
}

trait SymbolDigraphBuilder extends SymbolTableBuilder {
  def buildFromManagedResource(uri: String, delimiter: String): SymbolDigraph = {
    val managedResource = readURI(uri)
    def buildDigraph(buffSource: BufferedSource): SymbolDigraph = {
      val savedLines = new ArrayBuffer[String]()
      val it = buffSource.getLines
      for (a <- it) savedLines.append(a)
      val st = buildStringIndex(delimiter, savedLines)
      val keys = invertIndexKeys(st)
      val g = new Digraph(st.size)
      for {
        a <- savedLines
      } {
        val s:Array[String] = a.split(delimiter)
        val v = st.get(s(0))
        v match {
          case Some(x) => for {
            i <- 1 until s.size     
            w <- st.get(s(i))
          	} {
          		g.addEdge(x, w)
          }
          case None => 
        }

      }
      new SymbolDigraph(st, keys, g)
    }
    managedResource.loan(buildDigraph)
  }
}