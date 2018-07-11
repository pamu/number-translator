package com.wordify.domain

sealed trait Term

case class Hundreds(a: Int, b: Int, c: Int) extends Term
case class Tens(a: Int, b: Int) extends Term
case class Units(a: Int) extends Term

