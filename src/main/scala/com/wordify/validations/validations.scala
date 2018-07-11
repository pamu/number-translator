package com.wordify

import cats.data._
import cats.implicits._
import com.wordify.exceptions._
import com.wordify.domain.tags.WholeNumber
import com.wordify.services.StaticTranslationData
import shapeless.tag
import shapeless.tag.@@

import scala.util.{Failure, Success, Try}

package object validations {

  type Validation[A] = ValidatedNel[ValidationError, A]

  def stringIsValidWholeNumberUnderTranslationLimit(
    name: String,
    value: String): Validation[Int @@ WholeNumber] =
    validateInt(name, value).andThen(validWholeNumber(name, _))
      .andThen(value =>
        if (value <= StaticTranslationData.MaxValue) value.validNel
        else NumberTooBigToTranslate(
          s"Numbers greater than ${StaticTranslationData.MaxValue} cannot be translated").invalidNel
      )

  // Private

  private def nonEmptyString(name: String, value: String): Validation[String] =
    if (value.trim.nonEmpty) value.trim.validNel
    else StringIsEmpty(s"$name: String is empty, required non empty string").invalidNel

  private def validateInt(name: String, value: String): Validation[Int] =
    nonEmptyString(name, value).andThen { validString =>
      Try(validString.toInt) match {
        case Failure(ex) =>
          StringParsingFailed(
            s"$name: String is not valid Int, required valid int in string form. reason: ${ex.getClass.getName}, ${ex.getMessage}").invalidNel
        case Success(intValue) => intValue.validNel
      }
    }

  private def validWholeNumber(name: String, value: Int): Validation[Int @@ WholeNumber] =
    if (value >= 0) tag[WholeNumber](value).validNel
    else StringNotWholeNumber(s"$name: Number must be greater than or equal to zero").invalidNel

}
