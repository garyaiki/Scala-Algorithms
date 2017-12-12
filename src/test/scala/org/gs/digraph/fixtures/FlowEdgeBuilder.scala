package org.gs.digraph.fixtures

import org.gs.digraph.FlowEdge
import org.gs.fixtures.BufferedSourceBuilder
import scala.collection.mutable.ArrayBuffer
import scala.io.BufferedSource

/** Builds arrays of directed edges
  *
  * @author Gary Struthers
  */
trait FlowEdgeBuilder extends BufferedSourceBuilder {
  val docTypePattern = """<!DOCTYPE\[^>\[\]*(\[\[^\]\]*\])?>""".r
  val intPattern = """^\d+$""".r
  val edgePattern = """^(\d+)\s+(\d+)\s+(-?\d+[.]\d+)$""".r

  def readFileToTuple(buffSource: BufferedSource): (Int, Int, ArrayBuffer[FlowEdge]) = {
    val savedLines = new ArrayBuffer[FlowEdge]()
    var v = 0
    var e = 0

    for(s <- buffSource.getLines) s match {
        case edgePattern(a,b,c) => savedLines.append(new FlowEdge(a.toInt,b.toInt, c.toDouble))
        case intPattern() => if(v == 0) v = s.toInt else e = s.toInt
        case docTypePattern =>
      }
    (v, e, savedLines)
  }

  def buildFromManagedResource(uri: String): (Int, Int, ArrayBuffer[FlowEdge]) = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToTuple)
  }
}
