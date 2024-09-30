package com.evolution.bootcamp.functions

import java.time.Instant
import scala.util.Try

object Functions {

  // Exercise. Implement `isEven` method that checks if a number is even.
  def isEven(n: Int): Boolean = n % 2 == 0

  // Exercise. Implement `isEvenFunc` function that behaves exactly like `isEven` method.
  val isEvenFunc: Int => Boolean = _ % 2 == 0

  // Exercise. Implement `isEvenMethodToFunc` function by transforming `isEven` method into a function.
  val isEvenMethodToFunc: Int => Boolean = isEven

  // One interesting aspect of functions being traits is that we can subclass function types.
  trait MyMap[K, V] extends (K => V)

  // Question. What function should we extend to check if an element belongs to a set?
  trait MySet[A] extends (A => Boolean)

  // Question. What function should we extend to return a value by its index?
  trait MySeq[A] extends (Int => A)

  // Exercise. Implement `mapOption` function without calling `Option` APIs.
  def mapOption[A, B](option: Option[A], f: A => B): Option[B] = option match {
    case Some(value) => Some(f(value))
    case None        => None
  }

  type ??? = Nothing

  // Exercises. Convert the following functions into pure functions. Replace ??? with correct return types.

  def parseDate(s: String): Instant             = Instant.parse(s)
  def parseDatePure(s: String): Option[Instant] = Try(Instant.parse(s)).toOption

  def divide(a: Int, b: Int): Int             = a / b
  def dividePure(a: Int, b: Int): Option[Int] = Try(divide(a, b)).toOption

  def isAfterNow(date: Instant): Boolean                   = date.isAfter(Instant.now())
  def isAfterNowPure(date: Instant, now: Instant): Boolean = date.isAfter(now)

  case class NonEmptyList[T](head: T, rest: List[T])
  def makeNonEmptyList[T](list: List[T]): NonEmptyList[T] = {
    if (list.isEmpty) println("Error: list must not be empty")
    NonEmptyList(list.head, list.tail)
  }

  def makeNonEmptyListPure[T](list: List[T]): Option[NonEmptyList[T]] =
    if (list.isEmpty) None else Some(NonEmptyList(list.head, list.tail))

}
