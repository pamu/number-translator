package com.wordify.services

import cats.implicits._
import com.wordify.domain._
import com.wordify.services.StaticTranslationData.{tenMultiples, tens, termMap, units}

object Interpreter {

  def interpret(number: NumberTerms): String = translateTerms(number)

  def translateTerm(term: Term): String = (term match {
    case Hundreds(0, 0, 0) => ""
    case Hundreds(0, 0, c) => translateTerm(Units(c))
    case Hundreds(0, b, c) => translateTerm(Tens(b, c))
    case Hundreds(a, 0, 0) => s"${translateTerm(Units(a))} hundred"
    case Hundreds(a, b, c) =>
      s"${translateTerm(Units(a))} hundred and ${translateTerm(Tens(b, c))}"
    case Tens(0, 0) => ""
    case Tens(a, 0) => if (a === 1) tens(10) else tenMultiples(a * 10)
    case Tens(0, b) => translateTerm(Units(b))
    case Tens(a, b) =>
      if (a === 1) tens(a * 10 + b) else s"${tenMultiples(a * 10)} ${translateTerm(Units(b))}"
    case Units(0) => ""
    case Units(a) => units(a)
  }).trim

  def translateTerms(number: NumberTerms): String =
    number.terms.zipWithIndex.map {
      case (term, index) =>
        s"${translateTerm(term)} ${if (index === 0) "" else termMap(index)}".trim
    }.reverse.filterNot(_.trim.isEmpty).mkString(", ")
}
