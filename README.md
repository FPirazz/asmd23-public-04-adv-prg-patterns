# 04Lab - Verified Specification, ADTs, type classes, and monads

## Task 1: TEST-OPERATE

Download the repo and check everything works as expected. Play just a bit with ScalaCheck and see which parameters it 
has (e.g. number of generated tests?). Play also with ScalaTest, and see if it can perform parameterized tests. What 
are key differences between the two?

### Work Done:

As per asked task, I've played around with both the ScalaTest and ScalaCheck library, which are extensively used in order
to test functionalities of Scala applications, by developing two different test classes, that test the same operations
and axioms, as shown below. The properties tested were that of the Sequence object available in the lab repo, by which
I check the properties that:
* A Sequence has a length >= 0, regardless of the fact that it's filled or not with elements;
* The sum of the members of a Sequence (made up of integers), remains the same after applying a generic filter that is
true for all cases.
Both of these properties were test in ScalaCheck and ScalaTest.

#### ScalaCheck

In order to test well ScalaCheck, I developed 
[ScalaCheckPropertiesTest.scala](src/test/scala/u04/datastructures/task1/ScalaCheckPropertiesTest.scala). Generally
speaking the library's used to do *Property-Based Testing*, where the main idea is to define general properties that can
be easily tested, and therefore the code and entities tested need to adhere to. ScalaCheck also permits to generate
automatically input tests randomly when verifying these properties, to make sure invariance isn't influencing the tests.

The main characteristics of the library are:
* **Data Generation**, meaning that ScalaCheck uses natively present generators to randomly create values to test with,
in the code mainly seen in the `numsToGenerate[A]` method;
* **Property Definition**, meaning that the properties that need to be tested, are defined through the `property` method,
by which for example the property `"The sum remains the same even after filtering"` verifies that the sum of a Sequence
is always the same, even after performing a basic filtering;
* **Properties validation**, ScalaCheck performs test on a wide range of random values to make sure to cover a big range
of scenarios to make sure the code is correct.

#### ScalaTest

Meanwhile, in order to test ScalaTest, I developed the 
[ScalaTestPropertiesTest.scala](src/test/scala/u04/datastructures/task1/ScalaTestPropertiesTest.scala) file. Unlike
ScalaCheck, ScalaTest is used to develop traditional *Unit Testing* classes, to develop very detailed and specific tests
for specific functions of applications, where, when we say **Unit Tests**, we mean tests designed to verify a singular
function or behaviour of a piece of code.

The main characteristics of the library are:
* **Specific Tests**, meaning that test are manually defined for each single case, therefore bringing the idea of
specificity to the test itself, in the code this can be seen in the naming (and body) of a test like 
`"The sum remains the same even after filtering"`;
* **Multiple Executions**, meaning that to simulate multiple inputs, ScalaTest can re-run the same test multiple times,
* with different data, *but* the data has to be generated and handled manually;
* **Explicit Assertions**, meaning that the assertions that can be made (through the keyword `assert`), are used to
verify specific conditions inside the tests, like the comparison between the results of the two sums tested.

So all in all both libraries have their pros (and cons), and putting them side by side we can generally assert that:
* When it comes to `Testing`, **ScalaTest** concentrates on delivering specific and detailed test cases, where inputs are
handled mostly manually; meanwhile **ScalaCheck**, is based on more general properties and also generates inputs casually for
tests, to tests these properties on a wide range of scenarios;
* For `Input Data`, **ScalaTest** requires to manually define inputs or simulate them through specific libraries and code
implementations; **ScalaCheck** automates the generation of input data, to explore a wider range of cases;
* For the `Tests Goals`, **ScalaTest** it's mainly used for Unit Testing, therefore testing specific single case 
behaviour of applications in a very detailed manner; meanwhile **ScalaCheck** aims to test properties that are generalized
and invariant to time, on a wide range of scenarios.

## Task 2: ADT-VERIFIER

