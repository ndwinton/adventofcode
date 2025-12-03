package aoc2025

fun joltageOfBank(bank: String, size: Int = 2) : Long =
    bank.dropLast(size - 1)
        .max()
        .let { first ->
            if (size == 2) {
                (first + "" + bank.substring(bank.indexOf(first) + 1).max()).toLong()
            } else {
                (first + "" + joltageOfBank(bank.substring(bank.indexOf(first) + 1), size - 1)).toLong()
            }
        }

fun totalJoltageOfBanks(banks: List<String>, size: Int = 2) = banks.sumOf { joltageOfBank(it, size) }