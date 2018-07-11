package com.wordify

import cats.data.Validated.{Invalid, Valid}
import com.wordify.exceptions.StringIsEmpty
import com.wordify.services.StaticTranslationData
import org.scalatest.{GivenWhenThen, MustMatchers, WordSpec}
import com.wordify.validations._

class ValidationSpec extends WordSpec
  with MustMatchers
  with GivenWhenThen {

  "Validations" must {

    "fail when string is empty" in {
      When("value passed is empty")
      val result = stringIsValidWholeNumberUnderTranslationLimit(
        "argument", "")

      Then("validation fails")
      expectFailure(result) must contain theSameElementsAs List(
        s"argument: String is empty, required non empty string"
      )
    }

    "fail when value is more than 999999999" in {
      When("value passed is 2147483647")
      val result = stringIsValidWholeNumberUnderTranslationLimit(
        "argument", "2147483647")

      Then("validation fails")
      expectFailure(result) must contain theSameElementsAs List(
        "Numbers greater than 999999999 cannot be translated")
    }

    "fail when value is negative" in {
      When("value passed is negative")
      val result = stringIsValidWholeNumberUnderTranslationLimit(
        "argument", "-12312312"
      )
      Then("validation fails")
      expectFailure(result) must contain theSameElementsAs List(
        "argument: Number must be greater than or equal to zero"
      )
    }

    "pass when value is between 0 and 99999999 both ends inclusive" in {
      When("value passed is within range")
      val result = stringIsValidWholeNumberUnderTranslationLimit(
        "argument", "12312312"
      )
      Then("validation passes")
      expectSuccess(result) mustBe 12312312
    }

  }

  // Private
  private def expectSuccess[A](validation: Validation[A]): A = validation match {
    case Valid(result) => result
    case Invalid(e) =>
      fail(s"Expected success but found errors: ${e.toList.map(_.getMessage).mkString("\n")}")
  }

  private def expectFailure(validation: Validation[_]): List[String] = validation match {
    case Valid(_) => fail(s"Expected failure but success found")
    case Invalid(e) => e.toList.map(_.getMessage)

  }
}
