package org.gs

/** Algorithms for directed graphs, minimum spanning trees, and shortest paths
  *
  * ==Digraphs==
  * {{{
  * val g = new Digraph(numberOfVertices)
  * g.addEdge(v, w)
  * val adjacentVertices = g.adj
  * }}}
  * {{{
  * val from1 = new DirectedDFS(g, 1) // DirectedDFS accepts variable number of source vertices
  * val reachableVerticies = from1.marked
  * }}}
  * {{{
  * val from1And2 = new DirectedDFS(g, 1, 2) // Source vertices 1,2
  * }}}
  * {{{
  * val from1And2 = new DirectedDFS(g, Array[Int](1, 2): _*) // Two source vertices in array
  * }}}
  * {{{
  * val from3 = new DepthFirstDirectedPaths(g, 3) // Source vertex 3
  * from3.pathTo(5) // vertices in path from 3 to 5
  * }}}
  * {{{
  * val dc = new DirectedCycle(g)
  * dc.cycle match {
  *   case None =>
  *   case Some(x) => println(x mkString ",")
  * }
  * }}}
  * {{{
  * val dfo = new DepthFirstOrder(g)
  * val preOrder = dfo.pre()
  * val postOrder = dfo.post()
  * val reversePostOrder = dfo.reversePost()
  * }}}
  * {{{
  * val t = new Topological(g) // accepts Digraph or EdgeWeightedDigraph
  * t.order match {
  *   case Some(x) => println(x mkString ",")
  *   case None => println("no topological order")
  * }
  * }}}
  * {{{
  * val scc = new KosarajuSharirSCC(g)
  * val m = scc.count  // number of strong components
  * val components = new Array[Queue[Int]](m)
  * for(i <- 0 until m) components(i) = new Queue[Int]() // vertices in components
  * }}}
  * ==Minimum Spanning Trees==
  * {{{
  * val g = new EdgeWeightedGraph(numberOfVertices)
  * g.addEdge(v, w)
  * }}}
  * {{{
  * val primMST = new PrimMST(g)
  * val weight = mst.weight
  * val edges = primMST.edges
  * }}}
  * {{{
  * val uf = new UF(g.V)
  * def buildUF(edges: Seq[Edge]): Boolean = {
  *   @tailrec
  *   def loop(i: Int): Boolean = {
  *     if (i < edges.length) {
  *       val v = edges(i).either
  *       val w = edges(i).other(v)
  *       if (uf.connected(v, w)) true else {
  *         uf.union(v, w)
  *         loop(i + 1)
  *       }
  *     } else false
  *   }
  *   loop(0)
  * }
  * val hasCycle = buildUF(edges)
  * }}}
  * ==Shortest Paths==
  {{{
  * val g = new EdgeWeightedDigraph(numberOfVertices)
  * g.addEdge(v, w)
  * }}}
  * {{{
  * val dsp = new DijkstraSP(g, sourceVertex)
  * val hasPath = dsp.hasPathTo(v)
  * dsp.pathTo(v) match {
  *   case Some(x) => println(x mkString ",")
  *   case None => println(path from source to v not found")
  * }
  * }}}
  * {{{
  * val a = new AcyclicSP(g, 5)
  * a.pathTo(i) match {
  *   case None => println("path 5-i not there")
  *   case Some(x) => println(x mkString ",")
  * }
  * }}}
  * {{{
  * new BellmanFordSP(g, s)
  * val p = a.pathTo(i)
  * p match {
  *   case None => println("path 0-i not there")
  *   case Some(x) => println(x mkString ",")
  * }
  * val nc = a.negativeCycle match {
  *   case None => println("negative cycle from source vertex not found")
  *   case Some(x) => x
  * }
  * val weight = nc.foldLeft(0.0)(_ + _.weight)
  * }}}
  * @see http://algs4.cs.princeton.edu/42directed/
  * @see http://algs4.cs.princeton.edu/43mst/
  * @see http://algs4.cs.princeton.edu/44sp/
  */


package object digraph {

}