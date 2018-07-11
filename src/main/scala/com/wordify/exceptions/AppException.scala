package com.wordify.exceptions

sealed trait AppException extends Throwable

abstract class ValidationError(msg: String) extends Exception(msg) with AppException
case class StringIsEmpty(msg: String) extends ValidationError(msg)
case class StringParsingFailed(msg: String) extends ValidationError(msg)
case class StringNotWholeNumber(msg: String) extends ValidationError(msg)
case class NumberTooBigToTranslate(msg: String) extends ValidationError(msg)
