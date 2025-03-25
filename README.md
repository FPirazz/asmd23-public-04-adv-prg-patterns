# 04Lab - Verified Specification, ADTs, type classes, and monads

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