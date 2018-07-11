package com.wordify.main

import cats.data.Validated.{Invalid, Valid}
import com.wordify.services.{StaticTranslationData, NumberTranslationServiceImpl}
import com.wordify.validations._

object Main {

  def main(args: Array[String]): Unit = {

    args.headOption match {
      case Some(firstArgument) =>
        stringIsValidWholeNumberUnderTranslationLimit("Program first argument", firstArgument) match {
          case Valid(value) =>
            val service = new NumberTranslationServiceImpl()
            val result = service.convert(value)
            System.out.println(s"result: $result")
          case Invalid(e) =>
            val errorMsg = e.toList.map(_.getMessage).mkString("\n", "\n", "\n")
            System.err.println(s"errors: $errorMsg")
        }
      case None =>
        val errorMsg = s"program usage: <program name> <number must be between [Zero, ${StaticTranslationData.MaxValue}] (Both ends included)>"
        System.err.println(errorMsg)
    }
  }

}
