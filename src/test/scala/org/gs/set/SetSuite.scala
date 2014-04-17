/**
 *
 */
package org.gs.set

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class SetSuite extends FlatSpec {
  trait UnionFindBuilder {
    val tinyUFdata = Array((4,3),(3,8),(6,5),(9,4),(2,1),(5,0),(7,2),(6,1))
    val tinyUF = new UF(10)
  }
  
  behavior of "a UF"

  it should "build 2 components" in new UnionFindBuilder {
    for {
      t <- tinyUFdata
      if(!tinyUF.connected(t._1, t._2))
    } tinyUF.union(t._1, t._2)
    assert(tinyUF.getCount === 2)
  }

}