Define an ADT for sequences with some operations: map/filer/flatMap/fold/reduce/.... Turn into a Scala trait, and 
define ScalaCheck properties capturing axioms 1-to-1. Develop two implementations (Cons/Nil and by Scala List). Engineer 
tests such that you can easily show they both satisfy those properties

### Work Done:

As expressed in the task description, two different Scala objects have been developed to define two different 
ADT implementations for Sequences, with also the implementation of the map, filter, flatmap, leftfold, rightfold, reduce
and concat operations. The objects developed are 
[CustomSequenceCons.scala](src/main/scala/u04/adts/task2/CustomSequenceCons.scala) for the Cons/Nil implementation, and
[CustomSequenceList.scala](src/main/scala/u04/adts/task2/CustomSequenceList.scala) for the native Scala List implementation.

#### CustomSequenceCons

For this implementation, a custom structure has been used to represent Sequences, through the use of the Cons/Nil
constructs. The object itself was mostly developed following what already we had done during class in order to fit what
we had seen for the Advanced Programming specifications classes.
The re-iterate how it works, the main constructs are:
* **Cons(head, tail)**, which represents a Sequence that has an element in the beginning, ***head***, and a ***tail*** possibly
containing another element, or ***Nil***.
* **Nil**, which represents an empty Sequence, and is used at the end of Sequences.

The operation in this file have been developed following classic explorative approaches to Sequences, which is the main
advantage over the other implementation, as it is easily possible to modify and insert new custom operations.

#### CustomSequenceList

Meanwhile, for this implementation the native Scala List library has been used to represent a Sequence, both for its
elements, and also its operations, as their taken directly from the List interface.

Obviously the advantage in this solution stands in the fact that it's re-using the List library to perform its operations.

#### Testing the ADTs created
As once again stated in the task, a Scala test file, called 
[SequencePropertiesTaskTwo.scala](src/test/scala/u04/task2/SequencePropertiesTaskTwo.scala), has been developed to 
capture and test the axioms of both implementation using ScalaCheck and its properties, with all the benefits mentioned 
in the task before in using the library. Below I will list all the tested operations, with the paired axioms in order to
test the properties properly, considering that I developed two axioms to test per operation, to test basic functionalities
ofr the Sequence structures base cases, which mostly included a "normal" case with a proper structure, or Nil cases.

* ***Map*** Operation:
  * **Cons Axiom**: map(cons(h, t), f) = cons(f(h), map(f, f))
  * **Nil Axiom**: map(nil, f) = nil
The included tests `Map Axiom => Cons` and `Map Axiom: Nil` tested that, considering both implementation of the sequences:
* Firstly, that applying the mapping of a function on a sequence that is not empty, would properly apply the function 
on each member of the sequence;
* and secondly in the case of having empty sequences, that they would return an empty sequence.


* ***Filter*** Operation:
  * **Cons Axiom**: filter(cons(h, t), p) = if (p(h)) cons(h, filter(t, p)) else filter(t, p)
  * **Nil Axiom**: filter(nil, p) = nil
The included tests `Filter Axiom => Cons` and `Filter Axiom: Nil` tested that, considering both implementation of the sequences:
* Firstly, that applying the predicate of a function on a sequence that is not empty, would properly apply the function
on the sequence, properly filtering the wanted elements;
* and secondly in the case of having empty sequences, that they would return an empty sequence.


* ***FlatMap*** Operation:
  * **Cons Axiom**: flatMap(cons(h, t), f) = concat(f(h), flatMap(t, f))
  * **Nil Axiom**: flatMap(nil, f) = nil
The included tests `FlatMap Axiom => Cons` and `FlatMap Axiom: Nil` tested that, considering both implementation of the sequences:
* Firstly, that applying the mapping of a function on a sequence that is not empty, would properly apply the function
  on the sequence, making sure that the concatenation of the sequences was correct;
