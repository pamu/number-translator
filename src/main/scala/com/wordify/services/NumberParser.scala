package com.wordify.services

import cats.implicits._
import com.wordify.domain._
import com.wordify.domain.tags._
import shapeless.tag.@@

object NumberParser {
  def parse(number: Int @@ WholeNumber): NumberTerms = {

    @scala.annotation.tailrec
    def loop(result: List[Int], left: Int): List[Int] =
      if (left === 0) result else loop(left % 10 :: result, left / 10)

    val terms = loop(Nil, number).reverse.grouped(3).map(_.reverse).map {
      case List(a, b, c) => Hundreds(a, b, c)
      case List(a, b) => Tens(a, b)
      case List(a) => Units(a)
    }.toList

    NumberTerms(terms)
  }
}
