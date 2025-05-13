package scala.u04.task5.GPT.completeImplement

/*
  Custom Sequence ADT and Monad Specification in Scala
*/

// ADT definition for a sequence of elements of type A
sealed trait Sequence[+A] {
  /*** Basic operations ***/
  def isEmpty: Boolean
  def head: A
  def tail: Sequence[A]

  /*** Functor operations ***/
  def map[B](f: A => B): Sequence[B]

  /*** Monad operations ***/
  def flatMap[B](f: A => Sequence[B]): Sequence[B]
}

// Companion object with constructors and Monad instance
object Sequence {
  // Constructors
  def empty[A]: Sequence[A] = NilSeq
  def apply[A](as: A*): Sequence[A] = if (as.isEmpty) NilSeq else ConsSeq(as.head, apply(as.tail: _*))

  /*** Monad typeclass ***/
  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  }

  // Monad instance for Sequence
  implicit val sequenceMonad: Monad[Sequence] = new Monad[Sequence] {
    def pure[A](a: A): Sequence[A] = ConsSeq(a, NilSeq)
    def flatMap[A, B](ma: Sequence[A])(f: A => Sequence[B]): Sequence[B] = ma.flatMap(f)
  }

  // Internal data constructors hidden from public API
  private case object NilSeq extends Sequence[Nothing] {
    def isEmpty = true
    def head = throw new NoSuchElementException("Nil.head")
    def tail = throw new NoSuchElementException("Nil.tail")

    def map[B](f: Nothing => B): Sequence[B] = this
    def flatMap[B](f: Nothing => Sequence[B]): Sequence[B] = this
  }

  private final case class ConsSeq[A](head: A, tail: Sequence[A]) extends Sequence[A] {
    def isEmpty = false

    def map[B](f: A => B): Sequence[B] = ConsSeq(f(head), tail.map(f))

    def flatMap[B](f: A => Sequence[B]): Sequence[B] = {
      // f(head) ++ tail.flatMap(f)
      def concat(xs: Sequence[B], ys: Sequence[B]): Sequence[B] = xs match {
        case NilSeq       => ys
        case ConsSeq(h, t) => ConsSeq(h, concat(t, ys))
      }
      concat(f(head), tail.flatMap(f))
    }
  }
}
