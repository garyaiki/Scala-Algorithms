package org.gs.graph.fixtures

import org.gs.graph.{DepthFirstSearch, Graph}

trait GraphBuilder {
  val tinyGdata = Array[(Int, Int)]((0, 5), (4, 3), (0, 1), (9, 12), (6, 4), (5, 4), (0, 2),
    (11, 12), (9, 10), (0, 6), (7, 8), (9, 11), (5, 3))
  val tinyG = new Graph(tinyGdata.size)
  for (i <- tinyGdata) tinyG.addEdge(i._1, i._2)

  def showMarked(): Unit = {
    for (i <- 0 until tinyGdata.size) {
      val g = new DepthFirstSearch(tinyG, i)
      for (j <- 0 until tinyGdata.size) println(s"i:$i j:$j marked:${g.marked(j)}")
    }
  }
}
