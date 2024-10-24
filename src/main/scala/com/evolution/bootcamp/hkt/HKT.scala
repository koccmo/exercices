package com.evolution.bootcamp.hkt

import cats.Functor

object HKT {

  /** A class that takes type parameter called Type Constructor
   */
  object TypeConstructors {

    sealed abstract class List[+A]

    sealed abstract class Option[+A]

    sealed abstract class Either[+A, +B]

    trait Monad[F[_]]

    final case class Kleisli[F[_], -A, B](run: A => F[B])

    trait Meta[F[_[_]]]

    // Exercise 1. Build a concrete types using type constructors above

  }

  /** A kind is a type of Type constructor
   */
  object Kind {

    /** Exercise 2. Discover kinds of [[TypeConstructors]]
     */

    /** Exercise 3. Define levers / orders of kinds from Exercise 2.
     */

    /** Exercise 4. Using `sbt`, determine kind of [[cats.data.EitherK]] and [[cats.Id]]
     */

  }

  // Let's define our own type similar to Option

  sealed trait Maybe[A]

  object Maybe {
    case object Empty                 extends Maybe[Nothing]
    case class Something[A](value: A) extends Maybe[A]
  }

  def useMaybe[A](b: Maybe[A]) = b

  useMaybe(Maybe.Something(1)) // wait, we can't pass Empty? How is that?

  object Variance {

    /** Scala has 3 variance annotations:
     * + indicates that subtyping is co- variant (flexible) in that parameter
     * - which indicates contravariant subtyping.
     * (no annotation) invariant
     */

    /** Invariance
     *
     * Means we expect exact type as it was specified
     */

    /** Covariance
     * C[+T]
     *
     * A    extends (<-)   B
     * C[A] extends (<-) C[B]
     */

    /** Exercise 5. Show that [[Maybe]] is invariant
     */

    /** Exercise 6. Make Maybe[T] covariant
     */

    /** Exercise 7. Introduce ADT hierarchy and use it together with covariant [[Maybe]]
     */

    /** Exercise 8. Show that Scala [[List]] is covariant
     */
    val covariantList: List[_] = ???

    /** Contravariance
     * C[-T]
     *
     * A    extends (<-)   B
     * C[B] extends (->) C[A]
     */

    /** Exercise 9. Implement [[VetClinic]]. Extend Animal hierarchy with Puppy and Kitten
     */

    /** Exercise 10. Name more examples where contravariance make sense.
     */

    /** Exercise 11. Check variance annotations of [[Function1]]
     */

    /** Exercise 12. Implement simple data structure Queue
     */

    class Queue[+T](init: List[T]) {
      def add[B >: T](x:  B): Queue[B] = new Queue[B](init :+ x)
      def get: T = init.head
    }
    // lower bound
    // upper bound

  }

  object TypeLambdas {


    /** Exercise 13. Implement instance of [[Functor]] for Map[K, V]
     */

    def mapFunctor[K]: Functor[({type StringKeyMap[V] = Map[K, V]})#StringKeyMap] = new Functor[Map[K, *]] {
      override def map[A, B](fa: Map[K, A])(f: A => B): Map[K, B] = ???
    }

    def mapFuctor2[K]: Functor[({ type EitherA[V] = Either[K, V]})#EitherA] = new Functor[Either[K, *]] {
      override def map[A, B](fa: Either[K, A])(f: A => B): Either[K, B] = ???
    }

    def mapFuctor3[K]: Functor[Map[K, *]] = new Functor[Map[K, *]] {
      override def map[A, B](fa: Map[K, A])(f: A => B): Map[K, B] = ???
    }
  }

  object Materials {
    println("""
              |Programming in Scala, Ordersky, Chapter 19
              |Rock the JVM:
              | - https://www.youtube.com/watch?v=EFK4UC5PONw
              | - https://www.youtube.com/watch?v=b1ftkK1zhxI
              |
              |Functional programming in Scala (Red book)
              |
              |Generics of a Higher Kind, paper, Adriaan Moors Frank Piessens
              | - http://adriaanm.github.io/files/higher.pdf
              |
              | Kind projector plugin: https://github.com/typelevel/kind-projector
              |""".stripMargin)

  }

  class Animal
  case class Dog(name: String) extends Animal

  val bobi: Dog = Dog("Bobi")
  val alex: Dog = Dog("Alex")

  val listOfAnimal: List[Animal] = List(bobi, alex)
  val dogs: List[Dog] = List(alex, bobi)
  val allAnimals = dogs :: listOfAnimal

  trait Vet[-T] {
    def heal(animal: Animal): Boolean
  }

  def giveMeVet(): Vet[Dog] = new Vet[Animal]{
    override def heal(animal: Animal): Boolean = {
      println("All will be all right!")
      true
    }
  }

  val myVet = giveMeVet()
  val chackie: Dog = Dog("Chackie")
  val dogVet = myVet.heal(chackie)

  case class Cat(name: String) extends Animal
  val cat: Cat = Cat("Red")
  val catVet = giveMeVet().heal(cat)

  class Pappy(override val name: String) extends Dog(name)

  val pappy: Pappy = new Pappy("Hope")

  val pappyVet = myVet.heal(pappy)
}
