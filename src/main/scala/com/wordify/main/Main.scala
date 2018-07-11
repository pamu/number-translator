package com.wordify.main

import cats.data.Validated.{Invalid, Valid}
import com.wordify.services.{NumberTranslationServiceImpl, StaticTranslationData}
import com.wordify.validations._

import scala.util.{Failure, Success}

object Main {

  def main(args: Array[String]): Unit = {

    args.headOption match {
      case Some(firstArgument) =>

        stringIsValidWholeNumberUnderTranslationLimit("Program first argument", firstArgument) match {
          case Valid(value) =>

            val service = new NumberTranslationServiceImpl()
            service.translate(value) match {
              case Failure(exception) =>

                exception.printStackTrace()
                System.err.println(s"error: ${exception.getMessage}")

              case Success(result) =>
                System.out.println(s"result: $result")
            }

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
