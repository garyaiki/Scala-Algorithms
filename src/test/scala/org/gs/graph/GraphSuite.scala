package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 * @see http://algs4.cs.princeton.edu/41undirected/tinyCG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer
import org.gs.graph.fixtures.SymbolGraphBuilder
import org.gs.fixtures.IntArrayBuilder

@RunWith(classOf[JUnitRunner])
class GraphSuite extends FlatSpec {
  class Movies extends SymbolGraphBuilder {
    val d = buildSymbolGraph("http://algs4.cs.princeton.edu/41undirected/movies.txt", "/")
  }
  val movies = new Movies

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
    val v = savedLines(0)
    val e = savedLines(1)
    val g = new Graph(v)
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
/*    def showHasPathTo() {
      for (i <- 0 until tinyCGdata.size) {
        val g = new BreadthFirstPaths(tinyCG, i)
        for (j <- 0 until tinyCGdata.size) {
          println(s"i:$i j:$j hasPathTo:${g.hasPathTo(j)}")
        }
      }
    } */
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

  // -Xms1g -Xmx2g
  ignore should "build largeG.txt" in new IntArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/41undirected/largeG.txt")
    val savedLines = managedResource.loan(readFileToArray)
    val v = savedLines(0)
    val e = savedLines(1)
    val g = new Graph(v)
    val twoInts = savedLines.drop(2).grouped(2)
    for {
      t <- twoInts
    } g.addEdge(t(0), t(1))
    val from0 = new BreadthFirstPaths(g, 0)
    assert(from0.distance(1) === 418)
    assert(from0.distance(2) === 323)
    assert(from0.distance(3) === 168)
    assert(from0.distance(4) === 144)
    assert(from0.distance(5) === 566)
    assert(from0.distance(6) === 349)
  }
  
  behavior of "a SymbolGraph"

  it should "find routes" in new SymbolGraphBuilder {
    val d = buildSymbolGraph("http://algs4.cs.princeton.edu/41undirected/routes.txt", "\\s+")
    val keys = d.keys
    val g = d.g
    assert("JFK" === keys(0))
    val wJFK = for (w <- g.adj(0)) yield keys(w)
    assert(wJFK.diff(List("MCO", "ATL", "ORD")) === List())
    assert("LAX" === keys(8))
    val wLAX = for (w <- g.adj(8)) yield keys(w)
    assert(wLAX.diff(List("LAS", "PHX")) === List())
  }

  it should "find movies and their actors" in {
    val d = movies.d
    val keys = d.keys
    val g = d.g
    val movieIdx = d.index("Tin Men (1987)")
    val wTinMen = movieIdx match {
      case Some(x) => for (w <- g.adj(x)) yield keys(w)
      case None => fail(s"Tin Men (1987) not found")
    }
    assert(wTinMen.contains("Munchel, Lois Raymond"))
  }

  it should "find actors and their movies" in {
    val d = movies.d
    val keys = d.keys
    val g = d.g
    val idx = d.index("Bacon, Kevin")
    val ws = idx match {
      case Some(x) => for (w <- g.adj(x)) yield keys(w)
      case None => fail(s"Bacon, Kevin not found")
    }
    val count = ws.size
    assert(ws.contains("Wild Things (1998)"))
  }
}