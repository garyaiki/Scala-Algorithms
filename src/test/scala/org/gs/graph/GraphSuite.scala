package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 * @see http://algs4.cs.princeton.edu/41undirected/tinyCG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer

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

  trait ConnectedGraphBuilder {
    val tinyCGdata = Array[(Int, Int)]((0, 5), (2, 4), (2, 3), (1, 2), (0, 1), (3, 4), (3, 5),
      (0, 2))
    val tinyCG = new Graph(tinyCGdata.size)
    for (i <- tinyCGdata) tinyCG.addEdge(i._1, i._2)

  }

  behavior of "a BreadthFirstPaths"
  it should "distance of 0 from same vertex" in new ConnectedGraphBuilder {
    for (i <- 0 until tinyCGdata.size) {
      val g = new BreadthFirstPaths(tinyCG, i)
      assert(g.distTo(i) === 0, s"i:$i distTo itself:${g.distTo(i)}")
    }
    def showHasPathTo() {
      for (i <- 0 until tinyCGdata.size) {
        val g = new BreadthFirstPaths(tinyCG, i)
        for (j <- 0 until tinyCGdata.size) {
          println(s"i:$i j:$j hasPathTo:${g.hasPathTo(j)}")
        }
      }
    }
  }

  it should "each edge v-w dist[w] <= dist[v] + 1 where v reachable from s" in
    new ConnectedGraphBuilder {
      for (i <- 0 until tinyCGdata.size) {
        val g = new BreadthFirstPaths(tinyCG, i)
        for {
          v <- 0 until tinyCGdata.size
          w <- tinyCG.adj(v)
          if (g.hasPathTo(v))
        } {
          assert(g.hasPathTo(v) == g.hasPathTo(w), s"source:$i edge v:$v - w:$w")
          assert(g.distTo(w) <= g.distTo(v) + 1,
            s"source:$i v:$v - w:$w distTo w:${g.distTo(w)}  distTo v + 1:${g.distTo(v) + 1}")
        }
      }
    }

  it should "satisfy distTo[w] + distTo[v] + 1 where v = edgeTo[w]" in
    new ConnectedGraphBuilder {
      for (i <- 0 until tinyCGdata.size) {
        val g = new BreadthFirstPaths(tinyCG, i)
        for {
          w <- 0 until tinyCGdata.size
          if (g.hasPathTo(w) && i != w) 
            
        } {
          val v = g.edgeTo(w)
          assert(g.hasPathTo(v) == g.hasPathTo(w), s"source:$i edge v:$v - w:$w")
          assert(g.distTo(w) <= g.distTo(v) + 1,
            s"source:$i v:$v - w:$w distTo w:${g.distTo(w)}  distTo v + 1:${g.distTo(v) + 1}")
        }
      }
    }
  it should "show if source hasPathTo target vertices" in new ConnectedGraphBuilder {
    val from0 = new BreadthFirstPaths(tinyCG, 0)
    for (j <- 1 to 5) {
      assert(from0.hasPathTo(j), s"0 - j:$j")
    }
    for (j <- 6 to 7) {
      assert(from0.hasPathTo(j) === false, s"0 - j:$j")
    }
  }

  it should "show distance from source to target vertices" in new ConnectedGraphBuilder {
    val from0 = new BreadthFirstPaths(tinyCG, 0)
    assert(from0.distance(1) === 1)
    assert(from0.distance(2) === 1)
    assert(from0.distance(3) === 2)
    assert(from0.distance(4) === 2)
    assert(from0.distance(5) === 1)
    assert(from0.distance(6) === Int.MaxValue)
    assert(from0.distance(7) === Int.MaxValue)
  }
}