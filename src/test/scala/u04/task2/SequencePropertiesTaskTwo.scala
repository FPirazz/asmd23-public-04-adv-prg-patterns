package scala.u04.task2

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Properties}

import scala.u04.adts.task2.CustomSequenceCons => csc
import scala.u04.adts.task2.CustomSequenceList => csl

implicit val arbFunction: Arbitrary[Int => csc.ConsSequence[Int]] = Arbitrary {
  for {
    n <- Arbitrary.arbitrary[Int]
  } yield (x: Int) => csc.cons(n + x, csc.nil())
}

object SequencePropertiesTaskTwo extends Properties("Custom Sequences"):

  import scala.u04.adts.task2.CustomSequenceCons._
  import scala.u04.adts.task2.CustomSequenceList._

  // Map Operation
  // Axiom: map(cons(h, t), f) = cons(f(h), map(f, f))
  property("Map Axiom => Cons") =
    forAll { (h: Int, t: List[Int], f: Int => Int) =>
      val customCons = t.foldRight(csc.cons(h, csc.nil[Int]()))((a, acc) => csc.cons(a, acc))
      val consResult = customCons.map(f)

      val customList = h :: t
      val listResult = customList.map(f)

      consResult == t.foldRight(csc.cons(f(h), csc.nil[Int]()))((a, acc) => csc.cons(f(a), acc)) &&
        listResult == (f(h) :: t.map(f))
    }
  
  // Axiom: map(nil, f) = nil
  property("Map Axiom => Nil") =
    forAll { (f: Int => Int) =>
      val consResult = csc.nil[Int]().map(f)
      val listResult = csl.nil[Int]().map(f)

      consResult == csc.nil[Int]() &&
        listResult == csl.nil[Int]()
    }

  
  // Filter Operation
  // Axiom: filter(cons(h, t), p) = if (p(h)) cons(h, filter(t, p)) else filter(t, p)
  property("Filter Axiom => Cons") =
    forAll { (h: Int, t: List[Int], p: Int => Boolean) =>
      val customCons = t.foldRight(csc.cons(h, csc.nil[Int]()))((a, acc) => csc.cons(a, acc))
      val consResult = customCons.filter(p)

      val customList = h :: t
      val listResult = customList.filter(p)

      val expectedConsResult = t.foldRight(if (p(h)) csc.cons(h, csc.nil[Int]()) else csc.nil[Int]())(
        (a, acc) => if (p(a)) csc.cons(a, acc) else acc
      )
      val expectedListResult = customList.filter(p)

      consResult == expectedConsResult && listResult == expectedListResult
    }

  // Axiom: filter(nil, p) = nil
  property("Filter Axiom => Nil") =
    forAll { (p: Int => Boolean) =>
      val consResult = csc.nil[Int]().filter(p)
      val listResult = csl.nil[Int]().filter(p)

      consResult == csc.nil[Int]() &&
        listResult == csl.nil[Int]()
    }

  
  // FlatMap Operation
  // Axiom: flatMap(cons(h, t), f) = concat(f(h), flatMap(t, f))
  property("FlatMap Axiom: Cons") =
    forAll { (h: Int, t: List[Int], f: Int => csc.ConsSequence[Int]) =>
      val customCons = t.foldRight(csc.cons(h, csc.nil[Int]()))((a, acc) => csc.cons(a, acc))
      val consResult = customCons.flatMap(f)

      val customList = t.foldRight(csl.cons(h, csl.nil[Int]()))((a, acc) => csl.cons(a, acc))
      val listResult = customList.flatMap(f.andThen(seq => csl.cons(h, csl.nil())))

      consResult == customCons.flatMap(f) &&
        listResult == customList.flatMap(f.andThen(seq => csl.cons(h, csl.nil())))
    }

  // Axiom: flatMap(nil, f) = nil
  property("FlatMap Axiom: Nil") =
    forAll { (f: Int => csc.ConsSequence[Int]) =>
      val consResult = csc.nil[Int]().flatMap(f)
      val expectedconsResult = csc.nil[Int]()

      val listResult = csl.nil[Int]().flatMap(x => csl.nil())

      consResult == expectedconsResult &&
        listResult == csl.nil[Int]()
    }

  
  // Fold Operations
  // Axiom: fold(cons(h, t), z)(f) = fold(t, f(z, h))
  property("FoldLeft Axiom: Cons") =
    forAll { (h: Int, t: List[Int], z: Int, f: (Int, Int) => Int) =>
      val customCons = t.foldRight(csc.cons(h, csc.nil[Int]()))((a, acc) => csc.cons(a, acc))
      val consResult = customCons.foldLeft(z)(f)

      val customList = t.foldRight(csl.cons(h, csl.nil[Int]()))((a, acc) => csl.cons(a, acc))
      val listResult = customList.foldLeft(z)(f)

      consResult == listResult
    }

  // Axiom: fold(nil, z)(f) = z
  property("FoldLeft Axiom: Nil") =
    forAll { (z: Int, f: (Int, Int) => Int) =>
      val consResult = csc.nil[Int]().foldLeft(z)(f)
      val listResult = csl.nil[Int]().foldLeft(z)(f)

      consResult == listResult
    }

  
  // Axiom: fold(cons(h, t), z)(f) = fold(t, f(z, h))
  property("FoldRight Axiom: Cons") =
    forAll { (h: Int, t: List[Int], z: Int, f: (Int, Int) => Int) =>
      val customCons = t.foldRight(csc.cons(h, csc.nil[Int]()))((a, acc) => csc.cons(a, acc))
      val consResult = customCons.foldRight(z)(f)

      val customList = t.foldRight(csl.cons(h, csl.nil[Int]()))((a, acc) => csl.cons(a, acc))
      val listResult = customList.foldRight(z)(f)

      consResult == listResult
    }

  // Axiom: fold(nil, z)(f) = z
  property("FoldRight Axiom: Nil") =
    forAll { (z: Int, f: (Int, Int) => Int) =>
      val consResult = csc.nil[Int]().foldRight(z)(f)
      val listResult = csl.nil[Int]().foldRight(z)(f)

      consResult == listResult
    }

  
  // Concat Operation
  // Axiom: concat(cons(h, t), l) = cons(h, concat(t, l))
  property("Concat Axiom: Cons") =
    forAll { (seq: List[Int]) =>
      val customCons = seq.foldRight(csc.nil[Int]())((a, acc) => csc.cons(a, acc))
      val consResult = csc.nil[Int]().concat(customCons)
      val expectedConsResult = customCons

      val customList = seq.foldRight(csl.nil[Int]())((a, acc) => csl.cons(a, acc))
      val listResult = csl.nil[Int]().concat(customList)

      consResult == expectedConsResult &&
        listResult == customList
    }

  // Axiom: concat(nil, l) = l
  property("Concat Axiom: Nil") =
    forAll { (h: Int, t: List[Int], seq2: List[Int]) =>
      val customCons1 = t.foldRight(csc.cons(h, csc.nil[Int]()))((a, acc) => csc.cons(a, acc))
      val customCons2 = seq2.foldRight(csc.nil[Int]())((a, acc) => csc.cons(a, acc))
      val consResult = customCons1.concat(customCons2)

      val customList1 = h :: t
      val customList2 = seq2
      val listResult = customList1 ++ customList2

      val expectedConsResult = t.foldRight(csc.cons(h, customCons2))((a, acc) => csc.cons(a, acc))

      consResult == expectedConsResult && listResult == (h :: t ++ seq2)
    }