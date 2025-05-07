//package u04.monads
//
//import Monads.*, Monad.*, States.*, State.*, WindowStateImpl.*
//import u04.datastructures.Streams.*
//import scala.util.Random
//
//// State per gestire il numero casuale da indovinare
//trait DrawNumberState:
//  def initialNumber: Int
//  def drawNewNumber: State[Int, Unit]
//  def checkGuess(guess: Int): State[Int, String]
//
//object DrawNumberStateImpl extends DrawNumberState:
//  def initialNumber: Int = Random.nextInt(100) + 1
//
//  def drawNewNumber: State[Int, Unit] =
//    State(s => (Random.nextInt(100) + 1, ()))
//
//  def checkGuess(guess: Int): State[Int, String] =
//    State(s =>
//      if guess < s then (s, "Too Low")
//      else if guess > s then (s, "Too High")
//      else (s, "Correct!")
//    )
//
//@main def drawNumberGame =
//  import DrawNumberStateImpl.*
//
//  def windowSetup(): State[Window, Unit] = for
//    _ <- setSize(400, 100)
//    _ <- addButton(text = "Guess", name = "GuessButton")
//    _ <- addTextField(name = "GuessInput")
//    _ <- addLabel(text = "Make a guess!", name = "ResultLabel")
//    _ <- addButton(text = "Quit", name = "QuitButton")
//    _ <- show()
//  yield ()
//
//  def nop[S]: State[S, Unit] = State(s => (s, ()))
//
//  // Funzione per eseguire operazioni su (Window, Int)
//  def gameLoop(): State[(Window, Int), Unit] = for
//    _ <- windowSetup().transform
//    events <- eventStream().transform
//    _ <- seqN(events.map {
//      case "GuessButton" => for
//        guessStr <- getTextFieldValue("GuessInput").transform
//        result <- checkGuess(guessStr.toInt).transformInt
//        _ <- toLabel(result, "ResultLabel").transform
//        _ <- if result == "Correct!" then drawNewNumber.transformInt else nop[(Window, Int)]
//      case "QuitButton" => for
//        _ => exec(sys.exit())
//      yield ()
//    })
//  yield ()
//
//  // Estensione dello stato per trasformare `State[Window, A]` in `State[(Window, Int), A]`
//  extension [A](s: State[Window, A])
//    def transform: State[(Window, Int), A] = State:
//      case (w, num) =>
//        val (newW, res) = s.run(w)
//        ((newW, num), res)
//    def quitApplication: State[(Window, Unit), A] = State:
//      case (w, num) =>
//        val (newW, res) = s.run(w)
//        ((newW, num), exec(sys.exit()))
//
//  // Estensione dello stato per trasformare `State[Int, A]` in `State[(Window, Int), A]`
//  extension [A](s: State[Int, A])
//    def transformInt: State[(Window, Int), A] = State:
//      case (w, num) =>
//        val (newNum, res) = s.run(num)
//        ((w, newNum), res)
//
//  // Stato iniziale e loop del gioco
//  val game = for
//    _ <- drawNewNumber.transformInt // Inizializza il numero da indovinare
//    _ <- gameLoop()
//  yield ()
//
//  game.run((initialWindow, initialNumber))