package part1_recap

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaRecap extends App {

  val aCondition: Boolean = false

  def myFunction(x: Int) = {
    // code here
    if (x > 4) 42 else 65
  }

  // instructions vs expressions
  // type + type inference

  // OO features of Scala
  class Animal
  trait Carnivore { // we can define methods without implement them - the ultimate abstract classes
    def eat(a: Animal): Unit
  }

  object Carnivore

  // generics
  abstract class MyList[+A]

  // method notation
  1 + 2 // infix notation
  1.+(2)

  // FP
  val anIncrementer: Int => Int = (x: Int) => x + 1
  anIncrementer(1) /// using apply method

  // Higher Order Functions: map, flatMap and filter
  List(1, 2, 3).map(anIncrementer)
  // List(2, 3, 4)
  // for-comprehensions - syntactic sugar for map, flatMap and filter
  // Monads: Option, Try

  // Pattern Matching
  val unknown: Any = 2
  val order = unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  try {
    // code that can throw and exception
    throw new RuntimeException
  } catch {
    case e: Exception => println("I caught an error")
  }

  /**
   * Scala advanced
   */

  // multithreading

  import scala.concurrent.ExecutionContext.Implicits.global // manager for the Thread
  val future = Future {
    // long computation here
    // executed on SOME other thread
    42
  }
  // map, flatMap, filter, + other niceties e.g. recover/recoverWith

  future.onComplete{
    case Success(value) => println(s"I found the meaning of life: $value")
    case Failure(exception) => println(s"I found $exception")
  } // it is executed on Some Thread

  val partialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65
    case _ => 999
  }
  // based on pattern matching

  // type aliases
  type AkkaReceive = PartialFunction[Any, Unit]
  def receive: AkkaReceive = {
    case 1 => println("Hello")
    case _ => println("confused...")
  }

  // Implicits:
  implicit val timeout = 3000
  def setTimeout(f: () => Unit)(implicit timeout: Int) = f()
  setTimeout(() => println("timeout")) // other arg list injected by the compiler

  // conversions
  //1) implicit methods
  case class Person(name: String) {
    def greet: String = s"Hi my name is $name"
  }

  implicit def fromStringToPerson(name: String): Person = Person(name)
  "Peter".greet
  // fromStringToPerson("Peter").greet

  // 2) implicit classes
  implicit class Dog(name: String) {
    def bark = println("bark!")
  }
  "lassie".bark
  // new Dog("Lassie").bark

  // implicit organizations
  // local scope
  implicit val numberOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  List(1, 2, 3).sorted //(numberOrdering) List(3, 2, 1)

}
