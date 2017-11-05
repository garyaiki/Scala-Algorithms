/** Builds arrays of directed edges
  */
package org.gs.graph.fixtures

import org.gs.digraph.DirectedEdge
import org.gs.fixtures.BufferedSourceBuilder
import org.gs.graph.Edge
import scala.collection.mutable.ArrayBuffer
import scala.io.BufferedSource

/** @author Gary Struthers
  *
  */
trait EdgeBuilder extends BufferedSourceBuilder {
  val docTypePattern = """<!DOCTYPE\[^>\[\]*(\[\[^\]\]*\])?>""".r
  val intPattern = """^\d+$""".r
  val edgePattern = """^(\d+)\s+(\d+)\s+(-?\d+[.]\d+)$""".r
  def readFileToTuple(buffSource: BufferedSource): (Int, Int, ArrayBuffer[Edge]) = {
    val savedLines = new ArrayBuffer[Edge]()
    val it = buffSource.getLines
    var v = 0
    var e = 0
    for(s <- it) s match {
        case edgePattern(a,b,c) => savedLines.append(new Edge(a.toInt,b.toInt, c.toDouble))
        case intPattern() => if(v == 0) v = s.toInt else e = s.toInt
        case docTypePattern =>
      }
    (v, e, savedLines)
  }

  def buildFromManagedResource(uri: String): (Int, Int, ArrayBuffer[Edge]) = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToTuple)
  }
}
