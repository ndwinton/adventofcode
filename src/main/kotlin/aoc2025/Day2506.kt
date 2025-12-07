package aoc2025

import common.transpose

fun solveWorksheet(lines: List<String>): Long =
    lines.map { it.trim() }
        .map { it.split(Regex("""\s+""")) }
        .transpose()
        .sumOf { row ->
            val op = row.last()
            val values = row.dropLast(1).map { it.toLong() }
            values.drop(1).fold(values.first()) { result, next -> if (op == "*") result * next else result + next }
        }

private tailrec fun chunk(remaining: List<Long>, part: List<Long> = emptyList(), result: List<List<Long>> = emptyList()): List<List<Long>> =
    when {
        remaining.isEmpty() -> result + listOf(part)
        remaining.first() == -1L -> chunk(remaining.drop(1), emptyList(), result + listOf(part))
        else -> chunk(remaining.drop(1), part + listOf(remaining.first()), result)
    }

fun solveWorksheetTopToBottom(lines: List<String>): Long {

    val transposed = lines.dropLast(1)
        .transpose()
        .map { if (it.isBlank()) -1L else it.trim().toLong() }
    val operators = lines.last().trim().split(Regex("""\s+"""))

    return chunk(transposed).mapIndexed { index, values ->
        val op = operators[index]
        values.drop(1).fold(values.first()) { result, next -> if (op == "*") result * next else result + next }
    }.sum()
}