package com.wordify.services

import cats.implicits._
import com.wordify.domain._
import com.wordify.services.StaticTranslationData.{tenMultiples, tens, termMap, units}

object Interpreter {

  def interpret(number: NumberTerms): String = translateTerms(number)

  def translateTerm(term: Term): String = term match {
    case Hundreds(a, b, c) =>
      s"${translateTerm(Units(a))} and ${translateTerm(Tens(b, c))}"
    case Tens(a, b) =>
      if (a === 1) tens(a * 10 + b)
      else s"${tenMultiples(a)} ${translateTerm(Units(b))}"
    case Units(a) => units(a)
  }

  def translateTerms(number: NumberTerms): String =
    number.terms.zipWithIndex.map {
      case (term, index) =>
        s"${translateTerm(term)} ${if (index === 0) "" else termMap(index)}".trim
    }.reverse.mkString(" ")
}
