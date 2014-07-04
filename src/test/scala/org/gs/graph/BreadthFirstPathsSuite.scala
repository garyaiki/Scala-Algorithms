package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/largeG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.fixtures.IntArrayBuilder

@RunWith(classOf[JUnitRunner])
class BreadthFirstPathsSuite extends FlatSpec {

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
    assert(from0.distTo(1) === 1)
    assert(from0.distTo(2) === 1)
    assert(from0.distTo(3) === 2)
    assert(from0.distTo(4) === 2)
    assert(from0.distTo(5) === 1)
    assert(from0.distTo(6) === Int.MaxValue)
    assert(from0.distTo(7) === Int.MaxValue)
  }

  // -Xms1g -Xmx2g
  it should "build largeG.txt" in new IntArrayBuilder {
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
    //    for(i <- 0 until g.v) if(from0.hasPathTo(i)) println(s"0 hasPathTo $i distance to:${from0.distance(i)}")
    //    println(s"0 distance to 713461:${from0.distance(713461)}")
    assert(from0.distTo(762) === 2)
    assert(from0.distTo(932942) === 1)
    assert(from0.distTo(474885) === 2)
    assert(from0.distTo(460790) === 1)
    assert(from0.distTo(53370) === 2)
    assert(from0.distTo(713461) === 1)
    assert(from0.distTo(75230) === 2)
    //println(s"marked:${from0.marked(82707)} edgeTo:${from0.edgeTo(82707)} distTo:${from0.distTo(82707)}")
    for {
      v <- 0 until g.V
      w <- g.adj(v)
      if (from0.hasPathTo(v))
    } {
      assert(from0.hasPathTo(v) == from0.hasPathTo(w), s"source:0 edge v:$v - w:$w")
      assert(from0.distTo(w) <= from0.distTo(v) + 1,
        s"source:0 v:$v - w:$w distTo w:${from0.distTo(w)}  distTo v + 1:${from0.distTo(v) + 1}")
    }
  }
}