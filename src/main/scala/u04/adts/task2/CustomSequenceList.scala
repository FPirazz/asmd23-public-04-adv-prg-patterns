package scala.u04.adts.task2

import scala.annotation.tailrec

object CustomSequenceList:

  // Assigning Sequence to be an opaque alias of the native List implementation
  opaque type ListSequence[A] = List[A]

  // Defining constructors with List
  def cons[E](head: E, tail: ListSequence[E]): ListSequence[E] = head :: tail
  def nil[E](): ListSequence[E] = List.empty[E]

  // Defining operations via the Lists defaults
  extension [A](seqList: ListSequence[A])
    def map[B](mapper: A => B): ListSequence[B] = seqList.map(mapper)

    def filter[B](pred: A => Boolean): ListSequence[A] = seqList.filter(pred)

    def flatMap[B](mapper: A => ListSequence[B]): ListSequence[B] = seqList.flatMap(mapper)
      
    def foldRight[B](init: => B)(op: (A, B) => B): B = seqList.foldRight(init)(op)
    
    def foldLeft[B](init: => B)(op: (B, A) => B): B = seqList.foldLeft(init)(op)
    
    def reduce[B](op: (A, A) => A): A = seqList.reduce(op)

    def concat(other: ListSequence[A]): ListSequence[A] = seqList.concat(other)