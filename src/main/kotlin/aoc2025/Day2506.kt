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