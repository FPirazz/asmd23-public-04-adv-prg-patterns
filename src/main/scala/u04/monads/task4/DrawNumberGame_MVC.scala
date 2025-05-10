package scala.u04.monads.task4

@main def runMVC =
  import u04.monads.Monads.*, Monad.*, u04.monads.States.*, State.*, DrawNumberGameStateImpl.*, u04.monads.WindowStateImpl.*
  
  def windowCreation(): State[Window, Unit] = for 
    _ <- setSize(300, 300)
    _ <- addTextField(name = "GuessInput")
    _ <- addButton(text = "Guess", name = "GuessButton")
    _ <- addLabel(text = "Make a Guess", name = "ResultLabel")
    _ <- show()
  yield ()

  def gameLoop(): State[(Window, Guesser), Unit] = for
    _ <- windowCreation().transform
    events <- eventStream().transform
    _ <- seqN(events.map {
      case "GuessButton" => for
        guessStr <- getTextFieldValue("GuessInput").transform
        result <- checkGuess(guessStr.toInt).transformGuesser
        _ <- toLabel(result, "ResultLabel").transform
        _ <- if result == "Correct!" then reset().transformGuesser else nopState[(Window, Guesser)]
      yield ()
    })
  yield ()
  
  // Initial State and gameloop
  val game = for
    _ <- reset().transformGuesser
    _ <- gameLoop()
  yield ()

  game.run((initialWindow, initialGuesser()))