package com.evolution.bootcamp.adt

object AlgebraicDataTypes {

  // Exercise. Create a smart constructor for `GameLevel` that only permits levels from 1 to 80 (inclusive).
  final case class GameLevel private (value: Int) extends AnyVal
  object GameLevel {
    def create(value: Int): Option[GameLevel] = if (value >= 1 && value <= 80) Some(GameLevel(value))
    else None
  }


  sealed abstract case class Time private (hour: Int, minute: Int)
  object Time {
    def create(hour: Int, minute: Int): Either[String, Time] = {
      if (hour < 0 && hour > 24) Left("Invalid hour value" )
      else if (minute < 0 && minute > 59) Left("Invalid minute value")
      else Right(new Time(hour, minute){})
    }
  }

  // Exercise. Implement the smart constructor for `Time` that only permits values from 00:00 to 23:59 and
  // returns "Invalid hour value" or "Invalid minute value" strings in `Left` when appropriate.


  // The power of sum and product types is unleashed when they are combined together. For example, consider a
  // case where multiple different payment methods need to be supported. (This is an illustrative example and
  // should not be considered complete.)
  final case class AccountNumber(value: String) extends AnyVal
  final case class CardNumber(value: String)    extends AnyVal
  final case class ValidityDate(month: Int, year: Int)
  sealed trait PaymentMethod
  object PaymentMethod {
    final case class BankAccount(accountNumber: AccountNumber)                      extends PaymentMethod
    final case class CreditCard(cardNumber: CardNumber, validityDate: ValidityDate) extends PaymentMethod
    final case object Cash                                                          extends PaymentMethod
  }

  import PaymentMethod._

  final case class PaymentStatus(value: String) extends AnyVal
  trait BankAccountService {
    def processPayment(amount: BigDecimal, accountNumber: AccountNumber): PaymentStatus
  }
  trait CreditCardService {
    def processPayment(amount: BigDecimal, creditCard: CreditCard): PaymentStatus
  }
  trait CashService {
    def processPayment(amount: BigDecimal): PaymentStatus
  }


  // Exercise. Implement `PaymentService.processPayment` using pattern matching and ADTs.
  class PaymentService(
                        bankAccountService: BankAccountService,
                        creditCardService: CreditCardService,
                        cashService: CashService,
                      ) {
    def processPayment(amount: BigDecimal, method: PaymentMethod): PaymentStatus = method match {
      case BankAccount(accountNumber) => bankAccountService.processPayment(amount, accountNumber)
      case CreditCard(cardNumber, validityDate) => creditCardService.processPayment(amount, CreditCard(cardNumber, validityDate))
      case PaymentMethod.Cash => cashService.processPayment(amount )
    }
  }
}
