package aoc2025

fun joltageOfBank(bank: String, size: Int = 2) : Long =
    bank.dropLast(size - 1)
        .max()
        .let { highest ->
            val remaining = bank.substring(bank.indexOf(highest) + 1)
            highest + "" + if (size == 2) remaining.max() else joltageOfBank(remaining, size - 1)
        }.toLong()

fun totalJoltageOfBanks(banks: List<String>, size: Int = 2) = banks.sumOf { joltageOfBank(it, size) }