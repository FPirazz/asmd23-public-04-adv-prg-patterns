package scala.u04.monads.task4

import u04.monads.States.*
import u04.monads.WindowStateImpl.Window

import java.util.Random

trait DrawNumberGameState:
  type Guesser
  def initialGuesser(): Guesser
  def reset(): State[Guesser, Unit]
  def get(): State[Guesser, Int]
  def nop(): State[Guesser, Unit]
  def checkGuess(guess: Int): State[Guesser, String]

object DrawNumberGameStateImpl extends DrawNumberGameState:
  opaque type Guesser = Int

  def initialGuesser(): Guesser = Random().nextInt(0, 101)
  def reset(): State[Guesser, Unit] = State(i => (Random().nextInt(0, 101), ()))
  def get(): State[Guesser, Int] = State(i => (i, i));
  def nop(): State[Guesser, Unit] = State(i => (i, ()));
  def nopState[S]: State[S, Unit] = State(i => (i, ()))
  def checkGuess(guess: Int): State[Guesser, String] =
    State(i =>
      if guess > i then (i, "Value is too high")
      else if guess < i then (i, "Value is too low")
      else (i, "Correct, you guessed it!")
    )


  // Extension method of `State` to cast `State[Window, A]` to `State[(Window, Guesser), A]`
  extension [A](s: State[Window, A])
    def transform: State[(Window, Guesser), A] = State:
      case (w, num) =>
        val (newW, res) = s.run(w)
        ((newW, num), res)

  // Extension method of `State` again to cast `State[Guesser, A]` to `State[(Window, Guesser), A]`
  extension [A](s: State[Guesser, A])
    def transformGuesser: State[(Window, Guesser), A] = State:
      case (w, num) =>
        val (newNum, res) = s.run(num)
        ((w, newNum), res)
        
        
@main def tryDrawNumberGameState =
  import u04.monads.Monads.*, Monad.*, u04.monads.States.{*, given}, State.*
  val guesserState: DrawNumberGameState = DrawNumberGameStateImpl
  import guesserState.*

  println:
    get().run(initialGuesser()) // ((),  1)

  println:
    seq(reset(), get()).run(initialGuesser()) // ((), 2)