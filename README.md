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
* and secondly in the case of having empty sequences, the first element of the fold. // TODO: REVISION THIS


* ***Concat*** Operation:
    * **Cons Axiom**: concat(cons(h, t), l) = cons(h, concat(t, l))
    * **Nil Axiom**: concat(nil, l) = l 
The included tests `Concat Axiom => Cons` and `Concat Axiom: Nil` tested that, considering both implementation of the sequences:
* Firstly, that applying the mapping of a function on a sequence that is not empty, would properly apply the function
  on the sequence, making sure that the concatenation of the two sequences was correct;
* and secondly in the case of having empty sequences, that they would return the given sequence to concatenate.

// TODO: ADD REDUCE OPERATION


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