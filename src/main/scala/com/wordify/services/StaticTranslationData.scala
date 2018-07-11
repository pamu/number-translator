package com.wordify.services


object StaticTranslationData {

  val MaxValue = 999999999

  val units: Map[Int, String] = Map(
    0 -> "zero",
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four",
    5 -> "five",
    6 -> "six",
    7 -> "seven",
    8 -> "eight",
    9 -> "nine"
  )

  val tens: Map[Int, String] = Map(
    11 -> "eleven",
    12 -> "twelve",
    13 -> "thirteen",
    14 -> "fourteen",
    15 -> "fifteen",
    16 -> "sixteen",
    17 -> "seventeen",
    18 -> "eighteen",
    19 -> "nineteen"
  )

  val tenMultiples: Map[Int, String] = Map(
    10 -> "ten",
    20 -> "twenty",
    30 -> "thirty",
    40 -> "forty",
    50 -> "fifty",
    60 -> "sixty",
    70 -> "seventy",
    80 -> "eighty",
    90 -> "ninety"
  )

  val termMap: Map[Int, String] = Map(
    1 -> "thousand",
    2 -> "million"
  )

}
