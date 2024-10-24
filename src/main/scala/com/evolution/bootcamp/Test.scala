package com.evolution.bootcamp

object Test extends App {

  val s = "h1BiLqO5bHiSVmDWiDbYfRnFAlYuP8He4LqXjLofGxairkvqc7tVZKFEW4UnQ7LVcdxPNoDtySvEHsoH9dgNIrgx6QK"
  println(s.length)

  val list = List(2, 4, 1, 5)

  println(list.sorted)
  println(list.sorted.reverse)

  case class Car(model: String, year: Int, engine: String)
  val cars: List[Car] = List(Car("BMW", 12, "P"), Car("Audi", 13, "D"), Car("Nisan", 14, "P"))

  println(cars.sortBy(_.engine))
  println(cars.sortBy(_.engine).reverse)

  val cars2 = List(Car("BMW", 12, "P"), Car("Opel", 14, "D"), Car("Audi", 13, "D"), Car("Opel", 15, "E"), Car("Audi", 16, "K"))
  println(cars2)
  println(cars2.sortBy(_.model))
  println(cars2.sortBy(_.model).reverse)

  import cats.Functor

  type Okk[A] = Either[String, A]
}
