package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/mediumG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer
import org.gs.fixtures.IntArrayBuilder

@RunWith(classOf[JUnitRunner])
class GraphSuite extends FlatSpec {

  trait GraphBuilder {
    val tinyGdata = Array[(Int, Int)]((0, 5), (4, 3), (0, 1), (9, 12), (6, 4), (5, 4), (0, 2),
      (11, 12), (9, 10), (0, 6), (7, 8), (9, 11), (5, 3))
    val tinyG = new Graph(tinyGdata.size)
    for (i <- tinyGdata) tinyG.addEdge(i._1, i._2)

    def showMarked() {
      for (i <- 0 until tinyGdata.size) {
        val g = new DepthFirstSearch(tinyG, i)
        for (j <- 0 until tinyGdata.size) {
          println(s"i:$i j:$j marked:${g.marked(j)}")
        }
      }
    }
  }

  behavior of "a Graph"
  it should "build mediumG.txt" in new IntArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/41undirected/mediumG.txt")
    val savedLines = managedResource.loan(readFileToArray)
    val g = new Graph(savedLines(0))
    val twoInts = savedLines.drop(2).grouped(2)
    for {
      t <- twoInts
    } g.addEdge(t(0), t(1))
    assert(g.adj(2).diff(Array[Int](141, 110, 108, 86, 79, 51, 42, 18, 14)) === Array())
  }

  behavior of "a DepthFirstSearch"
  it should "count verticies connected to an edge" in new GraphBuilder {
    val from0 = new DepthFirstSearch(tinyG, 0)
    assert(from0.count === 7)
    val from1 = new DepthFirstSearch(tinyG, 1)
    assert(from1.count === 7)
    val from7 = new DepthFirstSearch(tinyG, 7)
    assert(from7.count === 2)
    val from9 = new DepthFirstSearch(tinyG, 9)
    assert(from9.count === 4)
  }

  it should "show if there's a path from source to target" in new GraphBuilder {
    val from0 = new DepthFirstSearch(tinyG, 0)
    for (j <- 0 to 6) {
      assert(from0.marked(j), s"0 - j:$j")
    }
    val from1 = new DepthFirstSearch(tinyG, 1)
    for (j <- 0 to 6) {
      assert(from1.marked(j), s"1 - j:$j")
    }
    val from7 = new DepthFirstSearch(tinyG, 7)
    for (j <- 0 to 6) {
      assert(from7.marked(j) === false, s"7 - j:$j")
    }
    val from9 = new DepthFirstSearch(tinyG, 9)
    for (j <- 0 to 6) {
      assert(from9.marked(j) === false, s"9 - j:$j")
    }
  }
}