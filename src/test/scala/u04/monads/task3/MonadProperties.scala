package scala.u04.monads.task3

import org.scalacheck.{Arbitrary, Gen, Properties}
import org.scalacheck.Prop.forAll
import u04.monads.Monads.*
import u04.monads.Optionals.Optional

object MonadProperties extends Properties("Monad Properties"):

  // Define a generator for Optional monad
  given Arbitrary[Optional[Int]] = Arbitrary {
    Gen.oneOf(Optional.Just(Gen.choose(0, 100).sample.get), Optional.Empty())
  }

  // Define a generator for functions Int => Optional[Int]
  given Arbitrary[Int => Optional[Int]] = Arbitrary {
    Gen.oneOf[Int => Optional[Int]](
      x => Optional.Just(x + 1),
      x => Optional.Just(x * 2),
      _ => Optional.Empty()
    )
  }

  // Left identity: unit(a).flatMap(f) == f(a)
  property("Left Identity") = forAll { (a: Int, f: Int => Optional[Int]) =>
    summon[Monad[Optional]].unit(a).flatMap(f) == f(a)
  }

  // Right identity: m.flatMap(unit) == m
  property("Right Identity") = forAll { (m: Optional[Int]) =>
    m.flatMap(summon[Monad[Optional]].unit) == m
  }

  // Associativity: m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
  property("Associativity") = forAll { (m: Optional[Int], f: Int => Optional[Int], g: Int => Optional[Int]) =>
    m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
  }