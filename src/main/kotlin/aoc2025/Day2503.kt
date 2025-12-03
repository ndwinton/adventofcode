package aoc2025

fun joltageOfBank(bank: String) =
    bank.dropLast(1)
        .max()
        .let { first ->
            (first + "" + bank.substring(bank.indexOf(first) + 1).max()).toInt()
        }