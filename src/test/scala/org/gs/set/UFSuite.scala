/** @see http://algs4.cs.princeton.edu/15uf/largeUF.txt
  */
package org.gs.set

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.fixtures.BufferedSourceBuilder
import org.gs.fixtures.IntArrayBuilder

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class UFSuite extends FlatSpec {
  trait UnionFindBuilder {
    val tinyUFdata = Array((4,3),(3,8),(6,5),(9,4),(2,1),(5,0),(7,2),(6,1))
    val tinyUF = new UF(10)
  }
  
  behavior of "a UF"

  it should "find 2 components" in new UnionFindBuilder {
    for {
      t <- tinyUFdata
      if(!tinyUF.connected(t._1, t._2))
    } tinyUF.union(t._1, t._2)
    assert(tinyUF.count === 2)
  }
  // -Xms256m -Xmx512m
  it should "find 6 components in largeUF" in new IntArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/15uf/largeUF.txt")
    val savedLines = managedResource.loan(readFileToArray)
    val n = savedLines(0)
    val uf = new UF(n)
    val twoInts = savedLines.drop(1).grouped(2)
    for {
      t <- twoInts
      if(!uf.connected(t(0), t(1)))
    } uf.union(t(0), t(1))
    assert(uf.count === 6)
  }
}