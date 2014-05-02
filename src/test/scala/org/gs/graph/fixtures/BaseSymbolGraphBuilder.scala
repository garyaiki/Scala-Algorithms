/**
 *
 */
package org.gs.graph.fixtures

import org.gs.fixtures.SymbolTableBuilder
import scala.io.BufferedSource
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.TreeMap
import org.gs.graph.BaseGraph

/**
 * @author Gary Struthers
 *
 */
abstract class BaseSymbolGraphBuilder extends SymbolTableBuilder {
  def buildFromManagedResource(uri: String): ArrayBuffer[String] = {
    val managedResource = readURI(uri)
    def readFileToArray(buffSource: BufferedSource): ArrayBuffer[String] = {
      val savedLines = new ArrayBuffer[String]()
      val it = buffSource.getLines
      for (a <- it) savedLines.append(a)
      savedLines
    }
    managedResource.loan(readFileToArray)
  }
  
  def buildGraph[T <: BaseGraph](savedLines: ArrayBuffer[String], st: TreeMap[String, Int], g: T, delimiter: String) = {
    for {
      a <- savedLines
    } {
      val s: Array[String] = a.split(delimiter)
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
    g
  }
}