package org.gs.graph

import scala.collection._
import scala.collection.generic._
import scala.collection.mutable.{ Builder, ListBuffer }
class Bag[A](seq: A*) extends Traversable[A]
  with GenericTraversableTemplate[A, Bag]
  with TraversableLike[A, Bag[A]] {
  override def companion = Bag
  def foreach[U](f: A => U) = util.Random.shuffle(seq.toSeq).foreach(f)
  
}

object Bag extends TraversableFactory[Bag] {
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Bag[A]] = new GenericCanBuildFrom[A]
  def newBuilder[A] = new ListBuffer[A] mapResult (x => new Bag(x: _*))
}