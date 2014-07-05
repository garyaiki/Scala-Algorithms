package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/largeG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.fixtures.IntArrayBuilder
import org.gs.graph.fixtures.UnweightedEdgeBuilder

class UnweightedGraphBuilder(fileURL: String) extends UnweightedEdgeBuilder {
  private val managedResource = readURI(fileURL)
  private val tuple = managedResource.loan(readFileToTuple)
  private val edges = tuple._3
  private val _graph = new Graph(tuple._1)
  for (i <- edges) _graph.addEdge(i._1, i._2)
  val graph = _graph
}

@RunWith(classOf[JUnitRunner])
class BreadthFirstPathsSuite extends FlatSpec {
  val builder = new UnweightedGraphBuilder("http://algs4.cs.princeton.edu/41undirected/tinyCG.txt")
  val tinyCG = builder.graph

  trait ConnectedGraphBuilder {
    val tinyCGdata = Array[(Int, Int)]((0, 5), (2, 4), (2, 3), (1, 2), (0, 1), (3, 4), (3, 5),
      (0, 2))
    val tinyCG = new Graph(tinyCGdata.size)
    for (i <- tinyCGdata) tinyCG.addEdge(i._1, i._2)

  }

  behavior of "a BreadthFirstPaths"
  it should "have zero distance from a vertex to itself" in {
    for (i <- 0 until tinyCG.V) {
      val g = new BreadthFirstPaths(tinyCG, i)
      assert(g.distTo(i) === 0, s"i:$i distTo itself:${g.distTo(i)}")
    }
  }

  it should "find if source has path to target vertices" in {
    val from0 = new BreadthFirstPaths(tinyCG, 0)
    for (j <- 1 to 5) {
      assert(from0.hasPathTo(j), s"0 - j:$j")
    }
  }

  it should "have each edge v-w dist[w] <= dist[v] + 1 where v reachable from s" in {
    for (i <- 0 until tinyCG.V) {
      val g = new BreadthFirstPaths(tinyCG, i)
      for {
        v <- 0 until tinyCG.V
        w <- tinyCG.adj(v)
      } {
        assert(g.hasPathTo(v) == g.hasPathTo(w), s"source:$i edge v:$v - w:$w")
        assert(g.distTo(w) <= g.distTo(v) + 1,
          s"source:$i v:$v - w:$w distTo w:${g.distTo(w)}  distTo v + 1:${g.distTo(v) + 1}")
      }
    }
  }

  it should "satisfy constraint distTo[w] + distTo[v] + 1 where v = edgeTo[w]" in {
    for (i <- 0 until tinyCG.V) {
      val g = new BreadthFirstPaths(tinyCG, i)
      for {
        w <- 0 until tinyCG.V
        if (g.hasPathTo(w) && i != w)
      } {
        val v = g.edgeTo(w)
        assert(g.distTo(w) <= g.distTo(v) + 1,
          s"source:$i v:$v - w:$w distTo w:${g.distTo(w)}  distTo v + 1:${g.distTo(v) + 1}")
      }
    }
  }

  it should "find distance from source to target vertices" in {
    val from0 = new BreadthFirstPaths(tinyCG, 0)
    assert(from0.distTo(1) === 1)
    assert(from0.distTo(2) === 1)
    assert(from0.distTo(3) === 2)
    assert(from0.distTo(4) === 2)
    assert(from0.distTo(5) === 1)
  }

  // -Xms1g -Xmx2g
  it should "find distances from source to target vertices in largeG.txt" in {
    val gb = new UnweightedGraphBuilder("http://algs4.cs.princeton.edu/41undirected/largeG.txt")
    val largeG = gb.graph
    val from0 = new BreadthFirstPaths(largeG, 0)
    assert(from0.distTo(0) === 0, s"source:0 v:1 distTo:${from0.distTo(1)}")
    assert(from0.distTo(1) === 418, s"source:0 v:1 distTo:${from0.distTo(1)}")
    assert(from0.distTo(2) === 323, s"source:0 v:2 distTo:${from0.distTo(2)}")
    assert(from0.distTo(3) === 168, s"source:0 v:3 distTo:${from0.distTo(3)}")
    assert(from0.distTo(4) === 144, s"source:0 v:4 distTo:${from0.distTo(4)}")
    assert(from0.distTo(5) === 566, s"source:0 v:5 distTo:${from0.distTo(5)}")
    assert(from0.distTo(6) === 349, s"source:0 v:6 distTo:${from0.distTo(6)}")
  }
}