package scala.u04.datastructures.task1

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}
import u04.datastructures.Sequences.*
import Sequence.*

object ScalaCheckPropertiesTest extends Properties("Sequences"):

  def numsToGenerate[A](gen: Gen[A]): Gen[Sequence[A]] = Gen.sized {
    size =>
      val genEmpty = Gen.const(Nil(): Sequence[A])
      val genCons = for {
        head <- gen
        tail <- Gen.resize(size - 1, numsToGenerate(gen))
      } yield Cons(head, tail)
      Gen.frequency((1, genEmpty), (9, genCons))
  }

  def length[A](sequence: Sequence[A]): Int = sequence match {
    case Nil() => 0
    case Cons(_, tail) => 1 + length(tail)
  }

  property("The sum remains the same even after filtering") =
    forAll(numsToGenerate(Arbitrary.arbitrary[Int])) { sequence =>
      val ogSum = sequence.sum
      val filteredSum = sequence.filter(_ => true).sum
      ogSum == filteredSum
    }

  property("Length of Sequence is >= 0") =
    forAll(numsToGenerate(Arbitrary.arbitrary[Int])) { sequence =>
      length(sequence) >= 0
    }