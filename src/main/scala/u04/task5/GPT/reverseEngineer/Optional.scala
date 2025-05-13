package scala.u04.task5.GPT.reverseEngineer

// ADT
sealed trait Optional[+A]
case class Some[+A](value: A) extends Optional[A]
case object None extends Optional[Nothing]

// Monad Typeclass
trait Monad[F[_]] {
  def unit[A](a: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

// Instance
given optionalMonad: Monad[Optional] with {
  def unit[A](a: A): Optional[A] = Some(a)

  def flatMap[A, B](fa: Optional[A])(f: A => Optional[B]): Optional[B] = fa match {
    case Some(v) => f(v)
    case None    => None
  }
}

