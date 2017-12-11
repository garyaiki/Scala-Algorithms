package org.gs

/** Algorithms for undirected graphs
  *
  * ==Graphs==
  * {{{
  * val g = new Graph(numberOfVertices)
  * g.addEdge(v, w)
  * }}}
  * {{{
  * val from0 = new DepthFirstSearch(g, 0)
  * val verticiesAdjacentTo0 = from0.count
  * val isPath0To6 = from0.marked(6)
  * }}}
  * {{{
  * val from1 = new BreadthFirstPaths(g, 1)
  * val isPath1To5 = from1.hasPathTo(5)
  * val distTo = from1.distTo(5)
  * }}}
  * @see [[https://algs4.cs.princeton.edu/41undirected/]]
  */
package object graph {}
