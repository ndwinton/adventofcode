package aoc2023

operator fun List<String>.get(row: Int, col: Int): String =
    when {
        row < 0 || row >= this.size -> ""
        col < 0 || col >= this[row].length -> ""
        else -> this[row][col].toString()
    }

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


private fun isSpecial(s: String): Boolean =
    s != "" && !s[0].isDigit() && s[0] != '.'
