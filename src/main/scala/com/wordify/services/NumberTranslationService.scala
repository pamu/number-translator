package com.wordify.services

import com.wordify.domain.tags.WholeNumber
import com.wordify.exceptions.NumberTooBigToTranslate
import shapeless.tag.@@

import scala.util.{Failure, Success, Try}

trait NumberTranslationService {
  def translate(number: Int @@ WholeNumber): Try[String]
}

class NumberTranslationServiceImpl extends NumberTranslationService {

  override def translate(number: Int @@ WholeNumber): Try[String] =
    if (number <= StaticTranslationData.MaxValue)
      Success(Interpreter.interpret(NumberParser.parse(number)))
    else Failure(
      NumberTooBigToTranslate(
      s"Does not support translation of number larger than ${StaticTranslationData.MaxValue}"))

}