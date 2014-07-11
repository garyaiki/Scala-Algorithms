/** Builds arrays of directed edges
  */
package org.gs.digraph.fixtures

import org.gs.fixtures.BufferedSourceBuilder
import scala.io.BufferedSource
import scala.collection.mutable.ArrayBuffer
import org.gs.digraph.FlowEdge

/** @author Gary Struthers
  *
  */
trait FlowEdgeBuilder extends BufferedSourceBuilder {
  val intPattern = """^\d+$""".r
  val edgePattern = """^(\d+)\s+(\d+)\s+(-?\d+[.]\d+)$""".r
  def readFileToTuple(buffSource: BufferedSource): (Int, Int, ArrayBuffer[FlowEdge]) = {
    val savedLines = new ArrayBuffer[FlowEdge]()
    val it = buffSource.getLines
    var v = 0
    var e = 0
    for {
      s <- it
    } {
      s match {
        case edgePattern(a,b,c) => savedLines.append(new FlowEdge(a.toInt,b.toInt, c.toDouble))
        case intPattern() => if(v == 0) v = s.toInt else e = s.toInt
      }
    }
    (v, e, savedLines)
  }

  def buildFromManagedResource(uri: String): (Int, Int, ArrayBuffer[FlowEdge]) = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToTuple)
  }
}