package com.wordify.services

import com.wordify.domain.tags.WholeNumber
import shapeless.tag.@@

trait NumberTranslationService {
  def convert(number: Int @@ WholeNumber): String
}

class NumberTranslationServiceImpl extends NumberTranslationService {

  override def convert(number: Int @@ WholeNumber): String =
    Interpreter.interpret(Parser.parse(number))

}