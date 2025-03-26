package scala.u04.datastructures.task1

import org.scalatest.funsuite.AnyFunSuite
import u04.datastructures.Sequences.*
import Sequence.*

import scala.util.Random

class ScalaTestPropertiesTest extends AnyFunSuite:

  def randomSequence(length: Int): Sequence[Int] = 
    if (length <= 0) Nil()
    else Cons(Random.nextInt(100), randomSequence(length - 1))

  def length[A](stream: Sequence[A]): Int = stream match
    case Nil() => 0
    case Cons(_, tail) => 1 + length(tail)
  
  
  // Test used to verify that the sum remains the same after performing a filtering operation
  test("The sum remains the same even after filtering")
    for (_ <- 1 to 10) { // Runs the test for 10 sequences randomly generated
      val length = Random.nextInt(100) // The length is random, up to 100 members
      val sequence = randomSequence(length)
      val originalSum = sequence.sum
      val filteredSum = sequence.filter(_ => true).sum
      
      assert(originalSum == filteredSum)
    }

  // Test used to verify the length of the Sequence
  test("Length of Sequence is >= 0") {
    for (_ <- 1 to 10) {
      val size = Random.nextInt(100)
      val sequence = randomSequence(size)
      
      assert(length(sequence) >= 0)
    }
  }