package aoc2023

import common.transpose

fun horizontalReflection(lines: List<String>): Int {
    val forward = horizontalReflectionFromTop(lines)
    if (forward > 0) return forward
    val reversed = horizontalReflectionFromTop(lines.reversed())
    return if (reversed > 0) lines.size - reversed else 0
}

fun verticalReflection(lines: List<String>) =
    horizontalReflection(lines.map { it.toList() }.transpose().map { it.joinToString("") })

fun horizontalReflectionFromTop(lines: List<String>): Int {
    val linePos = lines.mapIndexed { index, line -> Pair(index, line) }
        .groupBy { it.second }
        .map { Pair(it.key, it.value.map { pair -> pair.first }) }
        .toMap()

    if (linePos[lines[0]]!!.size == 1) return 0
    val reflectionAt = linePos[lines[0]]!!
        .sorted()
        .drop(1)
        .filter { validateRange(it, lines, linePos) }
    return if (reflectionAt.isNotEmpty()) (reflectionAt.first() + 1) / 2 else 0
}

fun validateRange(pos: Int, lines: List<String>, linePos: Map<String, List<Int>>): Boolean =
    (0 .. pos).all { linePos[lines[it]]!!.contains(pos - it) }


fun reflectionTotals(data: String): Int =
    data.split("\n\n").sumOf { chunk ->
        horizontalReflection(chunk.split("\n")) * 100 + verticalReflection(chunk.split("\n"))
    }

