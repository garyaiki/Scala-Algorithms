package org.gs.digraph.fixtures

import org.gs.fixtures.BufferedSourceBuilder
import org.gs.digraph.DirectedEdge
import scala.collection.mutable.ArrayBuffer
import scala.io.BufferedSource

/** Builds arrays of directed edges
  *
  * @author Gary Struthers
  */
trait DirectedEdgeBuilder extends BufferedSourceBuilder {
  val intPattern = """^\d+$""".r
  val edgePattern = """^\s*(\d+)\s+(\d+)\s+(-?\d+[.]\d+)$""".r

  def readFileToTuple(buffSource: BufferedSource): (Int, Int, ArrayBuffer[DirectedEdge]) = {
    val savedLines = new ArrayBuffer[DirectedEdge]()
    var v = 0
    var e = 0

    for( s <- buffSource.getLines) s match {
        case edgePattern(a,b,c) => savedLines.append(new DirectedEdge(a.toInt,b.toInt, c.toDouble))
        case intPattern() => if(v == 0) v = s.toInt else e = s.toInt
        case _ => println(s"DirectedEdgeBuilder error s:${s.toString}")
      }
    (v, e, savedLines)
  }

  def buildFromManagedResource(uri: String): (Int, Int, ArrayBuffer[DirectedEdge]) = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToTuple)
  }
}