* and secondly in the case of having empty sequences, that they would return an empty sequence.


* ***Fold*** Operation:
  * **Cons Axiom**: fold(cons(h, t), z)(f) = fold(t, f(z, h))
  * **Nil Axiom**: fold(nil, z)(f) = z 
The included tests `FoldLeft Axiom => Cons`, `FoldRight Axiom => Cons`, `FoldLeft Axiom => Nil` and `FoldRight 
Axiom: Nil` tested that, considering both implementation of the sequences:
* Firstly, that applying the mapping of a function on a sequence that is not empty, would properly apply the folding 
function on the sequence;
* and secondly in the case of having empty sequences, the first element of the fold.


* ***Concat*** Operation:
    * **Cons Axiom**: concat(cons(h, t), l) = cons(h, concat(t, l))
    * **Nil Axiom**: concat(nil, l) = l 
The included tests `Concat Axiom => Cons` and `Concat Axiom: Nil` tested that, considering both implementation of the sequences:
* Firstly, that applying the mapping of a function on a sequence that is not empty, would properly apply the function
  on the sequence, making sure that the concatenation of the two sequences was correct;
* and secondly in the case of having empty sequences, that they would return the given sequence to concatenate.


* ***Reduce*** Operation:
  * **Cons Axiom**: reduce(cons(h, t), f) = t.foldLeft(h)(f)
    The included test `Concat Axiom => Cons`  tested that, considering both implementation of the sequences:
* Firstly, that applying the mapping of a function on a sequence that is not empty, would properly apply the function
  on the sequence, therefore reducing the sequence to one single element according to the provided function;
For the Nil axiom, I didn't provide any tests, as it is "illegal" to perform a reduce function on a sequence with no
elements in it.


  
## Task 3: MONAD-VERIFIER

Define ScalaCheck properties for Monad axioms, and prove that some of the monads given during the lesson actually 
satisfy them. Derive a general methodology to structure those tests.

### Work Done:

As asked per task, I've developed a class called 
[MonadProperties.scala](src/test/scala/u04/monads/task3/MonadProperties.scala) inside the test source directory, which,
through the use of ScalaChecks, tests the most important axioms related to Monads inside Scala, which are the verification
of 3 main monadic laws:
* The *Left Identity*;
* The *Right Identity*;
* *Associativity*.

All of this associated with the Monadic implementations of Optionals, to implement similar tests to the ones we've seen
during class for Sequences.




## Task 4: MVC-ENGINEER

Start with the given MVC monadic application: extend it to realise a more complex application, e.g. the DrawNumberGame. 
Of course, be fully monadic. Up to which complexity can one reach? Could we come up with a simple MVC application with 
reactive GUI, and/or could a GameLoop be framed into a fully monadic application?

### Work Done:

The task ha been implemented inside the files 
[DrawNumberGame_MVC.scala](src/main/scala/u04/monads/task4/DrawNumberGame_MVC.scala) and 
[DrawNumberGameState.scala](src/main/scala/u04/monads/task4/DrawNumberGameState.scala), where, as requested, it 
implements the simple game of "Draw Number", where the computer draws a number between 0 and 100, and the User has to 
guess in the least amounts of tries, through the feedback that the game gives back after each try. The implementation
uses Monads to manage the state of the game and reactive graphical interface, the GUI, by integrating other notions of
functional programming learned during the class, to manage the States and events. 

#### Components:

The various components used throughout the implementation are:

* The **DrawNumberGameState**, where a trait named `DrawNumberGameState` is implemented to define the operations to manage
the random number to guess. The operations are the one to initialize the number to guess (`initialGuesser()`), create a 
new number (`reset()`) and verify the number inputted by the User (`checkGuess()`). These operations are modeled as 
Monads of State `State[Guesser, A]`, where `Guesser` is an Int type declared inside the Game State which represents the 
number to guess, to better manage the flow of the data inside the game.

