package scala.u04.monads.task4

import u04.monads.CounterStateImpl.Counter
import u04.monads.States.*

trait DrawNumberGameState:
  type DrawnNumber
  def initialDrawnNumber(): DrawnNumber
  def init(): State[DrawnNumber, Unit]
  def get(): State[DrawnNumber, Int]
  def nop(): State[Counter, Unit]

object DrawNumberGameStateImpl extends DrawNumberGameState:
  import scala.util.Random.{nextInt => random} // alias import...
  opaque type DrawnNumber = Int
  
  def initialDrawnNumber(): DrawnNumber = random(100)
  def init(): State[DrawnNumber, Unit] = State(i => (initialDrawnNumber(), ()))
  def get(): State[DrawnNumber, Int] = State(i => (i, i));
  def nop(): State[Counter, Unit] = State(i => (i, ()))
