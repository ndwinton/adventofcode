package aoc2023

import common.get
fun findPartNumbersPerRow(lines: List<String>): List<List<Int>> =
    lines.mapIndexed { row, line ->
        Regex("""\d+""")
            .findAll(line)
            .filter { matchResult ->
                matchResult.range.any { bordersSpecial(lines, row, it) }
            }
            .map { matchResult -> matchResult.value.toInt() }
            .toList()
    }

private fun bordersSpecial(lines: List<String>, row: Int, col: Int): Boolean =
    when {
        (col - 1 .. col + 1).any { isSpecial(lines[row - 1, it]) } -> true
        (col - 1 .. col + 1).any { isSpecial(lines[row + 1, it]) } -> true
        isSpecial(lines[row, col - 1]) -> true
        isSpecial(lines[row, col + 1]) -> true
        else -> false
    }

fun sumOfPartNumbers(lines: List<String>) =
    findPartNumbersPerRow(lines).sumOf { it.sum() }


private fun isSpecial(c: Char): Boolean =
    !c.isDigit() && c != '.'
