/** Builds arrays of directed edges
  */
package org.gs.graph.fixtures

import org.gs.digraph.DirectedEdge
import org.gs.fixtures.BufferedSourceBuilder
import scala.collection.mutable.ArrayBuffer
import scala.io.BufferedSource

/** @author Gary Struthers
  *
  */
trait UnweightedEdgeBuilder extends BufferedSourceBuilder {
  val intPattern = """^\d+$""".r
  val edgePattern = """^(\d+)\s+(\d+)\s*$""".r
  def readFileToTuple(buffSource: BufferedSource): (Int, Int, ArrayBuffer[(Int, Int)]) = {
    val savedLines = new ArrayBuffer[(Int, Int)]()
    val it = buffSource.getLines
    var v = 0
    var e = 0
    it foreach (s => s match {
      case edgePattern(a, b) => savedLines.append((a.toInt, b.toInt))
      case intPattern() => if (v == 0) v = s.toInt else e = s.toInt
    })

    (v, e, savedLines)
  }

  def buildFromManagedResource(uri: String): (Int, Int, ArrayBuffer[(Int, Int)]) = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToTuple)
  }
}