* The **windowCreation**, where the method with the same name configures and builds the games window using Monads, more
specifically through the State `State[Window, A]`, to let the interface be reactive to GUI changes.

* And the **States Casting**, more specifically in order to implement the previously stated State transformations, two
extension methods have been defined that transform the State states in a `State[(Window, Guesser), A]` state. This
is a Monadic approach that permits to manage both the game state and the UI in an easier way.


Finally, all in all, the Monadic approach used could definitely be used to extend even more the present game loop, to a 
more complex application, still completely Monadic, but, one thing to consider is that, if one were to make a more 
advanced game/application, the increased complexity could make the project very difficult to maintain and manage,
especially when it comes to synchronizing complex states or heavy graphical interfaces, though obviously this is paid 
off by having much more clarity and modularity in developing the application, including managing the states and the 
sequential operations.





## Task 5: ADVANCED-FP-LLM


LLMs/ChatGPT can arguably help in write/improve/complete/implement/reverse-engineer ADT specifications, ADTs in Scala, 
and monadic specifications. Check if/whether this is the case.

### Work Done:

Just like with previous LLMs testing task in previous labs, I tested the ChatGPT LLM to help write, improve, complete, 
implement and/or reverse-engineer ADT specifications, and I also tested CoPilot and Llama on my own out of curiosity, but
not reporting the findings, since they were not as good as GPT.

For all the tasks, I used, as previously stated, the available `Search` and `Reason` functionalities made available from
OpenAI with GPT to perform all of these tasks.

* **write**: The prompt I used for a write task is as follows:

`Hello, I need help with a task, could you pretty pretty please help write some example code in Scala, with the 
objective of writing ADT and Monadic specifications, again in Scala, what to represent with these specifications is up 
to you.
`

What GPT came up with was an example of the representation of, I quote, a "file processing pipeline, using a sealed ADT to 
represent file contents or errors, then define a generic `Monad` type class and provide and instance to chain processing
steps".

To save space on the README, the material can be found inside the files 
[FileResult.scala](src/main/scala/u04/task5/GPT/write/FileResult.scala), 
[Monad.scala](src/main/scala/u04/task5/GPT/write/Monad.scala) and
[TestFileResult.scala](src/main/scala/u04/task5/GPT/write/TestFileResult.scala), where the main idea, for GPT, was to:

1. **Defining an ADT for File Processing**, this is done with the trait inside `FileProcessing`, to represent the result
of reading a file as either FileContent or an Error;
2. **Specifying a Monad Type Class in Scala**, done inside the `Monad` file in the same package, for the Monad to 
abstract over types `F[_]` that support two methods, `pure` and `flatmap`;
3. **Providing a Monad Instance for Our ADT**, again inside the `Monad` file, give FileResult a monadic interface so 
that successive operations stop on the first error;
4. And finally **Composing File Processing Steps as a Monad**, in the `TestFileResult` file, to chain steps using a 
for‐comprehension.

Unfortunately the code "out of the box" didn't work, and it didn't even after a few tries of trying to fix it with 
different prompts, that being said the code produced looks good and is usable if corrected manually, GPT unfortunately
couldn't though.

* **improve**: What I did to test the improvement, is to give it the code inside 
[SequenceADT.scala](src/main/scala/u04/adts/SequenceADT.scala), and then asked GPT to improve it using the following 
prompt:

`
Hello, I need help with a task, could you pretty pretty please help me improve some code in Scala, with the objective 
of writing ADT and Monadic specifications, again in Scala, given that the initial code is implemented as an ADT
sequence, as follows: ...
`

The result given was this piece of code:

