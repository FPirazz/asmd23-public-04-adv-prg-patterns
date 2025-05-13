package scala.u04.task5.GPT.write

trait Monad[F[_]] {
  def pure[A](a: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  // Derived: map via flatMap and pure
  def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))


//  implicit val fileResultMonad: Monad[FileResult] = new Monad[FileResult] {
//    def pure[A](a: A): FileResult = FileContent(a.toString)
//
//    def flatMap[A, B](fa: FileResult)(f: A => FileResult): FileResult = fa match {
//      case FileContent(data) =>
//        // We assume data can be cast back to A; in practice use a wrapper type
//        f(data.asInstanceOf[A])
//      case err@FileError(_) => err
//    }
//  }

}
