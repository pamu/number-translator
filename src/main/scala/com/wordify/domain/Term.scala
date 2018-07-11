package com.wordify.domain

/**
  * Every Number is composed of terms
  * For example: 123,006,089
  * The above number is composed of three terms. Each term is has max of 3 digits
  * First term 089, second term is 006 and third term is 123
  * 089 is represented as Tens(8, 9)
  * 006 is represented as Units(6)
  * 123 is represented as Hundreds(1, 2, 3)
  * So the number 123, 006, 089 gets converted to
  *  List(Tens(8, 9), Units(6), Hundreds(1, 2, 3)
  *  The above representation is then used for the converting the number to text
  */
sealed trait Term

case class Hundreds(a: Int, b: Int, c: Int) extends Term
case class Tens(a: Int, b: Int) extends Term
case class Units(a: Int) extends Term