```scala 3
// SequenceADT.scala

package data

// 1) ADT Definition
// -----------------
enum Sequence[+A]:
  case Cons(head: A, tail: Sequence[A])
  case Nil()

object Sequence:
  // smart constructors
  def cons[A](hd: A, tl: Sequence[A]): Sequence[A] = Cons(hd, tl)
  def nil[A]: Sequence[A]                   = Nil()

  // build from Scala Seq
  def fromSeq[A](xs: Seq[A]): Sequence[A] =
    xs.foldRight(nil[A])((a, acc) => cons(a, acc))

  // 2) Core operations as methods on the ADT
  // -----------------------------------------
  extension [A](s: Sequence[A])
    def map[B](f: A => B): Sequence[B] = s match
      case Cons(h, t) => Cons(f(h), t.map(f))
      case Nil()      => Nil()

    def flatMap[B](f: A => Sequence[B]): Sequence[B] = s match
      case Cons(h, t) =>
        // concat the mapped head onto the flatMap of tail
        f(h) ++ t.flatMap(f)
      case Nil()      => Nil()

    def ++[A2 >: A](other: Sequence[A2]): Sequence[A2] = s match
      case Cons(h, t) => Cons(h, t ++ other)
      case Nil()      => other

    def toList: List[A] =
      @annotation.tailrec
      def loop(rem: Sequence[A], acc: List[A]): List[A] = rem match
        case Cons(h, t) => loop(t, h :: acc)
        case Nil()      => acc.reverse

      loop(s, Nil)

  // 3) Monad Type-Class & Instance
  // -------------------------------
  trait Monad[F[_]]:
    def pure[A](a: A): F[A]
    def flatMap[A,B](fa: F[A])(f: A => F[B]): F[B]
    extension [A](fa: F[A])
      def map[B](f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))
      def flatMap[B](f: A => F[B]): F[B] = flatMap(fa)(f)

  object Monad:
    def apply[F[_]](using m: Monad[F]): Monad[F] = m

  // Sequence’s Monad instance
  given sequenceMonad: Monad[Sequence] with
    def pure[A](a: A): Sequence[A]           = cons(a, nil)
    def flatMap[A,B](fa: Sequence[A])(f: A => Sequence[B]): Sequence[B] =
      fa.flatMap(f)


  // 4) Monadic Law Specs (documentation)
  // ------------------------------------
  // For all values a: A, functions f: A => Sequence[B], g: B => Sequence[C]:
  //
  //  1. Left identity:   pure(a).flatMap(f) ≡ f(a)
  //  2. Right identity:  m.flatMap(pure)  ≡ m
  //  3. Associativity:   m.flatMap(f).flatMap(g) ≡ m.flatMap(a => f(a).flatMap(g))
  //
  // You can test these with a property-based testing tool like ScalaCheck:
  //
  //   import org.scalacheck.Prop.forAll
  //   forAll { (a: Int, f: Int => Sequence[Int]) =>
  //     Sequence.Monad[Sequence].pure(a).flatMap(f) == f(a)
  //   }
  //
  //   forAll { (m: Sequence[Int]) =>
  //     m.flatMap(Sequence.Monad[Sequence].pure) == m
  //   }
  //
  //   forAll { (m: Sequence[Int], f: Int => Sequence[Int], g: Int => Sequence[Int]) =>
  //     m.flatMap(f).flatMap(g) ==
  //       m.flatMap(a => f(a).flatMap(g))
  //   }
```

That overall seems to be written well, but it has problems when it comes to actually compiling, since the part related
to the Monads has some problems for some arguments written, a trend that also appeared with the previous `write` task.

* **complete/implement**: When it comes to complete/implement I actually used again the previous `SequenceADT` code
already present in the lab as an example, to use for GPT to properly finish it, using the following prompt:

``` scala 3
Hello, I need help with a task, could you pretty pretty please help me complete/implement some code in Scala, with the 
objective of writing ADT and Monadic specifications, again in Scala, given that the code that I need to implement is 
a custom ADT Sequence data structure, which for now is implemented as follows:
object SequenceADT:
// implementation of data structure: fully hidden
private enum SequenceImpl\[E]:
case Cons(head: E, tail: Sequence\[E])
case Nil()
```

