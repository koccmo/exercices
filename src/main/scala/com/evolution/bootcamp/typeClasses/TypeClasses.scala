package com.evolution.bootcamp.typeClasses

import com.evolution.bootcamp.typeClasses.TypeClasses.FPJsonSugared.Jsonable
import com.evolution.bootcamp.typeClasses.TypeClasses.HashCodeTask

object TypeClasses {
  final case class Json(s: String) { // simplified representation of JSON
    override def toString: String = s
  }

  object OOPJson extends App {

    trait Jsonable {
      def toJson: Json
    }

    def prettyPrint(jsonable: Jsonable): Unit = println(jsonable.toJson)

    final case class Game(id: Int) extends Jsonable {
      def toJson: Json = Json(s"""{"id": $id}""")
    }

    prettyPrint(Game(123))
  }

  // What if Game is defined in a library?

  object FPJson extends App {

    trait Jsonable[A] {
      def toJson(entity: A): Json
    }

    def prettyPrint[A](a: A)(implicit jsonable: Jsonable[A]): Unit = println(jsonable.toJson(a))

    final case class Game(id: Int)

    implicit val gameJsonable: Jsonable[Game] = new Jsonable[Game] {
      def toJson(game: Game): Json = Json(s"""{"id": ${game.id}}""")
    }

    prettyPrint(Game(123))

    object InstancesTask {

      final case class Player(id: Int, name: String)

      implicit val playerJsonable: Jsonable[Player] = new Jsonable[Player] { def toJson(player: Player): Json = Json(s"""{"id": ${player.id} , "name": ${player.name}""")}

      implicit val intJsonable: Jsonable[Int] = (int: Int) => Json(s"""{"int": ${int}""")

      implicit val optionIntJsonable: Jsonable[Option[Int]] = new Jsonable[Option[Int]] {
        def toJson(entity: Option[Int]): Json = entity.fold(Json("""{"number": 0}"""))(num => intJsonable.toJson(num))}
    }

    object GenericImplicitsTask {

      implicit def optionJsonable[A](implicit jsonableA: Jsonable[A]): Jsonable[Option[A]] =
        new Jsonable[Option[A]] {
          def toJson(entity: Option[A]): Json =
            entity match {
              case Some(value) => jsonableA.toJson(value)
              case None        => Json("null")
            }
        }

      implicit def listJsonable[A](implicit jsonableA: Jsonable[A]): Jsonable[List[A]] =
        new Jsonable[List[A]] {
          def toJson(entity: List[A]): Json = Json(entity.map(jsonableA.toJson).mkString("[", ",", "]"))
        }
    }

    object SingleAbstractMethod {

      implicit val before: Jsonable[Game] = new Jsonable[Game] {
        def toJson(game: Game): Json = Json(s"""{"id": ${game.id}}""")
      }

      implicit val after: Jsonable[Game] = (game: Game) => Json(s"""{"id": ${game.id}}""")
    }

    object ContextBound {

      def prettyPrintBefore[A](a: A)(implicit jsonable: Jsonable[A]): Unit = println(jsonable.toJson(a))

      def prettyPrintAfter[A: Jsonable](a: A): Unit = println(implicitly[Jsonable[A]].toJson(a))
    }

    object Summoner {

      object Jsonable {
        def apply[A](implicit instance: Jsonable[A]): Jsonable[A] = instance
      }

      def prettyPrintBefore[A: Jsonable](a: A): Unit = {
        val jsonable = implicitly[Jsonable[A]]
        println(jsonable.toJson(a))
      }

      def prettyPrintWithSummoner[A: Jsonable](a: A): Unit = Jsonable[A].toJson(a)
    }

    object Syntax {

      object Jsonable {
        def apply[A](implicit instance: Jsonable[A]): Jsonable[A] = instance
      }

      def prettyPrintBefore[A: Jsonable](a: A): Unit = println(Jsonable[A].toJson(a))

      object JsonableSyntax {
        implicit class JsonableOps[A](x: A) {
          def toJson(implicit jsonable: Jsonable[A]): Json = jsonable.toJson(x)
        }
      }

      import JsonableSyntax._
      def prettyPrintWithSyntax[A: Jsonable](a: A): Unit = a.toJson
    }
  }

  object FPJsonSugared extends App {

    // Typeclass Definition
    trait Jsonable[T] {
      def toJson(entity: T): Json
    }

    // Typeclass Summoner
    object Jsonable {
      def apply[A](implicit instance: Jsonable[A]): Jsonable[A] = instance
    }

    // Typeclass Syntax
    object JsonableSyntax {
      implicit class JsonableOps[A](val x: A) extends AnyVal {
        def toJson(implicit jsonable: Jsonable[A]): Json = jsonable.toJson(x)
      }
    }

    final case class Game(id: Int)

    // Typeclass Instance
    implicit val gameJsonable: Jsonable[Game] = (game: Game) => Json(s"""{"id": ${game.id}}""")

    import JsonableSyntax._

    // This method can be called only if Typeclass Instance of Jsonable exists (and visible) for A
    def prettyPrint[A: Jsonable](a: A): Unit = println(a.toJson)

    prettyPrint(Game(123))
  }

  //object FPJsonMacros extends App {
  //  import simulacrum._
  //
  //  @typeclass trait Jsonable[T] {
  //    def toJson(entity: T): Json
  //  }
  //
  //  import Jsonable.ops._
  //  def prettyPrint[A: Jsonable](a: A): Unit = println(a.toJson)
  //
  //  final case class Game(id: Int)
  //
  //  implicit val gameJsonable: Jsonable[Game] = (game: Game) => Json(s"""{"id": ${game.id}}""")
  //
  //  prettyPrint(Game(123))
  //}

  object HashCodeTask {

    trait HashCode[A] { // Turn me into TypeClass
      def hash(entity: A): Int
    }

    object HashCode {
      def apply[A](implicit instance: HashCode[A]): HashCode[A] = instance
    }

    implicit class HashCodeOps[A: HashCode](x: A) {
      def hash(implicit hashCode: HashCode[A]): Int = hashCode.hash(x)
      // Implement syntax so I could do "abc".hash
    }

    // Implement an instance for String
    implicit val stringHashCode: HashCode[String] = (string: String) => string.hashCode
    // Prove that I'm working
  }

}
object Opp extends App {
  import HashCodeTask._
  println("abc".hash)
}