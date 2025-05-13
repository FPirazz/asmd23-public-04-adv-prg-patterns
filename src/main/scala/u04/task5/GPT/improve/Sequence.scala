package scala.u04.task5.GPT.improve

// SequenceADT.scala

package data

// 1) ADT Definition
// -----------------
enum Sequence[+A]:
  case Cons(head: A, tail: Sequence[A])
  case Nil()

object Sequence:
  // smart constructors
  def cons[A](hd: A, tl: Sequence[A]): Sequence[A] = Cons(hd, tl)
  def nil[A]: Sequence[A]                   = Nil()

  // build from Scala Seq
  def fromSeq[A](xs: Seq[A]): Sequence[A] =
    xs.foldRight(nil[A])((a, acc) => cons(a, acc))

  // 2) Core operations as methods on the ADT
  // -----------------------------------------
  extension [A](s: Sequence[A])
    def map[B](f: A => B): Sequence[B] = s match
      case Cons(h, t) => Cons(f(h), t.map(f))
      case Nil()      => Nil()

    def flatMap[B](f: A => Sequence[B]): Sequence[B] = s match
      case Cons(h, t) =>
        // concat the mapped head onto the flatMap of tail
        f(h) ++ t.flatMap(f)
      case Nil()      => Nil()

    def ++[A2 >: A](other: Sequence[A2]): Sequence[A2] = s match
      case Cons(h, t) => Cons(h, t ++ other)
      case Nil()      => other

    def toList: List[A] =
      @annotation.tailrec
      def loop(rem: Sequence[A], acc: List[A]): List[A] = rem match
        case Cons(h, t) => loop(t, h :: acc)
        case Nil()      => acc.reverse

      loop(s, List.empty)

  // 3) Monad Type-Class & Instance
  // -------------------------------
//  trait Monad[F[_]]:
//    def pure[A](a: A): F[A]
//    def flatMap[A,B](fa: F[A])(f: A => F[B]): F[B]
//    extension [A](fa: F[A])
//      def map[B](f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))
//      def flatMap[B](f: A => F[B]): F[B] = flatMap(fa)(f)
//
//  object Monad:
//    def apply[F[_]](using m: Monad[F]): Monad[F] = m
//
//  // Sequence’s Monad instance
//  given sequenceMonad: Monad[Sequence] with
//    def pure[A](a: A): Sequence[A]           = cons(a, nil)
//    def flatMap[A,B](fa: Sequence[A])(f: A => Sequence[B]): Sequence[B] =
//      fa.flatMap(f)


// 4) Monadic Law Specs (documentation)
// ------------------------------------
// For all values a: A, functions f: A => Sequence[B], g: B => Sequence[C]:
//
//  1. Left identity:   pure(a).flatMap(f) ≡ f(a)
//  2. Right identity:  m.flatMap(pure)  ≡ m
//  3. Associativity:   m.flatMap(f).flatMap(g) ≡ m.flatMap(a => f(a).flatMap(g))
//
// You can test these with a property-based testing tool like ScalaCheck:
//
//   import org.scalacheck.Prop.forAll
//   forAll { (a: Int, f: Int => Sequence[Int]) =>
//     Sequence.Monad[Sequence].pure(a).flatMap(f) == f(a)
//   }
//
//   forAll { (m: Sequence[Int]) =>
//     m.flatMap(Sequence.Monad[Sequence].pure) == m
//   }
//
//   forAll { (m: Sequence[Int], f: Int => Sequence[Int], g: Int => Sequence[Int]) =>
//     m.flatMap(f).flatMap(g) ==
//       m.flatMap(a => f(a).flatMap(g))
//   }