And the code produced as a result is as follow:
```scala 3
/*
  Custom Sequence ADT and Monad Specification in Scala
*/

// ADT definition for a sequence of elements of type A 
sealed trait Sequence[+A] {
  /*** Basic operations ***/
  def isEmpty: Boolean
  def head: A
  def tail: Sequence[A]

  /*** Functor operations ***/
  def map[B](f: A => B): Sequence[B]

  /*** Monad operations ***/
  def flatMap[B](f: A => Sequence[B]): Sequence[B]
}

// Companion object with constructors and Monad instance
object Sequence {
  // Constructors
  def empty[A]: Sequence[A] = NilSeq
  def apply[A](as: A*): Sequence[A] = if (as.isEmpty) NilSeq else ConsSeq(as.head, apply(as.tail: _*))

  /*** Monad typeclass ***/
  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  }

  // Monad instance for Sequence
  implicit val sequenceMonad: Monad[Sequence] = new Monad[Sequence] {
    def pure[A](a: A): Sequence[A] = ConsSeq(a, NilSeq)
    def flatMap[A, B](ma: Sequence[A])(f: A => Sequence[B]): Sequence[B] = ma.flatMap(f)
  }

  // Internal data constructors hidden from public API
  private case object NilSeq extends Sequence[Nothing] {
    def isEmpty = true
    def head = throw new NoSuchElementException("Nil.head")
    def tail = throw new NoSuchElementException("Nil.tail")

    def map[B](f: Nothing => B): Sequence[B] = this
    def flatMap[B](f: Nothing => Sequence[B]): Sequence[B] = this
  }

  private final case class ConsSeq[A](head: A, tail: Sequence[A]) extends Sequence[A] {
    def isEmpty = false

    def map[B](f: A => B): Sequence[B] = ConsSeq(f(head), tail.map(f))

    def flatMap[B](f: A => Sequence[B]): Sequence[B] = {
      // f(head) ++ tail.flatMap(f)
      def concat(xs: Sequence[B], ys: Sequence[B]): Sequence[B] = xs match {
        case NilSeq       => ys
        case ConsSeq(h, t) => ConsSeq(h, concat(t, ys))
      }
      concat(f(head), tail.flatMap(f))
    }
  }
}
```

The LLM produced a pretty good extended version of the Sequence file compared to the original one, throwing in the general
ADT specifications AND the Monads one, in a pretty good example.


* **reverse-engineer**: Finally for the reverse-engineering, I gave GPT the identities specifications present inside the 
file [MonadProperties.scala](src/test/scala/u04/monads/task3/MonadProperties.scala) as an example to reverse engineer the
original specs. The prompt I gave GPT was:

```scala 3
Hello, I need help with a task, could you pretty pretty please help me reverse-engineer some code in Scala, with the 
objective of reverse-engineer and then writing ADT and Monadic specifications, again in Scala, given that the code that 
I have available to reverse engineer is related to an ADT of a custom implemented Sequence, and the code is as following:
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
```
GPT actually produced a pretty good Scala file which included basically the characteristics of the original Optionals,
the file produced is inside [Optional.scala](src/main/scala/u04/task5/GPT/reverseEngineer/Optional.scala).

#### **Final Thoughts:**

So all in all GPT is revealing itself to be an excellent instrument to use when it comes to writing code, and even 
problem-solving, it is a bit cumbersome to use though through the main web interface.

In comparison, Copilot, even though I used the GPT-4o model for tests, it revealed itself to not be great, especially 
because it infers heavily on the already existing code in the project, which sometimes, especially for writing something
from the ground up, is NOT useful, it's actually detrimental to the results given.

Finally, for Llama I used Llama3.1, just like last lab task, and it performed heavily badly compared to both of them,
giving out results that were not working, and were not related to what I was asking.

So as much as ChatGPT is a bit bothersome to use, I'd rely on it heavily if I had to use a model for help.