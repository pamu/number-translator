package com.wordify

import com.wordify.domain.tags.WholeNumber
import com.wordify.services.NumberTranslationServiceImpl
import org.scalatest.{GivenWhenThen, MustMatchers, WordSpec}
import shapeless.tag

class NumberTranslationServiceSpec extends WordSpec
  with MustMatchers
  with GivenWhenThen {

  "NumberTranslationService" must {

    "translate digits" in {
      Given("single digit 3")
      Then("answer is three")
      expectSuccess(3) mustBe "three"

      Given("single digit 6")
      Then("answer is six")
      expectSuccess(6) mustBe "six"

      Given("single digit 1")
      Then("answer is one")
      expectSuccess(1) mustBe "one"
    }

    "translate tens" in {
      Given("number 11")
      Then("answer is eleven")
      expectSuccess(11) mustBe "eleven"

      Given("number 19")
      Then("answer is nineteen")
      expectSuccess(19) mustBe "nineteen"

      Given("number 42")
      Then("answer is forty two")
      expectSuccess(42) mustBe "forty two"

      Given("number 59")
      Then("answer is fifty nine")
      expectSuccess(59) mustBe "fifty nine"

      Given("number 40")
      Then("answer is forty")
      expectSuccess(40) mustBe "forty"

      Given("number is 21")
      Then("answer is twenty one")
      expectSuccess(21) mustBe "twenty one"
    }

    "translate hundreds" in {
      Given("number 233")
      Then("answer is two hundred and thirty three")
      expectSuccess(233) mustBe "two hundred and thirty three"

      Given("number 999")
      Then("answer must be nine hundred and ninety nine")
      expectSuccess(999) mustBe "nine hundred and ninety nine"

      Given("number 703")
      Then("answer is seven hundred and three")
      expectSuccess(703) mustBe "seven hundred and three"

      Given("number 600")
      Then("answer is six hundred")
      expectSuccess(600) mustBe "six hundred"

      Given("number 510")
      Then("answer is five hundred and ten")
      expectSuccess(510) mustBe "five hundred and ten"
    }

    "translate thousands" in {
      Given("number 1000")
      Then("answer is one thousand")
      expectSuccess(1000) mustBe "one thousand"

      Given("number is 5003")
      Then("answer is five thousand, three")
      expectSuccess(5003) mustBe "five thousand, three"

      Given("number is 7010")
      Then("answer is seven thousand, ten")
      expectSuccess(7010) mustBe "seven thousand, ten"

      Given("number is 8300")
      Then("answer is eight thousand, three hundred")
      expectSuccess(8300) mustBe "eight thousand, three hundred"

      Given("number is 9207")
      Then("answer is nine thousand, two hundred and seven")
      expectSuccess(9207) mustBe "nine thousand, two hundred and seven"

      Given("number is 900023")
      Then("answer is nine hundred thousand, twenty three")
      expectSuccess(900023) mustBe "nine hundred thousand, twenty three"

      Given("number is 701503")
      Then("answer is seven hundred and one thousand, five hundred and three")
      expectSuccess(701503) mustBe "seven hundred and one thousand, five hundred and three"

      Given("number is 23001")
      Then("answer is twenty three thousand, one")
      expectSuccess(23001) mustBe "twenty three thousand, one"

      Given("number is 100001")
      Then("answer is one hundred thousand, one")
      expectSuccess(100001) mustBe "one hundred thousand, one"

      Given("number is 201304")
      Then("answer is two hundred and one thousand, three hundred and four")
      expectSuccess(201304) mustBe "two hundred and one thousand, three hundred and four"
    }

    "translate millions" in {
      Given("number is 999999999")
      Then(
        """answer is nine hundred and ninety nine million,
          |nine hundred and ninety nine thousand, nine hundred and ninety nine""".stripMargin)
      expectSuccess(999999999) mustBe
        "nine hundred and ninety nine million, nine hundred and ninety nine thousand, nine hundred and ninety nine"

      Given("number is 90013002")
      Then("answer is ninety million, thirteen thousand, two")
      expectSuccess(90013002) mustBe "ninety million, thirteen thousand, two"

      Given("number is 56945781")
      Then("answer is fifty six million, nine hundred and forty five thousand, seven hundred and eighty one")
      expectSuccess(56945781) mustBe "fifty six million, nine hundred and forty five thousand, seven hundred and eighty one"
    }

    "translate very large number" in {
      Given("number greater than 999999999 is used for translation")
      val number = 999999999 + 1
      Then("translation must fail with error")
      expectFailure(number).getMessage mustBe "Does not support translation of number larger than 999999999"
    }

  }

  // Private
  private val service = new NumberTranslationServiceImpl()
  
  private def expectSuccess(number: Int): String = {
    When("number is sent to translation service")
    service.translate(tag[WholeNumber](number)).get
  }
  
  private def expectFailure(number: Int): Throwable = {
    When("number is sent to translation service")
    service.translate(tag[WholeNumber](number)).failed.get
  }
}
