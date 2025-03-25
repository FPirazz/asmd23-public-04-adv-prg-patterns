package scala.u04.adts.task2

import scala.annotation.tailrec

object CustomSequenceCons:
  // Private implementation of the data structure
  private enum SequenceImpl[E]:
    case Cons(head: E, tail: SequenceImpl[E])
    case Nil()
  import SequenceImpl.*

  // Assigning Sequence to be an opaque alias to SequenceImpl
  opaque type ConsSequence[A] = SequenceImpl[A]

  // Defining constructors
  def cons[E](head: E, tail: ConsSequence[E]): ConsSequence[E] = Cons(head, tail)
  def nil[E](): ConsSequence[E] = Nil[E]()

  // Defining operations
  extension [A](seqCons: ConsSequence[A])
    def map[B](mapper: A => B): ConsSequence [B] = seqCons match
      case Cons (h, t) => Cons(mapper(h), t.map(mapper))
      case Nil() => Nil()

    def filter[B](pred: A => Boolean): ConsSequence[A] = seqCons match
      case Cons(h, t) if pred(h) => Cons(h, t.filter(pred))
      case Cons(_, t) => t.filter(pred)
      case Nil() => Nil()

    def flatMap[B](mapper: A => ConsSequence[B]): ConsSequence[B] = seqCons match
      case Cons(h, t) => mapper(h).concat(t.flatMap(mapper))
      case Nil() => Nil()

    def foldRight[B](init: => B)(op: (A, B) => B): B = seqCons match
      case Cons(h, t) => op(h, t.foldRight(init)(op))
      case Nil() => init

    @tailrec
    def foldLeft[B](init: => B)(op: (B, A) => B): B = seqCons match
      case Cons(h, t) => t.foldLeft(op(init, h))(op)
      case Nil() => init

    def reduce[B](op: (A, A) => A): A = seqCons match
      case Cons(h, t) => foldLeft(h)(op)
      case Nil() => throw new UnsupportedOperationException("reduce is empty")

    def concat(other: ConsSequence[A]): ConsSequence[A] = seqCons match
      case Cons(head, tail) => Cons(head, tail.concat(other))
      case Nil() => other